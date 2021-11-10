package main.java;

import java.util.*;

public class Bag {

    private final List<Tile> tiles = new ArrayList<>();

    public Bag() {
        HashMap<Character, Integer> distribution = getLetterDistribution();
        for (int i = 65; i <= 90; i++) {
            int num = distribution.get((char) i);
            for (int j = 0; j <= num; j++) {
                tiles.add(new Tile((char) i));
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
        for (int k = 0; k < numTilesToSwap; k++) {
            tiles.add(tilesToSwap.get(k));
        }
        return tilesReturned;
    }

    private HashMap<Character, Integer> getLetterDistribution() {
        HashMap<Character, Integer> letterDistribution = new HashMap<>();
        for (char letter : new char[]{'J', 'K', 'Q', 'X', 'Z'}) {
            letterDistribution.put(letter, 1);
        }
        for (char letter : new char[]{'B', 'C', 'F', 'H', 'M', 'P', 'V', 'W', 'Y'}) {
            letterDistribution.put(letter, 2);
        }
        letterDistribution.put('G', 3);
        for (char letter : new char[]{'D', 'L', 'S', 'U'}) {
            letterDistribution.put(letter, 4);
        }
        for (char letter : new char[]{'N', 'R', 'T'}) {
            letterDistribution.put(letter, 6);
        }
        letterDistribution.put('O', 8);
        letterDistribution.put('A', 9);
        letterDistribution.put('I', 9);
        letterDistribution.put('E', 12);

        return letterDistribution;
    }

    public int numTilesRemaining() {
        return tiles.size();
    }
}
