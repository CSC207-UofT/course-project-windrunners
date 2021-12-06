package main.java.scrabblegame.game;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {
    private static Dictionary dict;
    private Board board;

    @BeforeClass
    public static void setUpDict() {
        dict = new Dictionary();
    }

    @Before
    public void setUp() {
        board = new Board();
    }

    @Test
    public void testContainsNoTilesTrue() {
        assertTrue(board.containsNoTiles());
    }

    @Test
    public void testContainsNoTilesFalse() {
        board.insertWord(7, 7, Board.DOWN, Tile.charsToTiles("coke".toCharArray()));
        assertFalse(board.containsNoTiles());
    }

    @Test
    public void testCheckWordPlacementValidPlacement() {
        assertTrue(board.checkWordPlacement(7, 7, Board.RIGHT, "hello"));
    }

    @Test
    public void testCheckWordPlacementFirstMoveRightSpot() {
        assertTrue(board.checkWordPlacement(7, 7 - 3, Board.DOWN, "hello"));
    }

    @Test
    public void testCheckWordPlacementPlacementFirstMoveWrongSpotAbove() {
        assertFalse(board.checkWordPlacement(7 - 3, 7 - 3, Board.RIGHT, "hello"));
    }

    @Test
    public void testCheckWordPlacementFirstMoveWrongSpotBelow() {
        assertFalse(board.checkWordPlacement(7 + 3, 7 + 3, Board.RIGHT, "hello"));
    }

    @Test
    public void testCheckWordPlacementFirstMoveWrongSpotTooFarLeft() {
        assertFalse(board.checkWordPlacement(7 - 3, 7, Board.RIGHT, "at"));
    }

    @Test
    public void testCheckWordPlacementFirstMoveWrongSpotTooFarRight() {
        assertFalse(board.checkWordPlacement(7 + 1, 7, Board.RIGHT, "at"));
    }

    @Test
    public void testCheckWordPlacementOverflowsEdgeDown() {
        board.insertWord(7, 7, Board.DOWN, Tile.charsToTiles("abject".toCharArray()));
        assertFalse(board.checkWordPlacement(7 + 1, 7 + 5, Board.DOWN, "objectively"));
    }

    @Test
    public void testCheckWordPlacementOverflowsEdgeRight() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("abject".toCharArray()));
        assertFalse(board.checkWordPlacement(7 + 5, 7 + 1, Board.RIGHT, "objectively"));
    }

    @Test
    public void testCheckWordPlacementUnderflowsEdgeDown() {
        board.insertWord(7, 7 - 4, Board.DOWN, Tile.charsToTiles("abject".toCharArray()));
        assertFalse(board.checkWordPlacement(7 + 1, -1, Board.DOWN, "object"));
    }

    @Test
    public void testCheckWordPlacementUnderflowsEdgeRight() {
        board.insertWord(7 - 4, 7, Board.RIGHT, Tile.charsToTiles("abject".toCharArray()));
        assertFalse(board.checkWordPlacement(-1, 7 + 1, Board.RIGHT, "object"));
    }

    @Test
    public void testCheckWordPlacementOverflowsEdgeOtherDirectionDown() {
        board.insertWord(7, 7, Board.DOWN, Tile.charsToTiles("strength".toCharArray()));
        assertFalse(board.checkWordPlacement(7, 15, Board.RIGHT, "son"));
    }

    @Test
    public void testCheckWordPlacementOverflowsEdgeOtherDirectionRight() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("strength".toCharArray()));
        assertFalse(board.checkWordPlacement(15, 7, Board.DOWN, "son"));
    }

    @Test
    public void testCheckWordPlacementUnderflowsEdgeOtherDirectionDown() {
        board.insertWord(7, 0, Board.DOWN, Tile.charsToTiles("trappers".toCharArray()));
        assertFalse(board.checkWordPlacement(7, -1, Board.RIGHT, "son"));
    }

    @Test
    public void testCheckWordPlacementUnderflowsEdgeOtherDirectionRight() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("strength".toCharArray()));
        assertFalse(board.checkWordPlacement(-1, 7, Board.DOWN, "son"));
    }

    @Test
    public void testCheckWordPlacementTopEdge() {
        assertTrue(board.checkWordPlacement(7, 0, Board.DOWN, "strength"));
    }

    @Test
    public void testCheckWordPlacementLeftEdge() {
        assertTrue(board.checkWordPlacement(0, 7, Board.RIGHT, "strength"));
    }

    @Test
    public void testCheckWordPlacementBottomEdge() {
        assertTrue(board.checkWordPlacement(7, 7, Board.DOWN, "strength"));
    }

    @Test
    public void testCheckWordPlacementRightEdge() {
        assertTrue(board.checkWordPlacement(7, 7, Board.RIGHT, "strength"));
    }

    @Test
    public void testCheckWordPlacementDoesntTouchOtherWord() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("abject".toCharArray()));
        board.insertWord(7, 7 + 1, Board.DOWN, Tile.charsToTiles("qua".toCharArray()));
        assertFalse(board.checkWordPlacement(7 - 2, 7, Board.DOWN, "hello"));
    }

    @Test
    public void testCheckWordPlacementOnlyTouchesAtStart1() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        assertTrue(board.checkWordPlacement(7 - 1, 7, Board.DOWN, "cab"));
    }

    @Test
    public void testCheckWordPlacementOnlyShareLetterAtStart() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        assertTrue(board.checkWordPlacement(7, 7, Board.DOWN, "atrophy"));
    }

    @Test
    public void testCheckWordPlacementIncludesExistingLetters() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("abject".toCharArray()));
        board.insertWord(7, 7 + 2, Board.RIGHT, Tile.charsToTiles("aqua".toCharArray()));
        assertTrue(board.checkWordPlacement(7, 7, Board.DOWN, "abacus"));
    }

    @Test
    public void testCheckWordPlacementAddsToExistingWords() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        board.insertWord(7, 7 + 2, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        assertTrue(board.checkWordPlacement(7 - 1, 7, Board.DOWN, "cabaret"));
    }

    @Test
    public void testCheckWordPlacementLettersDontMatchUp() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        assertFalse(board.checkWordPlacement(7, 7, Board.DOWN, "braces"));
    }

    @Test
    public void testCheckWordPlacementOnlyMeetAtEnd() {
        board.insertWord(7, 7, Board.DOWN, Tile.charsToTiles("tee".toCharArray()));
        assertTrue(board.checkWordPlacement(3, 10, Board.RIGHT, "nails"));
    }

    @Test
    public void testCheckWordValidValidWord() {
        assertTrue(board.checkWordValid(7, 7, Board.RIGHT, "hello", dict));
    }

    @Test
    public void testCheckWordValidInvalidWord() {
        assertFalse(board.checkWordValid(7, 7, Board.RIGHT, "ADFASD", dict));
    }

    @Test
    public void testCheckWordValidCreatesInvalidWord() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        board.insertWord(7, 7 + 2, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        board.insertWord(7, 7 + 4, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        assertFalse(board.checkWordValid(7 - 1, 7, Board.DOWN, "braces", dict));
    }

    @Test
    public void testLettersNeeded() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        board.insertWord(7, 7 + 2, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        board.insertWord(7, 7 + 4, Board.RIGHT, Tile.charsToTiles("uncle".toCharArray()));
        List<Character> lettersNeeded = board.lettersNeeded(7, 7, Board.DOWN, "abacus");
        Collections.sort(lettersNeeded);
        assertArrayEquals(new Character[]{'b', 'c', 's'}, lettersNeeded.toArray());
    }
}