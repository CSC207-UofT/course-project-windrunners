package main.java;

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
}
