package main.java;

import java.util.HashMap;

public class Tile {
    private final char letter;
    private final int value;

    public char getLetter() { return letter; }
    public int getValue() { return value; }

    public Tile(char letter) {
        this.letter = letter;
        this.value = Tile.valueDict().get(letter);
    }
    public static HashMap<Character, Integer> valueDict() {
        HashMap<Character, Integer> letterValues = new HashMap<>();
        for (char letter : new char[]{'A', 'E', 'I', 'L', 'N', 'O', 'R', 'S', 'T', 'U'}) {
            letterValues.put(letter, 1);
        }
        letterValues.put('D', 2);
        letterValues.put('G', 2);
        for (char letter : new char[]{'B', 'C', 'M', 'P'}) {
            letterValues.put(letter, 3);
        }
        for (char letter : new char[]{'F', 'H', 'V', 'W', 'Y'}) {
            letterValues.put(letter, 4);
        }
        letterValues.put('K', 5);
        letterValues.put('J', 8);
        letterValues.put('X', 8);
        letterValues.put('Q', 10);
        letterValues.put('Z', 10);
        return letterValues;
    }
}
