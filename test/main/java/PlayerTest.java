package main.java;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerTest {
    private Player player;
    @Before
    public void setUp() {
        player = new Player("bob");
    }
    @Test
    public void testAddPoints() {
        int[] pointsToAdd = {1, 32, 3431, 534, 12323};
        int sum = 0;
        for (int points : pointsToAdd) {
            player.addPoints(points);
            sum += points;
        }
        assertEquals(player.getPoints(), sum);
    }

    @Test
    public void testRackHasCorrectSize() {

        player.addTiles(Tile.charsToTiles("ABCAADE".toCharArray()));
        assertEquals(player.getRackSize(), 7);
        player.removeTile('A');
        assertEquals(player.getRackSize(), 6);
    }

    @Test
    public void testGetRackPoints() {
        List<Tile> tiles = Tile.charsToTiles("ABCAADE".toCharArray());
        player.addTiles(tiles);
        int sum = 0;
        for (Tile tile: tiles) sum += tile.getValue();
        assertEquals(sum, player.getRackPoints());
    }

    @Test
    public void testRemoveTile() {
        List<Tile> tiles = Tile.charsToTiles("HIJH".toCharArray());
        player.addTiles(tiles);
        Tile t1 = player.removeTile('H');
        Tile t2 = player.removeTile('H');
        Tile t3 = player.removeTile('H');
        boolean playerHasH = player.hasLetters(List.of('H'));
        assertEquals('H', t1.getLetter());
        assertEquals('H', t2.getLetter());
        assertNull(t3);
        assertFalse(playerHasH);
    }

    @Test
    public void testHasLettersTrue() {
        List<Tile> tiles = Tile.charsToTiles("HIJHY".toCharArray());
        player.addTiles(tiles);
        boolean hasLetters = player.hasLetters(List.of('H', 'I', 'J', 'H', 'Y'));
        assertTrue(hasLetters);
    }

    @Test
    public void testHasLettersFalse() {
        List<Tile> tiles = Tile.charsToTiles("HIJHY".toCharArray());
        player.addTiles(tiles);
        boolean hasLetters = player.hasLetters(List.of('K'));
        assertFalse(hasLetters);
    }

    @Test
    public void testHasCopiedLetter() {
        List<Tile> tiles = Tile.charsToTiles("HIJ".toCharArray());
        player.addTiles(tiles);
        boolean hasLetters = player.hasLetters(Arrays.asList('J', 'J'));
        assertFalse(hasLetters);
    }

    @Test
    public void testHasTripledLetter() {
        List<Tile> tiles = Tile.charsToTiles("HIJJ".toCharArray());
        player.addTiles(tiles);
        boolean hasLetters = player.hasLetters(Arrays.asList('J', 'J', 'J'));
        assertFalse(hasLetters);
    }
}
