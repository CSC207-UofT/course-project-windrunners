package main.java.scrabblegame.game;

import java.util.List;

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
        return new GameState(new Bag(bag), new PlayerManager(playerManager), new Board(board));
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
    public void doMove(Move move) {
        move.execute(bag, playerManager, board, dict);
    }

    /**
     * Passes the turn
     */
    public void doPassMove() {
        Move move = new PassMove();
        doMove(move);
    }

    /**
     * Execute a swap move, with the tiles that were removed from the current player
     *
     * @param tilesRemoved the tiles to be swapped
     */
    public void doSwapMove(List<Tile> tilesRemoved) {
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
    public void doPlaceMove(int x, int y, boolean direction, String word) {
        Move move = new PlaceMove(x, y, direction, word);
        doMove(move);
    }

    /**
     * challenge the move of placing word into the board
     *
     * @param name       name of challenger
     * @param x          is the column of the first letter of the word
     * @param y          is the row of the last letter of the word
     * @param direction  is the direction along which the word may be placed
     * @param word       is the word that is being checked
     * @return true      iff placing this word would create an invalid Scrabble words
     *                      and name is the name of an existing player
     */
    public boolean challenge( String name, int x, int y, boolean direction, String word) {
        Player challenger = null;
        for (Player p : playerManager.getPlayers()) {
            if (p.getName().equals(name)) {
                challenger = p;
            }
        }
        return challenger != null && !board.checkWordValid(x, y, direction, word, dict);
    }

    /**
     * Set the player with name "name"'s turn to be skipped later
     *
     * @param name      name of player's turn to be skipped
     */
    public void setPlayerSkip( String name ) {
        for (Player p : playerManager.getPlayers()) {
            if (p.getName().equals(name)) {
                p.setSkip(true);
            }
        }
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
