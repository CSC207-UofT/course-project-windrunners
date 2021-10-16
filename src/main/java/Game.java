package main.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    public static void main(String[] args) {
        Board board = new Board();
        Dictionary dictionary = new Dictionary();
        Bag bag = new Bag();
        Scanner sc = new Scanner(System.in);
        System.out.println(board.getBoard()[1][1]);
        System.out.print("Enter Player 1's Name: ");
        String name1 = sc.next();
        System.out.print("Enter Player 2's Name: ");
        String name2 = sc.next();
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
            System.out.println(currentPlayer.getRackString());
            System.out.println(board.toString());
            System.out.println("Position of 1st letter (e.g. A5): ");
            String position = sc.next();
            int x = (int) position.charAt(0) - 65;
            int y = Integer.parseInt(String.valueOf(position.charAt(1)));
            System.out.println("You word goes from left to right? (answer true or false)");
            boolean direction = sc.nextBoolean();
            System.out.println("What is the word?");
            String word = sc.next();
            if (board.checkWord(x, y, direction, word)) {
                List<Tile> tilesForWord = new ArrayList<>();
                for (char letter : board.lettersNeeded(x, y, direction, word)) {
                    tilesForWord.add(currentPlayer.removeTile(letter));
                }
                board.insertWord(x, y, direction, tilesForWord);
                while(currentPlayer.getRackSize() < 7) {
                    currentPlayer.addTile(bag.drawTile());
                }
            } else {
                System.out.println("Invalid Word/Placement");
            }
        }
    }
}
