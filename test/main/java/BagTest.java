package main.java;

import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import static org.junit.Assert.*;

public class BagTest {
    private Bag bag;
    private int startSize;
    private int tilesToDraw;

    @Before
    public void setUp() {
        bag = new Bag();
        startSize = bag.numTilesRemaining();
        tilesToDraw = Math.floorDiv(startSize, 3);
    }

    @Test
    public void testDrawChangesNumTilesCorrectly() {
        bag.drawTiles(tilesToDraw);
        assertEquals(startSize - tilesToDraw, bag.numTilesRemaining());
    }

    @Test
    public void testDrawReturnsCorrectAmount() {
        List<Tile> tiles = bag.drawTiles(tilesToDraw);
        assertEquals(tilesToDraw, tiles.size());
    }

    @Test
    public void testDrawingToEmpty() {
        bag.drawTiles(startSize - tilesToDraw);
        List<Tile> tiles = bag.drawTiles(tilesToDraw + 20);
        assertEquals(tilesToDraw, tiles.size());
        assertEquals(0, bag.numTilesRemaining());
    }

    @Test
    public void testSwapReturnsCorrectAmount() {
        List<Tile> tiles = bag.drawTiles(tilesToDraw);
        List<Tile> swappedTiles = bag.swapTiles(tiles);
        assertEquals(tilesToDraw, swappedTiles.size());
    }

    @Test
    public void testSwapChangesNumTilesCorrectly() {
        List<Tile> tiles = bag.drawTiles(tilesToDraw);
        bag.swapTiles(tiles);
        assertEquals(bag.numTilesRemaining(), startSize - tilesToDraw);
    }

    @Test
    public void testSwapReturnsCorrect() {
        bag.drawTiles(startSize - 3);
        List<Tile> tiles = Arrays.asList(new Tile('A'), new Tile('B'), new Tile('C'));
        bag.swapTiles(tiles);
        List<Tile> drawnTiles = bag.drawTiles(3);
        List<Character> charsDrawn = new ArrayList<>();
        for (Tile tile : drawnTiles) {
            charsDrawn.add(tile.getLetter());
        }
        List<Character> c = Arrays.asList('A', 'B', 'C');
        assertTrue(charsDrawn.containsAll(c));
        assertEquals(3, c.size());
    }
}