package main.java.scrabblegame.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

/**
 * Each Tile object has a letter and points associated with the letter
 *
 * The WildCard Tile represented by '~' has 0 points associated with it,
 * however, it can be used as a proxy for any letter in the game
 *
 */

public class Tile {
    private char letter;
    private int value;

    public char getLetter() {
        return letter;
    }

    public int getValue() {
        return value;
    }

    public void setLetter(char letter){
        this.letter = letter;
    }

    public void setValue(int value){
        this.value = value;
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
            entry('Z', 10),
            entry('~', 0)
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

    /**
     * Overriding equals method to check for reference equality. Useful when checking if a tile belongs to a list
     * @param tile1 the first tile to be compared for reference equality
     * @param tile2 the first tile to be compared for reference equality
     * @return true iff tile1 and tile2 have the same reference
     */
    public boolean equals(Tile tile1, Tile tile2) {
        return tile1 == tile2;
    }
}
