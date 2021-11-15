package main.java;

import java.awt.*;
/**
 * Tiles are placed on Squares, a collection of which form a Board
 */

public class Square {
    private Tile tile;
    private final int letterMult;
    private final int wordMult;
    private boolean multActive;

    public static final int SQUARE_WIDTH = 40;

    /**
     * Class constructor. tile is null when no tiles are placed on the Square
     * @param letterMult controls the proportion of points gained for every letter placed on the Square
     * @param wordMult controls the proportion of points gained when a word passed through the
     */

    public Square(int letterMult, int wordMult) {
        tile = null;
        multActive = true;
        this.letterMult = letterMult;
        this.wordMult = wordMult;
    }

    /**
     * Class constructor. tile is null when no tiles are placed on the Square
     * For Squares that don't give extra points when a word/letter is placed on them,
     * letterMult and wordMult are 1 by default
     */

    public Square() {
        tile = null;
        multActive = true;
        letterMult = 1;
        wordMult = 1;
    }

    /**
     * @return the Tile on the Square (null if the Square is empty)
     */
    public Tile getTile() { return tile; }

    /**
     * place tile on this Square
     * @param tile the Tile to be placed on Square
     */
    public void setTile(Tile tile) { this.tile = tile; }

    /**
     * @return the value of multActive
     */
    public boolean isMultActive() { return multActive; }

    /**
     * set multActive to false
     */
    public void setMultUsed() { multActive = false; }

    /**
     * @return the value of letterMult
     */
    public int getLetterMult() { return letterMult; }

    /**
     *
     * @return the value of wordMult
     */
    public int getWordMult() { return wordMult; }

    /**
     * check if the Square is empty (i.e. has no tile placed on it)
     * @return true iff the Square is empty
     */
    public boolean isEmpty() {
        return tile == null;
    }

    /**
     * @return a string representation of the Square
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            String str = "";
            if (letterMult != 1) { str += letterMult + "L"; }
            if (wordMult != 1) { str += wordMult + "W"; }
            return str;
        } else {
            return tile.getLetter() + "";
        }
    }

    public void renderSquare(Graphics g, int x, int y) {
        // this method assumes there is no tile on the square
        g.setColor(new Color(253, 173, 91));
        if (this.letterMult == 2) {
            g.setColor(new Color(174, 232, 255, 255));
        }
        if (this.letterMult == 3) {
            g.setColor(new Color(0, 130, 255));
        }
        if (this.wordMult == 2) {
            g.setColor(new Color(232, 129, 129));
        }
        if (this.wordMult == 3) {
            g.setColor(Color.red);
        }
        Font font = new Font("TimesRoman", Font.BOLD, 20);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        g.fillRect(SQUARE_WIDTH * x, SQUARE_WIDTH * y, SQUARE_WIDTH, SQUARE_WIDTH);
        g.setColor(Color.WHITE);
        g.drawRect(SQUARE_WIDTH * x, SQUARE_WIDTH * y, SQUARE_WIDTH, SQUARE_WIDTH);
        g.setColor(Color.BLACK);
        String mult = this.toString();
        g.drawString(mult, SQUARE_WIDTH * x + (SQUARE_WIDTH - metrics.stringWidth(mult)) / 2, SQUARE_WIDTH * y + (SQUARE_WIDTH - metrics.getHeight()) / 2 + metrics.getAscent());
    }
}