package main.java;

import java.util.Scanner;

public class Game {
    private Player player1;
    private Player player2;
    private Board board;
    private Dictionary dictionary;
    private Bag bag;

    public static void main(String[] args) {
        Board board = new Board();
        Dictionary dictionary = new Dictionary();
        Bag bag = new Bag();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Player 1's Name: ");
        String name1 = sc.next();
        System.out.print("Enter Player 2's Name: ");
        String name2 = sc.next();
        Player player1 = new Player(name1);
        Player player2 = new Player(name2);
        for (int i=0; i<7; i++) {
            player1.addTile(bag.drawTile());
            player2.addTile(bag.drawTile());
        }
        player1.addPoints(0);
    }
}
