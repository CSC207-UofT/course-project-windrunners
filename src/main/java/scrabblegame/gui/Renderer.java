package main.java.scrabblegame.gui;

import main.java.scrabblegame.game.Player;
import main.java.scrabblegame.game.PlayerManager;
import main.java.scrabblegame.game.Board;
import main.java.scrabblegame.game.Square;
import main.java.scrabblegame.game.Tile;

import java.util.List;

import java.awt.*;

/**
 * Class that renders all the components required for the GUI.
 */
public class Renderer {

    public static final int TILE_SIZE = 40;
    public static final int SQUARE_SIZE = 40;

    /**
     * Renders a tile in the GUI
     * @param g Graphics object used to create visual components in GUI
     * @param tile the tile we want to render
     * @param x the x-coordinate of the tile we want to render
     * @param y the y-coordinate of the tile we want to render
     */
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

    /**
     * Renders a square (on the board) in the GUI
     * @param g Graphics object used to create visual components in GUI
     * @param square the square (on the board) we want to render
     * @param x the x-coordinate of the tile we want to render
     * @param y the y-coordinate of the tile we want to render
     */
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

    /**
     * Renders a Scrabble board in the GUI
     * @param g Graphics object used to create visual components in GUI
     * @param board the board we want to render
     */
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

    /**
     * Renders a player's rack in the GUI
     * @param g Graphics object used to create visual components in GUI
     * @param player the player whose rack we want to render
     */
    public void renderRack(Graphics g, Player player) {
        g.drawString(player.getName() + "'s rack:", 40, 16 * 40 + 20);
        for (int i = 0; i < player.getRackSize(); i++) {
            renderTile(g, player.getRack().get(i), i + 5, 16);
        }
    }

    /**
     * Renders the scoreboard, containing the players' names and the number of points they have accumulated so far
     * @param g Graphics object used to create visual components in GUI
     * @param playerManager the playerManager that has all the information on the players needed to render the
     *                      scoreboard
     */
    public void renderScoreboard(Graphics g, PlayerManager playerManager) {
        Player[] players = playerManager.getPlayers();
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            g.drawString(player.getName() + ": " + player.getPoints(), 600, 100 + 40 * i);
        }
    }

    /**
     * Renders the complete move box
     * @param g Graphics object used to create visual components in GUI
     * @param shouldItRender whether or not the box should render
     */
    public void renderCompleteMoveBox(Graphics g, boolean shouldItRender) {
        if (shouldItRender) {
            g.setColor(new Color(255, 167, 61));
            int x = Board.BOARD_WIDTH * SQUARE_SIZE;
            int y = (Board.BOARD_WIDTH + 1) * SQUARE_SIZE;
            renderRectangleBox(g, x, y, "Complete Move");
        }
    }

    /**
     * Renders the swap move box
     * @param g Graphics object used to create visual components in GUI
     * @param shouldItRender whether or not the box should render
     */
    public void renderSwapMoveBox(Graphics g, boolean shouldItRender) {
        if (shouldItRender) {
            g.setColor(new Color(2, 167, 61));
            int x = Board.BOARD_WIDTH * SQUARE_SIZE;
            int y = (Board.BOARD_WIDTH - 1) * SQUARE_SIZE;
            renderRectangleBox(g, x, y, "Swap Move");
        }
    }

    /**
     * Renders the cancel swap move box
     * @param g Graphics object used to create visual components in GUI
     * @param isSwapMove whether or not the user was attempting to make a swap move before trying to cancel their move
     */
    public void renderCancelSwapMoveBox(Graphics g, boolean isSwapMove) {
        if (isSwapMove) {
            g.setColor(new Color(167, 2, 2));
            int x = Board.BOARD_WIDTH * SQUARE_SIZE;
            int y = (Board.BOARD_WIDTH + 3) * SQUARE_SIZE;
            renderRectangleBox(g, x, y, "Cancel Swap Move");
        }
    }

    /**
     * Renders the tiles that the user has selected to swap
     * @param g Graphics object used to create visual components in GUI
     * @param isSwapMove whether or not the user was attempting to make a swap move before trying to cancel their move
     */
    public void renderTilesToSwap(Graphics g, List<Tile> tiles, boolean isSwapMove) {
        if (isSwapMove) {
            g.drawString("Tiles To Swap", 40, 18 * 40 + 20);
            for (int i = 0; i < 7; i++) {
                if (i < tiles.size()) {
                    renderTile(g, tiles.get(i), i + 5, 18);
                }
                else {
                    renderSquare(g, new Square(), i + 5, 18);
                }
            }
        }
    }

    /**
     * Renders the pass move box
     * @param g Graphics object used to create visual components in GUI
     * @param shouldItRender whether or not the box should render
     */
    public void renderPassMoveBox(Graphics g, boolean shouldItRender) {
        if (shouldItRender) {
            g.setColor(new Color(2, 176, 189));
            int x = Board.BOARD_WIDTH * SQUARE_SIZE;
            int y = (Board.BOARD_WIDTH - 3) * SQUARE_SIZE;
            renderRectangleBox(g, x, y, "Pass Move");
        }
    }

    /**
     * Renders the prompt that allows the user to select the letter they wish to insert for a wildcard
     * @param g Graphics object used to create visual components in GUI
     */
    public void renderWildcardSelection(Graphics g) {
        g.drawString("Which Letter Would You Like to Use for your Wildcard Tile", 0, (Board.BOARD_WIDTH + 2) * TILE_SIZE);
        for (int i = 0; i < 26; i++) {
            renderTile(g, new Tile((char) (i + 65)), i % 13, Board.BOARD_WIDTH + 3 + (i / 13));
        }
    }

    /**
     * Renders a general rectangular box
     * @param g Graphics object used to create visual components in GUI
     * @param x the x-coordinate of the tile we want to render
     * @param y the y-coordinate of the tile we want to render
     * @param text the text that we want to include in the rendered box
     */
    private void renderRectangleBox(Graphics g, int x, int y, String text) {
        g.fillRect(x, y,3 * SQUARE_SIZE, SQUARE_SIZE);
        g.setColor(Color.BLACK);
        Font font = new Font("TimesRoman", Font.BOLD, 12);
        g.setFont(font);
        g.drawString(text, x + SQUARE_SIZE / 2, y + SQUARE_SIZE / 2);
    }
}
