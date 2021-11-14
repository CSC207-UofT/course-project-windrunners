package main.java;

import java.util.List;

/**
 * This class holds all the states of the game
 */
public class GameState {
    private Bag bag;
    private Board board;
    private PlayerManager playerManager;

    /**
     * Constructs an object that holds Game's important states.
     * This includes the Board, the currentPlayer, the number of points per player, and the player's racks.
     */
    public GameState(){
        this.bag = new Bag();
        this.playerManager = new PlayerManager(bag);
        this.board = new Board();
        // update it with the main method in Game
    }

    public void setPlayerManager(PlayerManager playerManager){
        this.playerManager = playerManager;
    }

    public void setBoard(Board board){
        this.board = board;
    }

    public void setBag(Bag bag){
        this.bag = bag;
    }

    public void saveGameState(String filePath){

    }

    public void loadGameState(String filePath){

    }
}
