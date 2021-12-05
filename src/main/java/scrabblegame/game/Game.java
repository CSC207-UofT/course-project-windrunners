package main.java.scrabblegame.game;

import java.util.*;

/**
 * The Game class which stores and controls all the resources needed for a game
 */
public class Game {
    private Board board = new Board();
    private Bag bag = new Bag();
    private PlayerManager playerManager;
    Dictionary dict = new Dictionary();

    /**
     * Creates a GameState object from the Game
     *
     * @return the created GameState object
     */
    public GameState getGameState() {
        return new GameState(new Bag (bag), new PlayerManager (playerManager), new Board (board));
    }

    /**
     * Updates the Game attributes with information from a GameState
     *
     * @param gameState the GameState object to get the information from
     */
    public void loadGameState(GameState gameState) {
        bag = gameState.getBag();
        playerManager = gameState.getPlayerManager();
        board = gameState.getBoard();
    }

    /**
     * Initializes the PlayerManager
     *
     * @param numPlayers the number of players
     * @param names      a list of the names of the players
     */
    public void initPlayers(int numPlayers, List<String> names) {
        playerManager = new PlayerManager(numPlayers, names, bag);
    }

    /**
     * Makes the given move in the the current game
     *
     * @param move the move to be made
     */
    public void doMove(Move move) throws Exception {
        move.execute(bag, playerManager, board, dict);
    }

    /**
     * Passes the turn
     */
    public void doPassMove() throws Exception {
        Move move = new PassMove();
        doMove(move);
    }

    /**
     * Execute a swap move, with the tiles that were removed from the current player
     *
     * @param tilesRemoved the tiles to be swapped
     */
    public void doSwapMove(List<Tile> tilesRemoved) throws Exception {
        Move move = new SwapMove(tilesRemoved);
        doMove(move);
    }

    /**
     * Execute a swap move, with the given inputs
     *
     * @param word      is the word to be placed on the Board
     * @param x         is the column of the first letter of the word
     * @param y         is the row of the first letter of the word
     * @param direction is the direction along which the word is to be placed
     */
    public void doPlaceMove(int x, int y, boolean direction, String word) throws Exception {
        Move move = new PlaceMove(x, y, direction, word);
        doMove(move);
    }


    public void nextTurn() {
        playerManager.goToNextPlayer();
    }

    public Player getLeader() {
        return playerManager.getLeader();
    }

    public Player getCurrentPlayer() {
        return playerManager.getCurrentPlayer();
    }

    public List<Tile> getCurrPlayerRack() {
        return playerManager.getCurrentPlayer().getRack();
    }

    public int numTilesRemaining() {
        return bag.numTilesRemaining();
    }

    public Board getBoard() {
        return board;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public Bag getBag() {
        return bag;
    }
}