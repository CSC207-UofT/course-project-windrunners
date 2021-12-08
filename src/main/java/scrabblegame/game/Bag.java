package main.java.scrabblegame.game;

import java.util.*;

import static java.util.Map.entry;

/**
 * A Bag is a collection of Tiles that is used to play Scrabble
 */
public class Bag {

    private static final Map<Character, Integer> LETTER_DISTRIBUTION = Map.ofEntries(
            entry('A', 9),
            entry('B', 2),
            entry('C', 2),
            entry('D', 4),
            entry('E', 12),
            entry('F', 2),
            entry('G', 3),
            entry('H', 2),
            entry('I', 9),
            entry('J', 1),
            entry('K', 1),
            entry('L', 4),
            entry('M', 2),
            entry('N', 6),
            entry('O', 8),
            entry('P', 2),
            entry('Q', 1),
            entry('R', 6),
            entry('S', 4),
            entry('T', 6),
            entry('U', 4),
            entry('V', 2),
            entry('W', 2),
            entry('X', 1),
            entry('Y', 2),
            entry('Z', 1),
            entry('~', 2)
    );
    private final List<Tile> tiles = new ArrayList<>();

    /**
     * Class Constructor. Creates new Tile objects and adds them to the Bag
     */
    public Bag() {
        for (Map.Entry<Character, Integer> en : LETTER_DISTRIBUTION.entrySet()) {
            for (int j = 0; j < en.getValue(); j++) {
                tiles.add(new Tile(en.getKey()));
            }
        }
    }

    /**
     * CLass Constructor. Creates new Tile objects and adds them to the Bag
     *
     * @param letters the letters of the tiles in the bag (from the saved GameState)
     */
    public Bag(char[] letters) {
        for (char letter : letters) {
            tiles.add(new Tile(letter));
        }
    }

    /**
     * Copy constructor. Makes a deep copy of the Bag.
     *
     * @param that the Bag to copy from
     */
    public Bag(Bag that) {
        tiles.addAll(that.getTiles());
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    /**
     * @return an ArrayList with just the letters of the tiles that are left in the bag (with repeats)
     */
    public ArrayList<Character> getBagLetters() {
        ArrayList<Character> bagLetters = new ArrayList<>();
        for (Tile tile : tiles) {
            bagLetters.add(tile.getLetter());
        }
        return bagLetters;
    }

    /**
     * @return an Integer representing number of the tiles that are left in the bag
     */

    public int getNumOfLettersInBag(){
        return this.getBagLetters().size();

    }

    /**
     * draw a random tile from the Bag
     *
     * @return the tile drawn from the Bag. Returns null if empty.
     */
    private Tile drawTile() {
        if (tiles.size() == 0) return null;
        Random rnd = new Random();
        int i = rnd.nextInt(tiles.size());
        return tiles.remove(i);
    }

    /**
     * draw a random number of tiles from the Bag
     *
     * @param num the number of tiles to draw
     * @return the List of Tiles drawn from the Bag.
     */
    public List<Tile> drawTiles(int num) {
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < num && this.tiles.size() > 0; i++) {
            tiles.add(drawTile());
        }
        return tiles;
    }

    /**
     * swap tiles with the bag
     *
     * @param tilesToSwap is the List of tiles that are to be swapped with tiles in the Bag.
     *                    Should always have tilesToSwap.size() <= tiles.size()
     * @return the List of tiles returned from the Bag
     */
    public List<Tile> swapTiles(List<Tile> tilesToSwap) {
        int numTilesToSwap = tilesToSwap.size();
        List<Tile> tilesReturned = drawTiles(numTilesToSwap);
        tiles.addAll(tilesToSwap);
        return tilesReturned;
    }

    /**
     * @return the number of Tiles remaining in the Bag
     */
    public int numTilesRemaining() {
        return tiles.size();
    }

}

