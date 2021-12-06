package main.java.scrabblegame;


import main.java.scrabblegame.game.*;
import main.java.scrabblegame.gui.GamePanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ScrabbleGame {

    private static Game game;
    private static GamePanel gamePanel;

    /**
     * The main method. Sets up and controls the state of the Game.
     * The Game ends when the Bag empties.
     */
    public static void main(String[] args) {

        JFrame window = new JFrame("Scrabble");

        Scanner sc = new Scanner(System.in);

        initGame(sc, window);

        while (game.getBag().numTilesRemaining() > 0) {
            gamePanel.repaint();

            Player currPlayer = game.getCurrentPlayer();
            System.out.println(currPlayer.getName() + "'s Turn");

            if (currPlayer.getSkip()) {
                System.out.println("This turn will be skipped, due to a previous unsuccessful challenge.\n");
                currPlayer.setSkip(false);
            } else {
                System.out.println("Number of Tiles in the Bag = " + game.numTilesRemaining());
                System.out.println(currPlayer);
                System.out.println(game.getBoard());

                makeMove(sc);
            }

            game.nextTurn();

        }
        Player winner = game.getLeader();
        System.out.println("Congratulations " + winner.getName() + "! You won with " + winner.getPoints() + " points");
    }

    private static void initGame(Scanner sc, JFrame window) {

        game = new Game();
        System.out.println("How many players are there?");
        int numPlayers = Math.max(sc.nextInt(), 1);
        List<String> names = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            System.out.println();
            System.out.print("Enter Player " + (i + 1) + "'s Name: ");
            names.add(sc.next());
        }
        game.initPlayers(numPlayers, names);

        gamePanel = new GamePanel(game);
        window.setContentPane(gamePanel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }

    private static void makeMove(Scanner sc) {

        System.out.println("What is your move? Answer 1 to place a word, 2 to swap tiles, 3 to pass: ");
        int choice = sc.nextInt();
        String moveType = (choice == 1) ? "place" : (choice == 2) ? "swap" : "pass";

        if (moveType.equals("pass")) {
            game.doPassMove();
        } else if (moveType.equals("swap")) {
            makeSwapMove(sc);
        } else {
            makePlaceMove(sc);
        }

    }

    private static void makeSwapMove(Scanner sc) {

        int maxTilesToSwap = Math.min(7, game.numTilesRemaining());
        int numTilesToSwap;
        while (true) {
            System.out.println("Number of tiles to swap? (between 1 and " + maxTilesToSwap + ")");
            numTilesToSwap = sc.nextInt();
            if (numTilesToSwap > maxTilesToSwap) {
                System.out.println("You can only swap " + maxTilesToSwap + " tiles. Try again.");
            } else {
                break;
            }
        }

        List<Tile> tilesToSwap = new ArrayList<>();
        List<Tile> rack = new ArrayList<>(game.getCurrPlayerRack());
        List<Character> charList;
        int i = 0;
        while (i < numTilesToSwap) {
            System.out.println("Tile number " + (i + 1) + " to swap? ");
            char letterToSwap = sc.next().toUpperCase().charAt(0);

            charList = rack.stream().map(Tile::getLetter).collect(Collectors.toList());
            if (charList.contains(letterToSwap)) {
                for (Tile tile : rack) {
                    if (tile.getLetter() == letterToSwap) {
                        rack.remove(tile);
                        tilesToSwap.add(tile);
                        break;
                    }
                }
                i++;
            } else {
                System.out.println(game.getCurrentPlayer().getName() + " doesn't have this tile. Try again");
            }
        }

        game.doSwapMove(tilesToSwap);
        System.out.println("Tiles Swapped");

    }

    private static void makePlaceMove(Scanner sc) {

        GameState prevState = game.getGameState();

        System.out.println("Position of 1st letter (e.g. A5): ");
        String position = sc.next();
        int y = (int) position.charAt(0) - 65;
        int x = Integer.parseInt(position.substring(1)) - 1;
        System.out.println("Does your word goes from left to right? (Y for left to right | N for top to bottom)");
        String directionStr = sc.next();
        boolean direction = (directionStr.charAt(0) == 'Y') ? Board.RIGHT : Board.DOWN;
        System.out.println("What is the word?");
        String word = sc.next().toUpperCase();
        game.doPlaceMove(x, y, direction, word);

        System.out.println(game.getBoard());
        gamePanel.repaint();

        System.out.println("Would any player like to challenge this move? (Y/N)");
        if (sc.next().charAt(0) == 'Y') {
            System.out.println("Who would like to challenge?");
            String name = sc.next();

            GameState currState = game.getGameState();
            game.loadGameState(prevState);
            boolean successful = game.challenge(name, x, y, direction, word);
            if (successful) {
                System.out.println("The challenge was succesful! The previous move will be reverted.\n");
            } else {
                System.out.println("The challenge was unsuccesful, and the game will continue.\n");
                game.loadGameState(currState);
                game.setPlayerSkip(name);
            }
        }

    }
}
