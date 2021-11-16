package main.java.scrabblegame.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

/**
 * Each Tile object has a letter and points associated with the letter
 */

public class Tile {
    private final char letter;
    private final int value;

    public char getLetter() {
        return letter;
    }

    public int getValue() {
        return value;
    }

    /**
     * Class constructor. The points of a tile depend on its letter
     *
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

    /**
     * Takes a List of Characters and returns a List of tiles with those Characters.
     *
     * @param letters the List of Characters to be converted into a List of Tiles
     * @return a List of Tiles
     */
    public static List<Tile> charsToTiles(List<Character> letters) {
        List<Tile> tiles = new ArrayList<>();
        for (char letter : letters) {
            tiles.add(new Tile(Character.toUpperCase(letter)));
        }
        return tiles;
    }

    /**
     * Takes an array of chars and returns a List of tiles with those chars.
     *
     * @param letters the array of chars[] to be converted into Tiles
     * @return a List of Tiles
     */
    public static List<Tile> charsToTiles(char[] letters) {
        List<Tile> tiles = new ArrayList<>();
        for (char letter : letters) {
            tiles.add(new Tile(Character.toUpperCase(letter)));
        }
        return tiles;
    }

    /**
     * Takes a List of Tiles and returns a List of Characters with the letters from those tiles.
     *
     * @param tiles the List of Tiles to be converted into Characters
     * @return a List of Characters
     */
    public static List<Character> tilesToChars(List<Tile> tiles) {
        List<Character> letters = new ArrayList<>();
        for (Tile tile : tiles) {
            letters.add(tile.getLetter());
        }
        return letters;
    }
}
