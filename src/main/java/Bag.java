package main.java;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Bag {

    private Tile[] tiles;

    public Bag() {
        tiles = new Tile[100];
        HashMap<Character, Integer> distribution = getLetterDistribution();
        int index = 0;
        for (int i = 65; i <= 90; i++) {
            int num = distribution.get((char) i);
            for (int j = 0; j <= num; j++) {
                tiles[index] = new Tile((char) i);
                index++;
            }
        }

        Random rnd = new Random();
        for (int i = tiles.length - 1; i > 0; i--)
        {
            index = rnd.nextInt(i + 1);
            Tile a = tiles[index];
            tiles[index] = tiles[i];
            tiles[i] = a;
        }

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
