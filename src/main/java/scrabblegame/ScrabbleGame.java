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
        PlayerController humanController = new HumanController();
        while (game.getBag().numTilesRemaining() > 0) {
            gamePanel.repaint();

            Player currPlayer = game.getCurrentPlayer();
            System.out.println(currPlayer.getName() + "'s Turn");
            System.out.println("Number of Tiles in the Bag = " + game.numTilesRemaining());
            System.out.println(currPlayer);
            System.out.println(game.getBoard());

            Move move = humanController.makeMove(sc, game);
            game.doMove(move);

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
}
