package main.java;

import java.awt.*;
import java.util.Map;

import static java.util.Map.entry;

/**
 *  Each Tile object has a letter and points associated with the letter
 */

public class Tile {
    private final char letter;
    private final int value;

    public static final int TILE_WIDTH = 40;

    public char getLetter() { return letter; }
    public int getValue() { return value; }

    /**
     * Class constructor. The points of a tile depend on its letter
     * @param letter is the letter on the tile
     */

    public Tile(char letter) {
        this.letter = letter;
        this.value = Tile.VALUE_DICT.get(letter);
    }

    public static final Map<Character, Integer> VALUE_DICT = Map.ofEntries(
            entry('A', 1),
            entry('B', 3),
            entry('C', 3),
            entry('D', 2),
            entry('E', 1),
            entry('F', 4),
            entry('G', 2),
            entry('H', 4),
            entry('I', 1),
            entry('J', 8),
            entry('K', 5),
            entry('L', 1),
            entry('M', 3),
            entry('N', 1),
            entry('O', 1),
            entry('P', 3),
            entry('Q', 10),
            entry('R', 1),
            entry('S', 1),
            entry('T', 1),
            entry('U', 1),
            entry('V', 4),
            entry('W', 4),
            entry('X', 8),
            entry('Y', 4),
            entry('Z', 10)
    );

    public void renderTile(Graphics g, int x, int y) {
        Font font = new Font("TimesRoman", Font.BOLD, 30);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        g.setColor(new Color(150,75,0));
        g.fillRoundRect(TILE_WIDTH * x, TILE_WIDTH * y, TILE_WIDTH, TILE_WIDTH, 10, 10);
        g.setColor(Color.BLACK);
        g.drawRoundRect(TILE_WIDTH * x, TILE_WIDTH * y, TILE_WIDTH, TILE_WIDTH, 10, 10);
        String letter = String.valueOf(this.letter);
        g.drawString(String.valueOf(letter), TILE_WIDTH * x + (TILE_WIDTH - metrics.stringWidth(letter)) / 2, TILE_WIDTH * y + (TILE_WIDTH - metrics.getHeight()) / 2 + metrics.getAscent());
    }
}
