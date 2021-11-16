package main.java.scrabblegame.gui;

import main.java.scrabblegame.game.Player;
import main.java.scrabblegame.game.PlayerManager;
import main.java.scrabblegame.game.Board;
import main.java.scrabblegame.game.Square;
import main.java.scrabblegame.game.Tile;

import java.awt.*;

public class Renderer {

    public static final int TILE_SIZE = 40;
    public static final int SQUARE_SIZE = 40;

    public void renderTile(Graphics g, Tile tile, int x, int y) {
        Font font = new Font("TimesRoman", Font.BOLD, 30);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        g.setColor(new Color(150, 75, 0));
        g.fillRoundRect(TILE_SIZE * x, TILE_SIZE * y, TILE_SIZE, TILE_SIZE, 10, 10);
        g.setColor(Color.BLACK);
        g.drawRoundRect(TILE_SIZE * x, TILE_SIZE * y, TILE_SIZE, TILE_SIZE, 10, 10);
        String letter = String.valueOf(tile.getLetter());
        g.drawString(letter, TILE_SIZE * x + (TILE_SIZE - metrics.stringWidth(letter)) / 2, TILE_SIZE * y + (TILE_SIZE - metrics.getHeight()) / 2 + metrics.getAscent());
    }

    public void renderSquare(Graphics g, Square square, int x, int y) {
        // this method assumes there is no tile on the square
        g.setColor(new Color(253, 173, 91));
        if (square.getLetterMult() == 2) {
            g.setColor(new Color(174, 232, 255, 255));
        }
        if (square.getLetterMult() == 3) {
            g.setColor(new Color(0, 130, 255));
        }
        if (square.getWordMult() == 2) {
            g.setColor(new Color(232, 129, 129));
        }
        if (square.getWordMult() == 3) {
            g.setColor(Color.red);
        }
        Font font = new Font("TimesRoman", Font.BOLD, 20);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        g.fillRect(SQUARE_SIZE * x, SQUARE_SIZE * y, SQUARE_SIZE, SQUARE_SIZE);
        g.setColor(Color.WHITE);
        g.drawRect(SQUARE_SIZE * x, SQUARE_SIZE * y, SQUARE_SIZE, SQUARE_SIZE);
        g.setColor(Color.BLACK);
        String mult = square.toString();
        g.drawString(mult, SQUARE_SIZE * x + (SQUARE_SIZE - metrics.stringWidth(mult)) / 2, SQUARE_SIZE * y + (SQUARE_SIZE - metrics.getHeight()) / 2 + metrics.getAscent());
    }

    public void renderBoard(Graphics g, Board board) {
        for (int i = 0; i < Board.BOARD_WIDTH; i++) {
            for (int j = 0; j < Board.BOARD_WIDTH; j++) {
                if (board.getBoard()[j][i].getTile() == null) {
                    renderSquare(g, board.getBoard()[j][i], i, j);
                } else {
                    renderTile(g, board.getBoard()[j][i].getTile(), i, j);
                }
            }
        }
    }

    public void renderRack(Graphics g, Player player) {
        g.drawString(player.getName() + "'s rack:", 40, 16 * 40 + 20);
        for (int i = 0; i < player.getRackSize(); i++) {
            renderTile(g, player.getRack().get(i), i + 5, 16);
        }
    }

    public void renderScoreboard(Graphics g, PlayerManager playerManager) {
        Player[] players = playerManager.getPlayers();
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            g.drawString(player.getName() + ": " + player.getPoints(), 600, 100 + 40 * i);
        }
    }
}
