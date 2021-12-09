package main.java.scrabblegame.game;
import org.junit.Test;
import org.junit.Before;

import java.util.List;

import static org.junit.Assert.*;

public class SwapMoveTest {
    private Bag bag;
    private Player player;
    private Board board;
    private Dictionary dict;

    @Before
    public void setUp() {
        bag = new Bag();
        player = new Player("John");
        List<Tile> tiles = Tile.charsToTiles("STRJJJE".toCharArray());
        player.addTiles(tiles);
        board = new Board();
        dict = new Dictionary();
    }

    @Test
    public void testWhenPlayerDoesntHaveTilesToSwap() {
        SwapMove swapMove = new SwapMove(Tile.charsToTiles("JJJSS".toCharArray()));
        swapMove.execute(bag, player, board, dict);
        List<Character> chars = Tile.tilesToChars(Tile.charsToTiles("JJJ".toCharArray()));
        assertTrue(player.hasLetters(chars));
        // if tiles were swapped then the Player's rack will contain at most one J
        // since the bag has only 1 J
    }

    @Test
    public void testWhenPlayerDoesHaveTilesToSwap() {
        SwapMove swapMove = new SwapMove(Tile.charsToTiles("JJJES".toCharArray()));
        swapMove.execute(bag, player, board, dict);
        List<Character> chars = Tile.tilesToChars(Tile.charsToTiles("JJJ".toCharArray()));
        assertFalse(player.hasLetters(chars));
        // if tiles were swapped then the Player's rack will contain at most one J
        // since the bag has only 1 J
    }
}