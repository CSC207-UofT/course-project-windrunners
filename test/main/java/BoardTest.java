package main.java;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {
    private static Dictionary dict;
    private Board board;

    private List<Tile> charsToTiles(char[] letters) {
        List<Tile> tiles = new ArrayList<>();
        for (char letter : letters) {
            tiles.add(new Tile(Character.toUpperCase(letter)));
        }
        return tiles;
    }

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
        board.insertWord(7, 7, Board.DOWN, charsToTiles("coke".toCharArray()));
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
       board.insertWord(7, 7, Board.DOWN, charsToTiles("abject".toCharArray()));
       assertFalse(board.checkWord(7 + 1, 7 + 5, Board.DOWN, "objectively", dict));
    }

    @Test
    public void testCheckWordOverflowsEdgeRight() {
        board.insertWord(7, 7, Board.RIGHT, charsToTiles("abject".toCharArray()));
        assertFalse(board.checkWord(7 + 5, 7 + 1, Board.RIGHT, "objectively", dict));
    }

    @Test
    public void testCheckWordUnderflowsEdgeDown() {
        board.insertWord(7, 7 - 4, Board.DOWN, charsToTiles("abject".toCharArray()));
        assertFalse(board.checkWord(7 + 1, -1, Board.DOWN, "object", dict));
    }

    @Test
    public void testCheckWordUnderflowsEdgeRight() {
        board.insertWord(7 - 4, 7, Board.RIGHT, charsToTiles("abject".toCharArray()));
        assertFalse(board.checkWord(-1, 7 + 1, Board.RIGHT, "object", dict));
    }

    @Test
    public void testCheckWordOverflowsEdgeOtherDirectionDown() {
        board.insertWord(7, 7, Board.DOWN, charsToTiles("strength".toCharArray()));
        assertFalse(board.checkWord(7, 15, Board.RIGHT, "son", dict));
    }

    @Test
    public void testCheckWordOverflowsEdgeOtherDirectionRight() {
        board.insertWord(7, 7, Board.RIGHT, charsToTiles("strength".toCharArray()));
        assertFalse(board.checkWord(15, 7, Board.DOWN, "son", dict));
    }

    @Test
    public void testCheckWordUnderflowsEdgeOtherDirectionDown() {
        board.insertWord(7, 0, Board.DOWN, charsToTiles("trappers".toCharArray()));
        assertFalse(board.checkWord(7, -1, Board.RIGHT, "son", dict));
    }

    @Test
    public void testCheckWordUnderflowsEdgeOtherDirectionRight() {
        board.insertWord(7, 7, Board.RIGHT, charsToTiles("strength".toCharArray()));
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
        board.insertWord(7, 7, Board.RIGHT, charsToTiles("abject".toCharArray()));
        board.insertWord(7, 7+1, Board.DOWN, charsToTiles("qua".toCharArray()));
        assertFalse(board.checkWord(7-2, 7, Board.DOWN, "hello", dict));
    }
    @Test public void testCheckWordOnlyTouchesAtStart1() {
        board.insertWord(7, 7, Board.RIGHT, charsToTiles("at".toCharArray()));
        assertTrue(board.checkWord(7-1, 7, Board.DOWN, "cab", dict));
    }

    @Test public void testCheckWordOnlyShareLetterAtStart() {
        board.insertWord(7, 7, Board.RIGHT, charsToTiles("at".toCharArray()));
        assertTrue(board.checkWord(7, 7, Board.DOWN, "atrophy", dict));
    }

    @Test
    public void testCheckWordIncludesExistingLetters() {
        board.insertWord(7, 7, Board.RIGHT, charsToTiles("abject".toCharArray()));
        board.insertWord(7, 7+2, Board.RIGHT, charsToTiles("aqua".toCharArray()));
        assertTrue(board.checkWord(7, 7, Board.DOWN, "abacus", dict));
    }

    @Test
    public void testCheckWordAddsToExistingWords() {
        board.insertWord(7, 7, Board.RIGHT, charsToTiles("at".toCharArray()));
        board.insertWord(7, 7+2, Board.RIGHT, charsToTiles("at".toCharArray()));
        assertTrue(board.checkWord(7-1, 7, Board.DOWN, "cabaret", dict));
    }

    @Test
    public void testCheckWordCreatesInvalidWord() {
        board.insertWord(7, 7, Board.RIGHT, charsToTiles("at".toCharArray()));
        board.insertWord(7, 7+2, Board.RIGHT, charsToTiles("at".toCharArray()));
        board.insertWord(7, 7+4, Board.RIGHT, charsToTiles("at".toCharArray()));
        assertFalse(board.checkWord(7-1, 7, Board.DOWN, "braces", dict));
    }

    @Test
    public void testCheckWordLettersDontMatchUp() {
        board.insertWord(7, 7, Board.RIGHT, charsToTiles("at".toCharArray()));
        assertFalse(board.checkWord(7, 7, Board.DOWN, "braces", dict));
    }

    @Test
    public void testLettersNeeded() {
        board.insertWord(7, 7, Board.RIGHT, charsToTiles("at".toCharArray()));
        board.insertWord(7, 7+2, Board.RIGHT, charsToTiles("at".toCharArray()));
        board.insertWord(7, 7+4, Board.RIGHT, charsToTiles("uncle".toCharArray()));
        List<Character> lettersNeeded = board.lettersNeeded(7,7, Board.DOWN, "abacus");
        Collections.sort(lettersNeeded);
        assertArrayEquals(new Character[] {'b', 'c', 's'}, lettersNeeded.toArray());
    }



}
