package main.java;

import java.util.*;

import static java.util.Map.entry;
public class Bag {

    private final List<Tile> tiles = new ArrayList<>();

    public Bag() {
        for (Map.Entry<Character,Integer> en : LETTER_DISTRIBUTION.entrySet()) {
            for (int j = 0; j < en.getValue(); j++) {
                tiles.add(new Tile(en.getKey()));
            }
        }
    }

    public List<Tile> getTiles() { return tiles; }

    public Tile drawTile() {
        Random rnd = new Random();
        int i = rnd.nextInt(tiles.size());
        return tiles.remove(i);
    }

    public List<Tile> swapTiles(List<Tile> tilesToSwap) {
        int numTilesToSwap = tilesToSwap.size();
        List<Tile> tilesReturned = new ArrayList<>();
        for (int i = 0; i < numTilesToSwap; i++) {
            tilesReturned.add(drawTile());
        }
        tiles.addAll(tilesToSwap);
        return tilesReturned;
    }

    public int numTilesRemaining() {
        return tiles.size();
    }

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
            entry('M', 1),
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
            entry('Y', 1),
            entry('Z', 1)
    );

}
