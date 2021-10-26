package main.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        Bag bag = new Bag();
        Board board = new Board(bag.drawTile());
        Scanner sc = new Scanner(System.in);
        System.out.println(board.getBoard()[1][1]);
        System.out.print("Enter Player 1's Name: ");
        String name1 = sc.next();
        System.out.print("Enter Player 2's Name: ");
        String name2 = sc.next();
        System.out.println();
        Player player1 = new Player(name1);
        Player player2 = new Player(name2);
        for (int i = 0; i < 7; i++) {
            player1.addTile(bag.drawTile());
            player2.addTile(bag.drawTile());
        }
        Player currentPlayer = player2;
        while (bag.getTiles().size() > 0) {
            if (currentPlayer == player2) {
                currentPlayer = player1;
            } else {
                currentPlayer = player2;
            }
            System.out.println(currentPlayer.getName() + "'s Turn:");
            System.out.println("Number of Tiles in the Bag = " + bag.getTiles().size());
            System.out.println("Score: " + currentPlayer.getPoints());
            System.out.println(currentPlayer.getRackString());
            System.out.println(board);
            System.out.println("Answer true to make a move, false to swap tiles: ");
            boolean choice = sc.nextBoolean();
            if (choice) {
                playerDecidesToMakeMove(currentPlayer, board, bag, sc);
            } else {
                playerDecidesToSwapTiles(currentPlayer, bag, sc);
            }
            System.out.println();
        }
    }

    private static void playerDecidesToMakeMove(Player currentPlayer, Board board, Bag bag, Scanner sc) {
        System.out.println("Position of 1st letter (e.g. A5): ");
        String position = sc.next();
        int x = (int) position.charAt(0) - 65;
        int y = Integer.parseInt(String.valueOf(position.charAt(1))) - 1;
        System.out.println("You word goes from left to right? (answer true or false)");
        boolean direction = sc.nextBoolean();
        System.out.println("What is the word?");
        String word = sc.next().toUpperCase();
        if (board.checkWord(x, y, direction, word)) {
            List<Tile> tilesForWord = new ArrayList<>();
            int wordValue = 0;
            for (char letter : board.lettersNeeded(x, y, direction, word)) {
                Tile removedTile = (currentPlayer.removeTile(letter));
                tilesForWord.add(removedTile);
                wordValue += removedTile.getValue();
            }
            board.insertWord(x, y, direction, tilesForWord);
            currentPlayer.addPoints(wordValue);
            while (currentPlayer.getRackSize() < 7) {
                currentPlayer.addTile(bag.drawTile());
            }
        } else {
            System.out.println("Invalid Word/Placement");
        }
    }

    private static void playerDecidesToSwapTiles(Player currentPlayer, Bag bag, Scanner sc) {
        boolean loopCondition = true;
        while (loopCondition) {
            System.out.println("Number of tiles to swap? (between 1 and 7)");
            int numTilesToSwap = sc.nextInt();
            if (bag.getTiles().size() - numTilesToSwap < 0) {
                System.out.println("Bag doesn't have enough tiles to swap. Try Again");
            }
            else {
                loopCondition = false;
                int i = 0;
                List<Tile> temp = new ArrayList<>();
                while (i < numTilesToSwap) {
                    System.out.println("Tile number " + (i + 1) + " to swap? ");
                    char tileToSwap = sc.next().toUpperCase().charAt(0);
                    if (currentPlayer.hasLetter(tileToSwap)) {
                        temp.add(currentPlayer.removeTile(tileToSwap));
                        i++;
                    }
                    else {
                        System.out.println(currentPlayer.getName() + " doesn't have this tile. Try again");
                    }
                }
                List<Tile> tilesReturned = bag.swapTiles(temp);
                for (int k = 0; k < numTilesToSwap; k++) {
                    currentPlayer.addTile(tilesReturned.get(k));
                }
                System.out.println("Tiles Swapped");
            }
        }
    }
}