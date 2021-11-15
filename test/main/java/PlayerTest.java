package main.java;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerTest {

    private List<Tile> charsToTiles(char[] letters) {
        List<Tile> tiles = new ArrayList<>();
        for (char letter : letters) tiles.add(new Tile(letter));
        return tiles;
    }

    @Test
    public void testAddPoints() {
        Player player = new Player("bob");
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
        Player player = new Player("bob");
        player.addTiles(charsToTiles(new char[] {'A', 'B', 'C', 'A', 'A', 'D', 'E'}));
        assertEquals(player.getRackSize(), 7);
        player.removeTile('A');
        assertEquals(player.getRackSize(), 6);
    }

    @Test
    public void testGetRackPoints() {
        Player player = new Player("bob");
        List<Tile> tiles = charsToTiles(new char[] {'A', 'B', 'C', 'A', 'A', 'D', 'E'});
        player.addTiles(tiles);
        int sum = 0;
        for (Tile tile: tiles) sum += tile.getValue();
        assertEquals(sum, player.getRackPoints());
    }

    @Test
    public void testRemoveTile() {
        Player player = new Player("bob");
        List<Tile> tiles = charsToTiles(new char[] {'H', 'I', 'J', 'H'});
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
        Player player = new Player("bob");
        List<Tile> tiles = charsToTiles(new char[] {'H', 'I', 'J', 'H', 'Y'});
        player.addTiles(tiles);
        boolean hasLetters = player.hasLetters(List.of('H', 'I', 'J', 'H', 'Y'));
        assertTrue(hasLetters);
    }

    @Test
    public void testHasLettersFalse() {
        Player player = new Player("bob");
        List<Tile> tiles = charsToTiles(new char[] {'H', 'I', 'J', 'H', 'Y'});
        player.addTiles(tiles);
        boolean hasLetters = player.hasLetters(List.of('K'));
        assertFalse(hasLetters);
    }

    @Test
    public void testHasCopiedLetter() {
        Player player = new Player("bob");
        List<Tile> tiles = charsToTiles(new char[] {'H', 'I', 'J'});
        player.addTiles(tiles);
        boolean hasLetters = player.hasLetters(Arrays.asList('J', 'J'));
        assertFalse(hasLetters);
    }

    @Test
    public void testHasTripledLetter() {
        Player player = new Player("bob");
        List<Tile> tiles = charsToTiles(new char[] {'H', 'I', 'J', 'J'});
        player.addTiles(tiles);
        boolean hasLetters = player.hasLetters(Arrays.asList('J', 'J', 'J'));
        assertFalse(hasLetters);
    }
}
