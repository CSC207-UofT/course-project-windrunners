package main.java.scrabblegame.game.elements;

import main.java.scrabblegame.game.elements.Player;
import main.java.scrabblegame.game.elements.Tile;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class PlayerTest {
    private Player player;

    @Before
    public void setUp() {
        player = new Player("bob", "human");
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
        for (Tile tile : tiles) sum += tile.getValue();
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
    public void testRemoveTilesTooMany() {
        List<Character> addLetters = Arrays.asList('H', 'H', 'I', 'J');
        List<Tile> tiles = Tile.charsToTiles(addLetters);
        player.addTiles(tiles);
        List<Character> letters = Arrays.asList('H', 'I', 'J', 'H', 'Y');
        List<Tile> removedTiles = player.removeTiles(letters);
        List<Character> removedLetters = Tile.tilesToChars(removedTiles);
        Collections.sort(removedLetters);
        assertEquals(addLetters, removedLetters);
    }

    @Test
    public void testRemoveTilesNotEvery() {
        List<Tile> tiles = Tile.charsToTiles("ABAABC".toCharArray());
        player.addTiles(tiles);
        List<Character> letters = Arrays.asList('A', 'A');
        List<Tile> removedTiles = player.removeTiles(letters);
        List<Character> removedLetters = Tile.tilesToChars(removedTiles);
        assertEquals(letters, removedLetters);
    }

    @Test
    public void testHasLettersTrue() {
        List<Tile> tiles = Tile.charsToTiles("HIJHY".toCharArray());
        player.addTiles(tiles);
        boolean hasLetters = player.hasLetters(Arrays.asList('H', 'I', 'J', 'H', 'Y'));
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
