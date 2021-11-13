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
//        g.fillRect(0, 0, 200, 200);
//        Tile tile = new Tile('B');
//        for (int i = 0; i < 15; i ++)
//        tile.drawTile(g, i, 1);
//        for (int i = 0; i < 15; i ++)
//            tile.drawTile(g, 1, i);
        Board board = new Board(new Tile('A'));
        board.drawBoard(g);
    }
}
