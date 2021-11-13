package main.java;

import java.awt.*;

public class Square {
    private Tile tile;
    private final int letterMult;
    private final int wordMult;
    private boolean multActive;

    public Square() {
        tile = null;
        multActive = true;
        letterMult = 1;
        wordMult = 1;
    }

    public Square(int letterMult, int wordMult) {
        tile = null;
        multActive = true;
        this.letterMult = letterMult;
        this.wordMult = wordMult;
    }

    public Tile getTile() { return tile; }
    public void setTile(Tile tile) { this.tile = tile; }

    public boolean isMultActive() { return multActive; }
    public void setMultUsed() { multActive = false; }

    public int getLetterMult() { return letterMult; }
    public int getWordMult() { return wordMult; }

    public boolean isEmpty() {
        return tile == null;
    }

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

    public void drawSquare(Graphics g, int x, int y) {
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
        g.fillRect(40 * x, 40 * y, 40, 40);
        g.setColor(Color.WHITE);
        g.drawRect(40 * x, 40 * y, 40, 40);
        g.setColor(Color.BLACK);
        String mult = this.toString();
        g.drawString(mult, 40 * x + (40 - metrics.stringWidth(mult)) / 2, 40 * y + (40 - metrics.getHeight()) / 2 + metrics.getAscent());
    }
}