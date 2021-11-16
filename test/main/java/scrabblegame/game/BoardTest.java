package main.java.scrabblegame.game;

import main.java.scrabblegame.game.Board;
import main.java.scrabblegame.game.Dictionary;
import main.java.scrabblegame.game.Tile;
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
        board.insertWord(7, 7+1, Board.DOWN, Tile.charsToTiles("qua".toCharArray()));
        assertFalse(board.checkWord(7-2, 7, Board.DOWN, "hello", dict));
    }
    @Test public void testCheckWordOnlyTouchesAtStart1() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        assertTrue(board.checkWord(7-1, 7, Board.DOWN, "cab", dict));
    }

    @Test public void testCheckWordOnlyShareLetterAtStart() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        assertTrue(board.checkWord(7, 7, Board.DOWN, "atrophy", dict));
    }

    @Test
    public void testCheckWordIncludesExistingLetters() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("abject".toCharArray()));
        board.insertWord(7, 7+2, Board.RIGHT, Tile.charsToTiles("aqua".toCharArray()));
        assertTrue(board.checkWord(7, 7, Board.DOWN, "abacus", dict));
    }

    @Test
    public void testCheckWordAddsToExistingWords() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        board.insertWord(7, 7+2, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        assertTrue(board.checkWord(7-1, 7, Board.DOWN, "cabaret", dict));
    }

    @Test
    public void testCheckWordCreatesInvalidWord() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        board.insertWord(7, 7+2, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        board.insertWord(7, 7+4, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        assertFalse(board.checkWord(7-1, 7, Board.DOWN, "braces", dict));
    }

    @Test
    public void testCheckWordLettersDontMatchUp() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        assertFalse(board.checkWord(7, 7, Board.DOWN, "braces", dict));
    }

    @Test
    public void testCheckWordOnlyMeetAtEnd() {
        board.insertWord(7, 7, Board.DOWN, Tile.charsToTiles("tee".toCharArray()));
        assertTrue(board.checkWord(3, 10, Board.DOWN, "nails", dict));
    }

    @Test
    public void testLettersNeeded() {
        board.insertWord(7, 7, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        board.insertWord(7, 7+2, Board.RIGHT, Tile.charsToTiles("at".toCharArray()));
        board.insertWord(7, 7+4, Board.RIGHT, Tile.charsToTiles("uncle".toCharArray()));
        List<Character> lettersNeeded = board.lettersNeeded(7,7, Board.DOWN, "abacus");
        Collections.sort(lettersNeeded);
        assertArrayEquals(new Character[] {'b', 'c', 's'}, lettersNeeded.toArray());
    }
}
