package main.java.scrabblegame.game;

import main.java.scrabblegame.game.elements.*;
import main.java.scrabblegame.game.elements.Dictionary;
import main.java.scrabblegame.game.moves.Move;
import main.java.scrabblegame.game.moves.PassMove;
import main.java.scrabblegame.game.moves.PlaceMove;
import main.java.scrabblegame.game.moves.SwapMove;
import main.java.scrabblegame.gui.InputHandler;

import java.util.*;

/**
 * The Game class which stores and controls all the resources needed for a game
 */
public class Game {
    private Board board = new Board();
    private Bag bag = new Bag();
    private PlayerManager playerManager;
    private InputHandler inputHandler = new InputHandler();
    main.java.scrabblegame.game.elements.Dictionary dict = new Dictionary();

    /**
     * Creates a GameState object from the Game
     *
     * @return the created GameState object
     */
    public GameState getGameState() {
        return new GameState(new Bag (bag), new PlayerManager (playerManager), new Board (board));
    }

    public InputHandler getInputHandler() {
        return inputHandler;
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
    public void initPlayers(int numPlayers, List<String> names, List<Integer> types) {
        playerManager = new PlayerManager(numPlayers, names, types, bag);
    }

    /**
     * Makes the given move in the current game
     *
     * @param move the move to be made
     */
    public void doMove(Move move) throws Exception {
        move.execute(bag, playerManager.getCurrentPlayer(), board, dict);
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