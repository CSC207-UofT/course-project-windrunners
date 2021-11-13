package main.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        Bag bag = new Bag();
        Board board = new Board();
        Scanner sc = new Scanner(System.in);
        System.out.println(board.getBoard()[1][1]);
        System.out.print("Enter Player 1's Name: ");
        String name1 = sc.next();
        System.out.print("Enter Player 2's Name: ");
        String name2 = sc.next();
        System.out.println();
        Player player1 = new Player(name1);
        Player player2 = new Player(name2);
        player1.addTiles(bag.drawTiles(7));
        player2.addTiles(bag.drawTiles(7));
        Player currentPlayer = player2;
        while (bag.numTilesRemaining() > 0) {
            if (currentPlayer == player2) {
                currentPlayer = player1;
            } else {
                currentPlayer = player2;
            }
            System.out.println(currentPlayer.getName() + "'s Turn:");
            System.out.println("Number of Tiles in the Bag = " + bag.numTilesRemaining());
            System.out.println("Score: " + currentPlayer.getPoints());
            System.out.println(currentPlayer.getRackString());
            System.out.println(board);
            if (bag.numTilesRemaining() < 7) {
                System.out.println("Note: Bag has only " + bag.numTilesRemaining() + " tiles");
            }
            System.out.println("Answer 1 to make a move, 2 to swap tiles, 3 to pass: ");
            int choice = sc.nextInt();
            if (choice == 1) {
                playerDecidesToMakeMove(currentPlayer, board, bag, sc, dictionary);
            } else if (choice == 2) {
                playerDecidesToSwapTiles(currentPlayer, bag, sc);
            }
            System.out.println();
        }
    }

    private static void playerDecidesToMakeMove(Player currentPlayer, Board board, Bag bag, Scanner sc,
                                                Dictionary dictionary) {
        System.out.println("Position of 1st letter (e.g. A5): ");
        String position = sc.next();
        int y = (int) position.charAt(0) - 65;
        int x = Integer.parseInt(position.substring(1)) - 1;
        System.out.println("You word goes from left to right? (answer true or false)");
        boolean direction = sc.nextBoolean();
        System.out.println("What is the word?");
        String word = sc.next().toUpperCase();
        if (board.checkWord(x, y, direction, word, dictionary)) {
            List<Tile> tilesForWord = new ArrayList<>();
            for (char letter : board.lettersNeeded(x, y, direction, word)) {
                Tile removedTile = (currentPlayer.removeTile(letter));
                tilesForWord.add(removedTile);
            }
            int wordValue = board.insertWord(x, y, direction, tilesForWord);
            currentPlayer.addPoints(wordValue);
            int tilesToDraw = 7 - currentPlayer.getRackSize();
            currentPlayer.addTiles(bag.drawTiles(tilesToDraw));
        } else {
            System.out.println("Invalid Word/Placement");
        }
    }

    private static void playerDecidesToSwapTiles(Player currentPlayer, Bag bag, Scanner sc) {
        boolean loopCondition = true;
        while (loopCondition) {
            System.out.println("Number of tiles to swap? (between 1 and 7)");
            int numTilesToSwap = sc.nextInt();
            if (bag.numTilesRemaining() < numTilesToSwap) {
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
                currentPlayer.addTiles(tilesReturned);
                System.out.println("Tiles Swapped");
            }
        }
    }
}