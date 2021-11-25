package main.java.scrabblegame.game;
import org.junit.Test;
import org.junit.Before;

import java.util.List;

public class PlaceMoveTest {
    private Bag bag;
    private PlayerManager pm;
    private Board board;
    private Dictionary dict;

    @Before
    public void setUp() {
        bag = new Bag();
        Player player = new Player("John");
        List<Tile> tiles = Tile.charsToTiles("STROKEE".toCharArray());
        player.addTiles(tiles);
        Player[] players = {player};
        pm = new PlayerManager(players, 0);
        board = new Board();
        dict = new Dictionary();
    }

    @Test
    public void testPlacingWordNotInDict() {
        PlaceMove placeMove = new PlaceMove(7, 7, true, "ROK");
        placeMove.execute(bag, pm, board, dict);
        assert pm.getCurrentPlayer().getPoints() == 0; // word has not been inserted
    }

    @Test
    public void testFirstWordDoesNotTouchCenterSquare() {
        PlaceMove placeMove = new PlaceMove(7, 8, false, "TREE");
        placeMove.execute(bag, pm, board, dict);
        assert pm.getCurrentPlayer().getPoints() == 0; // word has not been inserted
    }

    @Test
    public void testPlacingValidWord() {
        PlaceMove placeMove = new PlaceMove(7, 5, false, "STROKE");
        placeMove.execute(bag, pm, board, dict);
        assert pm.getCurrentPlayer().getPoints() == 20; // word has been inserted
    }

    @Test
    public void testPlacingTwoWordsSecondWordNotInDict() {
        PlaceMove placeMoveA = new PlaceMove(7, 5, false, "STROKE");
        placeMoveA.execute(bag, pm, board, dict);
        assert pm.getCurrentPlayer().getPoints() == 20; // word has been inserted
        pm.updateCurrentPlayer(Tile.charsToTiles("STEERAM".toCharArray()), pm.getCurrentPlayer().getRack());
        PlaceMove placeMoveB = new PlaceMove(7, 5, true, "STR");
        placeMoveB.execute(bag, pm, board, dict);
        assert pm.getCurrentPlayer().getPoints() == 20; // second word has not been inserted
    }

    @Test
    public void testPlacingTwoWordsSecondWordInsertedAtWrongPosition() {
        PlaceMove placeMoveA = new PlaceMove(7, 5, false, "STROKE");
        placeMoveA.execute(bag, pm, board, dict);
        assert pm.getCurrentPlayer().getPoints() == 20; // word has been inserted
        pm.updateCurrentPlayer(Tile.charsToTiles("STEERAM".toCharArray()), pm.getCurrentPlayer().getRack());
        PlaceMove placeMoveB = new PlaceMove(7, 5, true, "STEER");
        placeMoveB.execute(bag, pm, board, dict);
        assert pm.getCurrentPlayer().getPoints() == 27; // second word has not been inserted
    }

    @Test
    public void testPlacingTwoWordsSecondWordValid() {
        PlaceMove placeMoveA = new PlaceMove(7, 5, false, "STROKE");
        placeMoveA.execute(bag, pm, board, dict);
        assert pm.getCurrentPlayer().getPoints() == 20; // word has been inserted
        pm.updateCurrentPlayer(Tile.charsToTiles("STEERAM".toCharArray()), pm.getCurrentPlayer().getRack());
        PlaceMove placeMoveB = new PlaceMove(7, 5, false, "STROKES");
        placeMoveB.execute(bag, pm, board, dict);
        assert pm.getCurrentPlayer().getPoints() == 32; // second word has been inserted
    }
 }
