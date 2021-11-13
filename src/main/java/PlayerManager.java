package main.java;

import java.util.Scanner;
import java.util.List;

public class PlayerManager {
    private Player[] players;
    private int currentPlayerNum = 0;
    public PlayerManager(Bag bag) {
        Scanner sc = new Scanner(System.in);
        System.out.print("How many players are there?");
        int numPlayers = Math.min(sc.nextInt(), 1);
        this.players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter Player " + (i+1) + "'s Name: ");
            String name = sc.next();
            Player player = new Player(name);
            player.addTiles(bag.drawTiles(7));
            players[i] = player;
            System.out.println();
        }
    }
    private Player getCurrentPlayer() {
        return players[currentPlayerNum];
    }

    public void goToNextPlayer() {
        currentPlayerNum = (currentPlayerNum + 1) % players.length;
    }

    public Move getNextMove(Scanner sc, Board board, int numTilesRemaining) {
        return getCurrentPlayer().makeMove(sc, board, numTilesRemaining);
    }

    public boolean currentPlayerHasLetters(List<Character> letters) {
        return getCurrentPlayer().hasLetters(letters);
    }

    public void updateCurrentPlayer(int pointsToAdd, List<Tile> tilesToAdd, List<Tile> tilesToRemove) {
        getCurrentPlayer().addPoints(pointsToAdd);
        updateCurrentPlayer(tilesToAdd, tilesToRemove);
    }
    public void updateCurrentPlayer(List<Tile> tilesToAdd, List<Tile> tilesToRemove) {
        Player currentPlayer = getCurrentPlayer();
        for (Tile tile: tilesToRemove) {
            currentPlayer.removeTile(tile.getLetter());
        }
        currentPlayer.addTiles(tilesToAdd);
    }

    public Player getLeader() {
        Player leader = players[0];
        int leaderPoints = 0;
        for (Player player : players) {
            if (player.getPoints() >= leaderPoints) {
                leader = player;
                leaderPoints = player.getPoints();
            }
        }
        return leader;
    }
}