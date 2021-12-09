package main.java.scrabblegame.game;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
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
    public void testCheckWordValidWord() {
        assertTrue(board.checkWord(7, 7, Board.RIGHT, "hello", dict));
    }

    @Test
    public void testCheckWordInvalidWord() {
        assertFalse(board.checkWord(7, 7, Board.RIGHT, "ADFASD", dict));
    }

    @Test
    public void testCheckWordFirstMoveRightSpot() {
        assertTrue(board.checkWord(7, 7 - 3, Board.DOWN, "hello", dict));
    }

    @Test
    public void testCheckWordFirstMoveWrongSpotAbove() {
        assertFalse(board.checkWord(7 - 3, 7 - 3, Board.RIGHT, "hello", dict));
    }

    @Test
    public void testCheckWordFirstMoveWrongSpotBelow() {
        assertFalse(board.checkWord(7 + 3, 7 + 3, Board.RIGHT, "hello", dict));
    }

    @Test
    public void testCheckWordFirstMoveWrongSpotTooFarLeft() {
        assertFalse(board.checkWord(7 - 3, 7, Board.RIGHT, "at", dict));
    }

    @Test
    public void testCheckWordFirstMoveWrongSpotTooFarRight() {
        assertFalse(board.checkWord(7 + 1, 7, Board.RIGHT, "at", dict));
    }

    @Test
    public void testCheckWordOverflowsEdgeDown() {
        board.insertWord(7, 7, Board.DOWN, Tile.charsToTiles("abject".toCharArray()));
        assertFalse(board.checkWord(7 + 1, 7 + 5, Board.DOWN, "objectively", dict));
    }

    @Test
    public void testCheckWordOverflowsEdgeRight() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("abject".toCharArray()));
        assertFalse(board.checkWord(7 + 5, 7 + 1, Board.RIGHT, "objectively", dict));
    }

    @Test
    public void testCheckWordUnderflowsEdgeDown() {
        board.insertWord(7, 7 - 4, Board.DOWN, Tile.charsToTiles("abject".toCharArray()));
        assertFalse(board.checkWord(7 + 1, -1, Board.DOWN, "object", dict));
    }

    @Test
    public void testCheckWordUnderflowsEdgeRight() {
        board.insertWord(7 - 4, 7, Board.RIGHT, Tile.charsToTiles("abject".toCharArray()));
        assertFalse(board.checkWord(-1, 7 + 1, Board.RIGHT, "object", dict));
    }

    @Test
    public void testCheckWordOverflowsEdgeOtherDirectionDown() {
        board.insertWord(7, 7, Board.DOWN, Tile.charsToTiles("strength".toCharArray()));
        assertFalse(board.checkWord(7, 15, Board.RIGHT, "son", dict));
    }

    @Test
    public void testCheckWordOverflowsEdgeOtherDirectionRight() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("strength".toCharArray()));
        assertFalse(board.checkWord(15, 7, Board.DOWN, "son", dict));
    }

    @Test
    public void testCheckWordUnderflowsEdgeOtherDirectionDown() {
        board.insertWord(7, 0, Board.DOWN, Tile.charsToTiles("trappers".toCharArray()));
        assertFalse(board.checkWord(7, -1, Board.RIGHT, "son", dict));
    }

    @Test
    public void testCheckWordUnderflowsEdgeOtherDirectionRight() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("strength".toCharArray()));
        assertFalse(board.checkWord(-1, 7, Board.DOWN, "son", dict));
    }

    @Test
    public void testCheckWordTopEdge() {
        assertTrue(board.checkWord(7, 0, Board.DOWN, "strength", dict));
    }

    @Test
    public void testCheckWordLeftEdge() {
        assertTrue(board.checkWord(0, 7, Board.RIGHT, "strength", dict));
    }

    @Test
    public void testCheckWordBottomEdge() {
        assertTrue(board.checkWord(7, 7, Board.DOWN, "strength", dict));
    }

    @Test
    public void testCheckWordRightEdge() {
        assertTrue(board.checkWord(7, 7, Board.RIGHT, "strength", dict));
    }

    @Test
    public void testCheckWordDoesntTouchOtherWord() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("abject".toCharArray()));
        board.insertWord(7, 7 + 1, Board.DOWN, Tile.charsToTiles("qua".toCharArray()));
        assertFalse(board.checkWord(7 - 2, 7, Board.DOWN, "hello", dict));
    }

    @Test
    public void testCheckWordOnlyTouchesAtStart1() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        assertTrue(board.checkWord(7 - 1, 7, Board.DOWN, "cab", dict));
    }

    @Test
    public void testCheckWordOnlyShareLetterAtStart() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        assertTrue(board.checkWord(7, 7, Board.DOWN, "atrophy", dict));
    }

    @Test
    public void testCheckWordIncludesExistingLetters() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("abject".toCharArray()));
        board.insertWord(7, 7 + 2, Board.RIGHT, Tile.charsToTiles("aqua".toCharArray()));
        assertTrue(board.checkWord(7, 7, Board.DOWN, "abacus", dict));
    }

    @Test
    public void testCheckWordAddsToExistingWords() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        board.insertWord(7, 7 + 2, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        assertTrue(board.checkWord(7 - 1, 7, Board.DOWN, "cabaret", dict));
    }

    @Test
    public void testCheckWordCreatesInvalidWord() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        board.insertWord(7, 7 + 2, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        board.insertWord(7, 7 + 4, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        assertFalse(board.checkWord(7 - 1, 7, Board.DOWN, "braces", dict));
    }

    @Test
    public void testCheckWordLettersDontMatchUp() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        assertFalse(board.checkWord(7, 7, Board.DOWN, "braces", dict));
    }

    @Test
    public void testCheckWordOnlyMeetAtEnd() {
        board.insertWord(7, 7, Board.DOWN, Tile.charsToTiles("tee".toCharArray()));
        assertTrue(board.checkWord(7-4, 7+3, Board.RIGHT, "nails", dict));
    }
    @Test
    public void testCheckWordOnlyMeetAtEnd2() {
        board.insertWord(7, 7, Board.DOWN, Tile.charsToTiles("seats".toCharArray()));
        assertTrue(board.checkWord(7-4, 7+4, Board.RIGHT, "wears", dict));
    }

    @Test
    public void testCheckWordLetterRightBeforeWord() {
        board.insertWord(7, 7-3, Board.DOWN, Tile.charsToTiles("barn".toCharArray()));
        board.insertWord(6, 7, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        assertFalse(board.checkWord(8,6, Board.RIGHT, "attic", dict));
    }

    @Test
    public void testCheckWordLetterRightAfterWord() {
        board.insertWord(7-2, 7, Board.RIGHT, Tile.charsToTiles("abandon".toCharArray()));
        assertFalse(board.checkWord(7+2,7-3, Board.DOWN, "ban", dict));
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

    @Test
    public void testFindWordFormedByTilesInvalidPlacements() {
        List<Tile> tiles = Tile.charsToTiles("atc".toCharArray());
        List<Integer> coordinates = new ArrayList<>(Arrays.asList(1, 2, 4));
        assertEquals(board.findWordFormedByTiles(tiles, coordinates, 2, Board.RIGHT).size(), 0);
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("hat".toCharArray()));
        board.insertWord(7, 7, Board.DOWN, Tile.charsToTiles("at".toCharArray()));
        board.insertWord(7, 9, Board.DOWN, Tile.charsToTiles("ile".toCharArray()));
        coordinates = new ArrayList<>(Arrays.asList(6, 8, 11));
        assertEquals(board.findWordFormedByTiles(tiles, coordinates, 9, Board.RIGHT).size(), 0);
    }

    @Test
    public void testFindWordFormedByTilesValidPlacements() {
        List<Tile> tiles = Tile.charsToTiles("ars".toCharArray());
        List<Integer> coordinates = new ArrayList<>(Arrays.asList(8, 10, 12));
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("hates".toCharArray()));
        board.insertWord(7, 7, Board.DOWN, Tile.charsToTiles("at".toCharArray()));
        board.insertWord(9, 7, Board.DOWN, Tile.charsToTiles("ile".toCharArray()));
        board.insertWord(11, 7, Board.DOWN, Tile.charsToTiles("ap".toCharArray()));
        assertEquals(board.findWordFormedByTiles(tiles, coordinates, 9, Board.RIGHT).size(), 3);
        assertEquals(board.findWordFormedByTiles(tiles, coordinates, 9, Board.RIGHT).get(2), "TALRPS");
        assertEquals(board.findWordFormedByTiles(tiles, coordinates, 9, Board.RIGHT).get(0), 9);
        assertEquals(board.findWordFormedByTiles(tiles, coordinates, 9, Board.RIGHT).get(1), 7);
    }

    @Test
    public void testTilesInSameRowOrColumn() {
        List<Integer> rows = new ArrayList<>(List.of(2));
        List<Integer> cols = new ArrayList<>(List.of(4));
        assertEquals(board.tilesInSameRowOrColumn(rows, cols), "row");
        rows = new ArrayList<>(Arrays.asList(4, 4, 4, 4, 5));
        cols = new ArrayList<>(List.of(3, 3, 3, 3, 3));
        assertEquals(board.tilesInSameRowOrColumn(rows, cols), "col");
        rows = new ArrayList<>(Arrays.asList(4, 4, 4, 4, 4));
        cols = new ArrayList<>(List.of(3, 3, 2, 2, 3));
        assertEquals(board.tilesInSameRowOrColumn(rows, cols), "row");
        rows = new ArrayList<>(Arrays.asList(1, 4, 4, 4, 4));
        cols = new ArrayList<>(List.of(3, 3, 3, 3, 2));
        assertEquals(board.tilesInSameRowOrColumn(rows, cols), "none");
    }


}
