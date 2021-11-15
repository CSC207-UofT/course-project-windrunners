package main.java;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class BagTest {

    @Test
    public void testDrawChangesNumTilesCorrectly() {
        Bag bag = new Bag();
        int startSize = bag.numTilesRemaining();
        int tilesToDraw = Math.floorDiv(startSize, 3);
        bag.drawTiles(tilesToDraw);
        assertEquals(startSize - tilesToDraw, bag.numTilesRemaining());
    }

    @Test
    public void testDrawReturnsCorrectAmount() {
        Bag bag = new Bag();
        int startSize = bag.numTilesRemaining();
        int tilesToDraw = Math.floorDiv(startSize, 3);
        List<Tile> tiles = bag.drawTiles(tilesToDraw);
        assertEquals(tilesToDraw, tiles.size());
    }

    @Test
    public void testDrawingToEmpty() {
        Bag bag = new Bag();
        int startSize = bag.numTilesRemaining();
        int tilesToDraw = Math.floorDiv(startSize, 3);
        bag.drawTiles(startSize - tilesToDraw);
        List<Tile> tiles = bag.drawTiles(tilesToDraw + 20);
        assertEquals(tilesToDraw, tiles.size());
        assertEquals(0, bag.numTilesRemaining());
    }

    @Test
    public void testSwapReturnsCorrectAmount() {
        Bag bag = new Bag();
        int startSize = bag.numTilesRemaining();
        int tilesToDraw = Math.floorDiv(startSize, 3);
        List<Tile> tiles = bag.drawTiles(tilesToDraw);
        List<Tile> swappedTiles = bag.swapTiles(tiles);
        assertEquals(tilesToDraw, swappedTiles.size());
    }

    @Test
    public void testSwapChangesNumTilesCorrectly() {
        Bag bag = new Bag();
        int startSize = bag.numTilesRemaining();
        int tilesToDraw = Math.floorDiv(startSize, 3);
        List<Tile> tiles = bag.drawTiles(tilesToDraw);
        bag.swapTiles(tiles);
        assertEquals(bag.numTilesRemaining(), startSize - tilesToDraw);
    }
}