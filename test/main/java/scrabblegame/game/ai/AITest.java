package main.java.scrabblegame.game.ai;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import main.java.scrabblegame.game.elements.*;
import main.java.scrabblegame.game.moves.*;

public class AITest {
    // test board copied from here
    // https://www.diffen.com/difference/Boggle_vs_Scrabble
    private static final char[][] TEST_BOARD = {
        {'\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0'},
        {'\0', '\0', 'Q', 'U', 'A', 'N', 'T', '\0', '\0', '\0', '\0', '\0', 'D', 'I', 'B'},
        {'\0', '\0', 'U', '\0', '\0', '\0', '\0', '\0', '\0', '\0', 'W', '\0', 'E', '\0', '\0'},
        {'\0', '\0', 'E', '\0', '\0', 'C', 'L', 'O', 'V', 'E', 'R', '\0', 'M', '\0', '\0'},
        {'\0', 'B', 'E', '\0', '\0', '\0', '\0', 'X', '\0', '\0', 'A', '\0', 'O', '\0', '\0'},
        {'D', 'O', 'N', '\0', '\0', 'P', 'A', 'I', 'R', '\0', 'P', '\0', 'T', '\0', '\0'},
        {'O', 'Y', '\0', '\0', '\0', '\0', '\0', 'D', '\0', '\0', '\0', '\0', 'E', '\0', '\0'},
        {'Z', '\0', '\0', '\0', 'G', 'R', 'E', 'E', 'D', '\0', '\0', 'E', 'S', '\0', '\0'},
        {'E', '\0', '\0', '\0', 'R', '\0', '\0', '\0', '\0', '\0', '\0', 'V', '\0', '\0', '\0'},
        {'S', 'I', 'T', 'U', 'A', 'T', 'I', 'O', 'N', '\0', '\0', 'I', '\0', '\0', '\0'},
        {'\0', '\0', '\0', '\0', 'I', '\0', '\0', '\0', '\0', '\0', '\0', 'C', '\0', '\0', '\0'},
        {'\0', '\0', '\0', 'F', 'L', 'A', 'M', 'E', '\0', '\0', '\0', 'T', '\0', '\0', '\0'},
        {'\0', '\0', '\0', '\0', '\0', '\0', '\0', 'R', 'E', 'A', 'L', 'I', 'T', 'Y', '\0'},
        {'\0', '\0', '\0', '\0', '\0', '\0', '\0', 'A', '\0', '\0', '\0', 'N', '\0', '\0', '\0'},
        {'I', 'N', 'G', 'R', 'A', 'T', 'E', 'S', '\0', '\0', '\0', 'G', 'O', 'O', 'F'}
    };
    
    Board board;
    Bag bag;
    Player player;
    static Dictionary dict;

    @BeforeClass
    public static void dictSetup() {
        dict = new Dictionary();
    }

    @Before
    public void setUp() {
        board = new Board(TEST_BOARD);
        bag = new Bag();
        player = new Player("john", "human");
    }
    // No asserts, because test is successful if mode made successfully which occurs if no exception occurs.
    @Test
    public void testBasicAiNoWildcard() throws Exception {
        List<Tile> rack = Tile.charsToTiles("STROKEA".toCharArray());
        BasicAI bob = new BasicAI(rack);
        Move move = bob.makeMove(board);
        move.execute(bag, player, board, dict);
    }
    @Test
    public void testBasicAiWithWildcards() throws Exception {
        List<Tile> rack = Tile.charsToTiles("STROKE~".toCharArray());
        BasicAI bob = new BasicAI(rack);
        Move move = bob.makeMove(board);
        move.execute(bag, player, board, dict);
    }
    @Test
    public void testSlightlyMoreAdvancedAiNoWildcard() throws Exception {
        List<Tile> rack = Tile.charsToTiles("STROKEA".toCharArray());
        SlightlyMoreAdvancedAI bob = new SlightlyMoreAdvancedAI(rack);
        Move move = bob.makeMove(board);
        move.execute(bag, player, board, dict);
    }

    @Test
    public void testSlightlyMoreAdvancedWithWildcard() throws Exception {
        List<Tile> rack = Tile.charsToTiles("STROKE~".toCharArray());
        SlightlyMoreAdvancedAI bob = new SlightlyMoreAdvancedAI(rack);
        Move move = bob.makeMove(board);
        move.execute(bag, player, board, dict);
    }

}

