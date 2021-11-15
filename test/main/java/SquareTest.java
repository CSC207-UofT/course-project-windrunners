package main.java;

import org.junit.Test;

import static org.junit.Assert.*;

public class SquareTest {
    @Test
    public void testSetTile() {
        Tile tile = new Tile('Z');
        Square square = new Square(1, 1);
        square.setTile(tile);
        assertEquals('Z', square.getTile().getLetter());
        assertEquals( tile.getValue(), square.getTile().getValue());
    }

    @Test
    public void testMult() {
        Square square = new Square(1, 1);
        assertTrue(square.isMultActive());
        square.setMultUsed();
        assertFalse(square.isMultActive());
        square.setMultUsed();
        assertFalse(square.isMultActive());
    }

    @Test
    public void testIsEmpty() {
        Square square = new Square(3,1);
        assertTrue(square.isEmpty());
        square.setTile(new Tile('D'));
        assertFalse(square.isEmpty());
    }

    @Test
    public void testSquareToStringOnlyWordMult() {
        Square square = new Square(1, 3);
        assertEquals("3W", square.toString());
    }

    @Test
    public void testSquareToStringOnlyLetterMult() {
        Square square = new Square(4, 1);
        assertEquals("4L", square.toString());
    }

    @Test
    public void testSquareToStringBothMult() {
        Square square = new Square(4, 0);
        assertEquals("4L0W", square.toString());
    }

    @Test
    public void testSquareToStringEmptyNoMult() {
        Square square = new Square(1, 1);
        assertEquals("", square.toString());
    }

    @Test
    public void testSquareToStringFilled() {
        Square square = new Square(4, 1);
        square.setTile(new Tile('A'));
        assertEquals("A", square.toString());
    }
}