package main.java.scrabblegame.game;

import org.junit.Test;
import org.junit.Before;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class GameStateTest {
    private Bag bag;
    private PlayerManager pm;
    private Board board;
    private GameState gameState;

    @Before
    public void setUp() {
        bag = new Bag();
        Player player = new Player("John", "human");
        List<Tile> tiles = Tile.charsToTiles("STROKEA".toCharArray());
        player.addTiles(tiles);
        Player[] players = {player};
        pm = new PlayerManager(players, 0);
        board = new Board();
        gameState = new GameState(bag, pm, board);
        // reset the folder that will contain all the files required for the tests
        File dir = new File("gamestate-tests");
        if (!dir.mkdir()) {
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                assert(file.delete());
            }
        }
    }

    @Test
    public void testBoardInfoSavesToFile(){
        gameState.saveGameState("gamestate-tests/");
        boolean check = new File("gamestate-tests/", "boardInfo.csv").exists();
        assert (check);
    }

    @Test
    public void testBagInfoSavesToFile(){
        gameState.saveGameState("gamestate-tests/");
        boolean check = new File("gamestate-tests/", "bagInfo.csv").exists();
        assert (check);
    }

    @Test
    public void testPlayerInfoSavesToFile(){
        gameState.saveGameState("gamestate-tests/");
        boolean check = new File("gamestate-tests/", "playerInfo.csv").exists();
        assert (check);
    }

    @Test
    public void testBoardInfoLoading() {
        gameState.saveGameState("gamestate-tests/");
        gameState = new GameState("gamestate-tests/");
        assert (Arrays.deepEquals(board.getBoardLetters(), gameState.getBoard().getBoardLetters()));
    }

    @Test
    public void testPlayerInfoPlayerNamesLoading() {
        gameState.saveGameState("gamestate-tests/");
        gameState = new GameState("gamestate-tests/");

        Player[] loadedPlayerList = gameState.getPlayerManager().getPlayers();
        Player[] playerList = pm.getPlayers();

        for (int i = 0; i < playerList.length; i++) {
            assert (loadedPlayerList[i].getName().equals(playerList[i].getName()));
        }
    }

    @Test
    public void testPlayerInfoPlayerPointsLoading() {
        gameState.saveGameState("gamestate-tests/");
        gameState = new GameState("gamestate-tests/");

        Player[] loadedPlayerList = gameState.getPlayerManager().getPlayers();
        Player[] playerList = pm.getPlayers();

        for (int i = 0; i < playerList.length; i++) {
            assert (loadedPlayerList[i].getPoints() == playerList[i].getPoints());
        }
    }

    @Test
    public void testPlayerInfoPlayerTilesLoading() {
        gameState.saveGameState("gamestate-tests/");
        gameState = new GameState("gamestate-tests/");

        Player[] loadedPlayerList = gameState.getPlayerManager().getPlayers();
        Player[] playerList = pm.getPlayers();

        for (int i = 0; i < playerList.length; i++) {
            for (int j = 0; j < playerList[i].getRackSize(); j++) {
                assert (loadedPlayerList[i].getRack().get(j).getLetter() == playerList[i].getRack().get(j).getLetter());
            }
        }
    }

    @Test
    public void testPlayerInfoPlayerTypesLoading() {
        gameState.saveGameState("gamestate-tests/");
        gameState = new GameState("gamestate-tests/");

        Player[] loadedPlayerList = gameState.getPlayerManager().getPlayers();
        Player[] playerList = pm.getPlayers();

        for (int i = 0; i < playerList.length; i++) {
            assert (loadedPlayerList[i].getType().equals(playerList[i].getType()));
        }
    }

    @Test
    public void testBagInfoBagLoading(){
        gameState.saveGameState("gamestate-tests/");
        gameState = new GameState("gamestate-tests/");
        for (int i = 0; i < bag.getBagLetters().size(); i++) {
            assert (bag.getBagLetters().get(i) == gameState.getBag().getBagLetters().get(i));
        }
    }

    @Test
    public void testBagInfoNumberOfPlayersLoading() {
        gameState.saveGameState("gamestate-tests/");
        gameState = new GameState("gamestate-tests/");
        assert (pm.getPlayers().length == gameState.getPlayerManager().getPlayers().length);
    }

    @Test
    public void testBagInfoCurrentPlayerNumberLoading() {
        gameState.saveGameState("gamestate-tests/");
        gameState = new GameState("gamestate-tests/");
        assert (pm.getCurrentPlayerNum() == gameState.getPlayerManager().getCurrentPlayerNum());
    }
}
