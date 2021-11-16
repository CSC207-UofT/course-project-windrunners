package main.java;

import java.awt.*;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.Arrays;

/**
 * A Player Manager which keeps track of the Players, and the current Player
 * Has a list of Players, and an attribute to keep track of the current Player
 */

public class PlayerManager {
    private Player[] players;
    private int currentPlayerNum = 0;

    /**
     * Class constructor. Initializes Player objects and distributes
     * Tiles to each player from the Bag
     * @param bag the Bag used to distribute Tiles from
     * @param in is an InputStream to allow the playerManager to ask the user
     *          for the number and names of players
     * @param out is an PrintStream to allow the playerManager to ask a user for input
     */
    public PlayerManager(InputStream in, PrintStream out, Bag bag) {
        Scanner sc = new Scanner(in);
        out.print("How many players are there?");
        int numPlayers = Math.max(sc.nextInt(), 1);
        this.players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            out.println();
            out.print("Enter Player " + (i+1) + "'s Name: ");
            String name = sc.next();
            Player player = new Player(name);
            player.addTiles(bag.drawTiles(7));
            players[i] = player;
        }
    }

    /**
     * Class constructor.
     * @param players the list of players
     */
    public PlayerManager(Player[] players, int currentPlayerNum){
        this.players = players;
        this.currentPlayerNum = currentPlayerNum;
    }

    /**
     * Copy constructor. Used to make a deep copy of a player manager.
     * @param that the PlayerManager to copy from
     */
    public PlayerManager(PlayerManager that){
        this.players = new Player[that.getPlayers().length];
        for (int i = 0; i < this.players.length; i++){
            this.players[i] = new Player(that.getPlayers()[i]);
        }
        this.currentPlayerNum = that.getCurrentPlayerNum();
    }

    /**
     * @return the number of the current player
     */
    public int getCurrentPlayerNum() {
        return currentPlayerNum;
    }

    /**
     * @return the current Player
     */
    public Player getCurrentPlayer() {
        return players[currentPlayerNum];
    }

    /**
     * @return the list of players
     */
    public Player[] getPlayers() { return players; }
    /**
     * move to the next player
     */
    public void goToNextPlayer() {
        currentPlayerNum = (currentPlayerNum + 1) % players.length;
    }

    /**
     * get the Move made by the current Player
     * @param in is an InputStream to allow the Player to get data from a user
     * @param out is an PrintStream to allow the Player to output data to a user
     * @param board the Scrabble Board
     * @param numTilesRemaining is the number of Tiles remaining in the Bag
     * @return the Move made by the current Player
     */
    public Move getNextMove(InputStream in, PrintStream out, Board board, int numTilesRemaining) {
        return getCurrentPlayer().makeMove(in, out, board, numTilesRemaining);
    }

    /**
     * check whether the current Player has all the letters in the List
     * @param letters the List of letters; to be checked if it's contained in the Player's rack
     * @return true iff the Player's rack contains all the letters in the List
     */
    public boolean currentPlayerHasLetters(List<Character> letters) {
        return getCurrentPlayer().hasLetters(letters);
    }

    /**
     * called when the current Player decides to insert a word into the Board
     * adds tilesToAdd to the Player's rack and removes tilesToRemove
     * @param pointsToAdd the points the payer gains on inserting the word
     * @param tilesToAdd the tiles to be added to the Player's rack (drawn from the Bag)
     * @param tilesToRemove the tiles to be removed from the Player's rack (required for the word)
     */
    public void updateCurrentPlayer(int pointsToAdd, List<Tile> tilesToAdd, List<Tile> tilesToRemove) {
        getCurrentPlayer().addPoints(pointsToAdd);
        updateCurrentPlayer(tilesToAdd, tilesToRemove);
    }

    /**
     * called when the current Player decides to swap Tiles with the Bag
     * adds tilesToAdd to the Player's rack and removes tilesToRemove
     * @param tilesToAdd the tiles to be added to the Player's rack
     * @param tilesToRemove the tiles to be removed from the Player's rack
     */
    public void updateCurrentPlayer(List<Tile> tilesToAdd, List<Tile> tilesToRemove) {
        Player currentPlayer = getCurrentPlayer();
        for (Tile tile: tilesToRemove) {
            currentPlayer.removeTile(tile.getLetter());
        }
        currentPlayer.addTiles(tilesToAdd);
    }

    /**
     * get the Player with maximum points so far
     * @return the Player with maximum points so far
     */
    public Player getLeader() {
        int totalRackPoints = 0;
        for (Player player : players) totalRackPoints += player.getRackPoints();
        final int finalTotalRackPoints = totalRackPoints;
        Comparator<Player> comp = (Player p1, Player p2) -> {
            int m1 = p1.getPoints();
            int m2 = p2.getPoints();
            m1 += (p1.getRackPoints() == 0) ? finalTotalRackPoints : -p1.getRackPoints();
            m2 += (p2.getRackPoints() == 0) ? finalTotalRackPoints : -p2.getRackPoints();
            return m1 - m2;
        };
        return Collections.max(Arrays.asList(players), comp.thenComparing(Player::getPoints));
    }

}
