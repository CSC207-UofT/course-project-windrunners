package main.java.scrabblegame.game;

import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.Arrays;

/**
 * A Player Manager which keeps track of the Players, and the current Player
 * Has a list of Players, and an attribute to keep track of the current Player
 */

public class PlayerManager {
    private final Player[] players;
    private int currentPlayerNum = 0;

    /**
     * Class constructor. Initializes Player objects and distributes
     * Tiles to each player from the Bag
     *
     * @param numPlayers the number of players
     * @param names      a list of the names of the players
     * @param bag        the Bag used to distribute Tiles from
     */
    public PlayerManager(int numPlayers, List<String> names, Bag bag) {
        this.players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            Player player = new Player(names.get(i));
            player.addTiles(bag.drawTiles(7));
            players[i] = player;
        }
    }

    /**
     * Class constructor.
     *
     * @param players          the list of players
     * @param currentPlayerNum the number of players
     */
    public PlayerManager(Player[] players, int currentPlayerNum) {
        this.players = players;
        this.currentPlayerNum = currentPlayerNum;
    }

    /**
     * Copy constructor. Used to make a deep copy of a player manager.
     *
     * @param that the PlayerManager to copy from
     */
    public PlayerManager(PlayerManager that) {
        this.players = new Player[that.getPlayers().length];
        for (int i = 0; i < this.players.length; i++) {
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
    public Player[] getPlayers() {
        return players;
    }

    /**
     * move to the next player
     */
    public void goToNextPlayer() {
        currentPlayerNum = (currentPlayerNum + 1) % players.length;
    }

    /**
     * check whether the current Player has all the letters in the List
     *
     * @param letters the List of letters; to be checked if it's contained in the Player's rack
     * @return true iff the Player's rack contains all the letters in the List
     */
    public boolean currentPlayerHasLetters(List<Character> letters) {
        return getCurrentPlayer().hasLetters(letters);
    }

    /**
     * called when the current Player decides to insert a word into the Board
     * adds tilesToAdd to the Player's rack and removes tilesToRemove
     *
     * @param pointsToAdd   the points the payer gains on inserting the word
     * @param tilesToAdd    the tiles to be added to the Player's rack (drawn from the Bag)
     * @param tilesToRemove the tiles to be removed from the Player's rack (required for the word)
     */
    public void updateCurrentPlayer(int pointsToAdd, List<Tile> tilesToAdd, List<Tile> tilesToRemove) {
        getCurrentPlayer().addPoints(pointsToAdd);
        updateCurrentPlayer(tilesToAdd, tilesToRemove);
    }

    /**
     * called when the current Player decides to swap Tiles with the Bag
     * adds tilesToAdd to the Player's rack and removes tilesToRemove
     *
     * @param tilesToAdd    the tiles to be added to the Player's rack
     * @param tilesToRemove the tiles to be removed from the Player's rack
     */
    public void updateCurrentPlayer(List<Tile> tilesToAdd, List<Tile> tilesToRemove) {
        Player currentPlayer = getCurrentPlayer();
        currentPlayer.removeTiles(Tile.tilesToChars(tilesToRemove));
        currentPlayer.addTiles(tilesToAdd);
    }

    /**
     * get the Player with currently in the lead.
     * Points for determining lead is points - points of the tiles on rack unless your rack is empty.
     * In that case it is points + points of tiles on everyone elses rack.
     * In the case of a tie, points is used as a tiebreaker.
     *
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