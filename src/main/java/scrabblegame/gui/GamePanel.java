package main.java.scrabblegame.gui;

import main.java.scrabblegame.game.Game;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    private static final Renderer renderer = new Renderer();

    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public void paintComponent(Graphics g, Game currGame) {
        super.paintComponent(g);
        g.setFont(new Font("TimesRoman", Font.BOLD, 30));
        renderer.renderBoard(g, currGame.getBoard());
        renderer.renderRack(g, currGame.getCurrentPlayer());
        renderer.renderScoreboard(g, currGame.getPlayerManager());
    }
}
