package main.java.scrabblegame.gui;

import main.java.scrabblegame.game.Game;

import javax.swing.*;
import java.awt.*;

/**
 * The class representing the visual component of the GUI
 */
public class GamePanel extends JPanel {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    private static Game currGame;
    private static final Renderer renderer = new Renderer();

    /**
     * Class constructor for the GamePanel (the screen showing the Scrabble game that is being played)
     * @param game the game that is currently being played
     */
    public GamePanel(Game game) {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        currGame = game;
    }
    
    /**
     * Paints all the visual components required for the game on the GamePanel
     * @param g the graphics object used to create the visual components of the board
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("TimesRoman", Font.BOLD, 30));
        renderer.renderBoard(g, currGame.getBoard());
        renderer.renderRack(g, currGame.getCurrentPlayer());
        renderer.renderScoreboard(g, currGame.getPlayerManager());
        renderer.renderCompleteMoveBox(g, currGame.getInputHandler().isAMoveMade());
        renderer.renderSwapMoveBox(g, !currGame.getInputHandler().placeMoveBeingMade() &&
                !currGame.getInputHandler().getSwapMove());
        renderer.renderTilesToSwap(g, currGame.getInputHandler().getTilesToSwap(),
                currGame.getInputHandler().getSwapMove());
        renderer.renderPassMoveBox(g, currGame.getInputHandler().checkIfAccumulatorsResetted());
        renderer.renderCancelSwapMoveBox(g, currGame.getInputHandler().getSwapMove());
        if (currGame.getInputHandler().getSelectingWildcard()) {
            renderer.renderWildcardSelection(g);
        }
        if (currGame.getInputHandler().getChallengeActive()) {
            renderer.renderChallengeSelection(g);
        }
    }
}
