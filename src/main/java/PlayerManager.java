package main.java;

import java.util.Scanner;

public class PlayerManager {
    private Player[] players;
    private int currentPlayerNum = 0;
    public PlayerManager(Bag bag) {
        Scanner sc = new Scanner(System.in);
        System.out.print("How many players are there?");
        int numPlayers = sc.nextInt();
        this.players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            System.out.println("Enter Player " + (i+1) + "'s Name: ");
            String name = sc.next();
            Player player = new Player(name);
            player.addTiles(bag.drawTiles(7));
            players[i] = player;
        }
    }
    public Player getNextPlayer() {
        Player nextPlayer = players[currentPlayerNum];
        currentPlayerNum = (currentPlayerNum + 1) % players.length;
        return nextPlayer;
    }
}