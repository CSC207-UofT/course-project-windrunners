package main.java.scrabblegame.game;
import org.junit.Test;
import org.junit.Before;

import java.util.List;

import static org.junit.Assert.*;

public class SwapMoveTest {
    private Bag bag;
    private PlayerManager pm;
    private Board board;
    private Dictionary dict;

    @Before
    public void setUp() {
        bag = new Bag();
        Player player = new Player("John");
        List<Tile> tiles = Tile.charsToTiles("STRJJJE".toCharArray());
        player.addTiles(tiles);
        Player[] players = {player};
        pm = new PlayerManager(players, 0);
        board = new Board();
        dict = new Dictionary();
    }

    @Test
    public void testWhenPlayerDoesntHaveTilesToSwap() {
        SwapMove swapMove = new SwapMove(Tile.charsToTiles("JJJSS".toCharArray()));
        swapMove.execute(bag, pm, board, dict);
        List<Character> chars = Tile.tilesToChars(Tile.charsToTiles("JJJ".toCharArray()));
        assertTrue(pm.currentPlayerHasLetters(chars));
        // if tiles were swapped then the Player's rack will contain at most one J
        // since the bag has only 1 J
    }

    @Test
    public void testWhenPlayerDoesHaveTilesToSwap() {
        SwapMove swapMove = new SwapMove(Tile.charsToTiles("JJJES".toCharArray()));
        swapMove.execute(bag, pm, board, dict);
        List<Character> chars = Tile.tilesToChars(Tile.charsToTiles("JJJ".toCharArray()));
        assertFalse(pm.currentPlayerHasLetters(chars));
        // if tiles were swapped then the Player's rack will contain at most one J
        // since the bag has only 1 J
    }
}
