package main.java;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Game class which controls the Game
 */
public class Game {
    private static Board board = new Board();
    private static Bag bag = new Bag();
    private static PlayerManager playerManager = new PlayerManager(System.in, System.out, bag);

    /**
     * The main method. Sets up and controls the state of the Game.
     * The Game ends when the Bag empties.
     */
    public static void main(String[] args) {
        JFrame window = new JFrame("Scrabble");
        GamePanel gamePanel = new GamePanel();
        window.setContentPane(gamePanel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        Dictionary dictionary = new Dictionary();
        System.out.println(bag.numTilesRemaining());

        while (bag.numTilesRemaining() > 0) {
            gamePanel.repaint();
            Move move = playerManager.getNextMove(System.in, System.out, board, bag.numTilesRemaining());
            move.execute(bag, playerManager, board, dictionary, System.out);
            playerManager.goToNextPlayer();
        }
        Player winner = playerManager.getLeader();
        System.out.println("Congratulations " + winner.getName() + "! You won with " + winner.getPoints() + " points");
    }

    public static Board getBoard() {
        return board;
    }

    public static Player getCurrentPlayer() {
        return playerManager.getCurrentPlayer();
    }

    public static PlayerManager getPlayerManager() {
        return playerManager;
    }
}
