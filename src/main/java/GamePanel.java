package main.java;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("TimesRoman", Font.BOLD, 30));
        Game.getBoard().renderBoard(g);
        Game.getCurrentPlayer().renderRack(g);
        Game.getPlayerManager().renderScoreboard(g);
    }
}
