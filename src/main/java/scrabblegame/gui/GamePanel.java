package main.java.scrabblegame.gui;

import main.java.scrabblegame.game.Game;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    private static Game currGame;
    private static final Renderer renderer = new Renderer();

    public GamePanel(Game game) {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        currGame = game;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("TimesRoman", Font.BOLD, 30));
        renderer.renderBoard(g, currGame.getBoard());
        renderer.renderRack(g, currGame.getCurrentPlayer());
        renderer.renderScoreboard(g, currGame.getPlayerManager());
    }
}
