package main.java.scrabblegame.game;
import org.junit.Test;
import org.junit.Before;

import java.util.List;

import static org.junit.Assert.*;

public class PlaceMoveTest {
    private Bag bag;
    private Player player;
    private Board board;
    private Dictionary dict;

    @Before
    public void setUp() {
        bag = new Bag();
        player = new Player("John");
        List<Tile> tiles = Tile.charsToTiles("STROKEE".toCharArray());
        player.addTiles(tiles);
        board = new Board();
        dict = new Dictionary();
    }

    @Test
    public void testPlacingWordNotInDict() throws Exception {
        PlaceMove placeMove = new PlaceMove(7, 7, true, "ROK");
        placeMove.execute(bag, player, board, dict);
        assertEquals(0, player.getPoints()); // word has not been inserted
    }

    @Test
    public void testFirstWordDoesNotTouchCenterSquare() throws Exception {
        PlaceMove placeMove = new PlaceMove(7, 8, false, "TREE");
        placeMove.execute(bag, player, board, dict);
        assertEquals(0, player.getPoints()); // word has not been inserted
    }

    @Test
    public void testPlacingValidWord() throws Exception {
        PlaceMove placeMove = new PlaceMove(7, 5, false, "STROKE");
        placeMove.execute(bag, player, board, dict);
        assertEquals(20, player.getPoints()); // word has been inserted
    }

    @Test
    public void testPlacingTwoWordsSecondWordNotInDict() throws Exception {
        PlaceMove placeMoveA = new PlaceMove(7, 5, false, "STROKE");
        placeMoveA.execute(bag, player, board, dict);
        assertEquals(20, player.getPoints()); // word has been inserted
        player.swapTiles(Tile.charsToTiles("STEERAM".toCharArray()), player.getRack());
        PlaceMove placeMoveB = new PlaceMove(7, 5, true, "STR");
        placeMoveB.execute(bag, player, board, dict);
        assertEquals(20, player.getPoints()); // second word has not been inserted
    }

    @Test
    public void testPlacingTwoWordsSecondWordInsertedAtWrongPosition() throws Exception {
        PlaceMove placeMoveA = new PlaceMove(7, 5, false, "STROKE");
        placeMoveA.execute(bag, player, board, dict);
        assertEquals(20, player.getPoints()); // word has been inserted
        player.swapTiles(Tile.charsToTiles("STEERAM".toCharArray()), player.getRack());
        PlaceMove placeMoveB = new PlaceMove(8, 5, true, "STEER");
        placeMoveB.execute(bag, player, board, dict);
        assertEquals(20, player.getPoints()); // second word has not been inserted
    }

    @Test
    public void testPlacingTwoWordsSecondWordValid() throws Exception {
        PlaceMove placeMoveA = new PlaceMove(7, 5, false, "STROKE");
        placeMoveA.execute(bag, player, board, dict);
        assertEquals(20, player.getPoints()); // word has been inserted
        player.swapTiles(Tile.charsToTiles("STEERAM".toCharArray()), player.getRack());
        PlaceMove placeMoveB = new PlaceMove(7, 5, false, "STROKES");
        placeMoveB.execute(bag, player, board, dict);
        assertEquals(32, player.getPoints()); // second word has been inserted
    }
}
