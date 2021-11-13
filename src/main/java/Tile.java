package main.java;

import java.awt.*;
import java.util.Map;

import static java.util.Map.entry;

public class Tile {
    private final char letter;
    private final int value;

    public char getLetter() { return letter; }
    public int getValue() { return value; }

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

    public void drawTile(Graphics g, int x, int y) {
        Font font = new Font("TimesRoman", Font.BOLD, 30);
//        Font font = new Font("TimesRoman", Font.BOLD, 12);
//        String newline = System.getProperty("line.separator");
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        g.setColor(new Color(150,75,0));
        g.fillRoundRect(40 * x, 40 * y, 40, 40, 10, 10);
        g.setColor(Color.BLACK);
        g.drawRoundRect(40 * x, 40 * y, 40, 40, 10, 10);
        String letter = String.valueOf(this.letter);
        g.drawString(String.valueOf(letter), 40 * x + (40 - metrics.stringWidth(letter)) / 2, 40 * y + (40 - metrics.getHeight()) / 2 + metrics.getAscent());
    }
}
