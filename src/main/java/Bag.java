package main.java;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Bag {

    private List<Tile> tiles;

    public Bag() {
        tiles = new ArrayList<>();
        HashMap<Character, Integer> distribution = getLetterDistribution();
        for (int i = 65; i <= 90; i++) {
            int num = distribution.get((char) i);
            for (int j = 0; j <= num; j++) {
                tiles.add(new Tile((char) i));
            }
        }
        Collections.shuffle(tiles);
    }

    public HashMap getLetterDistribution() {
        HashMap<Character, Integer> letterDistribution = new HashMap<Character, Integer>();
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
}
