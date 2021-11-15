package main.java;

import org.junit.Test;
import org.junit.Before;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerManagerTest {
    private InputStream getInputStream(String[] strings) {
        StringBuilder inputStr = new StringBuilder();
        for (int i = 0; i < strings.length - 1; i++) {
            inputStr.append(strings[i]).append("\n");
        }
        inputStr.append(strings[strings.length - 1]);
        return new ByteArrayInputStream(inputStr.toString().getBytes());
    }
    private List<Tile> charsToTiles(Character[] letters) {
        List<Tile> tiles = new ArrayList<>();
        for (char letter : letters) tiles.add(new Tile(letter));
        return tiles;
    }
    private PrintStream out;
    private Bag bag;
    @Before
    public void setUp() {
        out = new PrintStream(new ByteArrayOutputStream(), false, StandardCharsets.UTF_8);
        bag = new Bag();
    }

    @Test
    public void testCreatesCorrectPlayers() {
        InputStream in = getInputStream(new String[] {"3", "bob", "rob", "robert", "joe"});
        PlayerManager pm = new PlayerManager(in, out, bag);
        Player p1 = pm.getCurrentPlayer();
        pm.goToNextPlayer();
        Player p2 = pm.getCurrentPlayer();
        pm.goToNextPlayer();
        Player p3 = pm.getCurrentPlayer();
        pm.goToNextPlayer();
        Player p4 = pm.getCurrentPlayer();
        assertEquals("bob", p1.getName());
        assertEquals("rob", p2.getName());
        assertEquals("robert", p3.getName());
        assertEquals(p1, p4);
    }

    @Test
    public void testUsesBagCorrectly() {
        InputStream in = getInputStream(new String[] {"3", "bob", "rob", "robert"});
        int startSize = bag.numTilesRemaining();
        new PlayerManager(in, out, bag);
        assertEquals(startSize - 21, bag.numTilesRemaining());
    }

    @Test
    public void testRackInitialization() {
        InputStream in = getInputStream(new String[] {"3", "bob", "rob", "robert"});
        PlayerManager pm = new PlayerManager(in, out, bag);
        Player p1 = pm.getCurrentPlayer();
        pm.goToNextPlayer();
        Player p2 = pm.getCurrentPlayer();
        pm.goToNextPlayer();
        Player p3 = pm.getCurrentPlayer();
        assertEquals(7, p1.getRackSize());
        assertEquals(7, p2.getRackSize());
        assertEquals(7, p3.getRackSize());
    }

    @Test
    public void testRackInitializationSpecificLetters() {
        InputStream in = getInputStream(new String[] {"1", "bob"});
        bag.drawTiles(bag.numTilesRemaining() - 7); //
        Character[] chars = new Character[] {'A', 'B','C', 'D', 'E', 'F', 'G'};
        List<Tile> playerTiles = charsToTiles(chars);
        bag.swapTiles(playerTiles);
        PlayerManager pm = new PlayerManager(in, out, bag);
        Player player = pm.getCurrentPlayer();
        assertEquals(7, player.getRackSize());
        assertTrue(player.hasLetters(Arrays.asList(chars)));
    }

    @Test
    public void testUpdatingTiles() {
        InputStream in = getInputStream(new String[] {"1", "bob"});
        bag.drawTiles(bag.numTilesRemaining() - 7); //
        List<Tile> playerTiles = charsToTiles(new Character[] {'A', 'A','B', 'C', 'D', 'E', 'F'} );
        bag.swapTiles(playerTiles);
        PlayerManager pm = new PlayerManager(in, out, bag);
        Character[] lettersToAdd = new Character[] {'G', 'H', 'I', 'J', 'K','L','M'};
        List<Tile> tilesToAdd = charsToTiles(lettersToAdd);
        pm.updateCurrentPlayer(tilesToAdd, playerTiles);
        Player player = pm.getCurrentPlayer();
        assertEquals(7, player.getRackSize());
        assertTrue(player.hasLetters(Arrays.asList(lettersToAdd)));
        assertFalse(player.hasLetter('A'));
        assertFalse(player.hasLetter('F'));
    }

    @Test
    public void testUpdatingScore() {
        InputStream in = getInputStream(new String[] {"1", "bob"});
        PlayerManager pm = new PlayerManager(in, out, bag);
        List<Tile> L = new ArrayList<>();
        int[] points = {1, 321, 32, 123};
        int sum = 0;
        for (int num : points) {
            sum += num;
            pm.updateCurrentPlayer(num, L, L);
            assertEquals(sum, pm.getCurrentPlayer().getPoints());
        }
    }

    private void emptyPlayerRack(Player player) {
        for (char letter='A'; letter <= 'Z'; letter++) {
            while (player.hasLetter(letter)) {
                player.removeTile(letter);
            }
        }
    }

    @Test
    public void testGetLeaderAdjustsPointsCorrect() {
        InputStream in = getInputStream(new String[] {"3", "bob", "rob", "robert"});
        PlayerManager pm = new PlayerManager(in, out, bag);
        ArrayList<Tile> tiles = new ArrayList<>();
        int[] points = {0, 35, 0};
        for (int i = 0; i < 3; i++) {
            Player player = pm.getCurrentPlayer();
            emptyPlayerRack(player);
            player.addTiles(tiles);
            tiles.add(new Tile('Q'));
            player.addPoints(points[i]);
            pm.goToNextPlayer();
        }
        /*
         30 points total on racks
         bob has empty rack and 0 points so 30 adjusted points
         rob has 10 points on rack and 35 points so 25 adjusted points
         robert has 20 points on rack and 0 points so -20 adjusted points
         so bob should win
        */
        Player winner = pm.getLeader();
        assertEquals("bob", winner.getName());
    }

    @Test
    public void testGetLeaderTieBreaker() {
        InputStream in = getInputStream(new String[] {"3", "bob", "rob", "robert"});
        PlayerManager pm = new PlayerManager(in, out, bag);
        ArrayList<Tile> tiles = new ArrayList<>();
        int[] points = {0, 0, 50};
        for (int i = 0; i < 3; i++) {
            Player player = pm.getCurrentPlayer();
            emptyPlayerRack(player);
            player.addTiles(tiles);
            tiles.add(new Tile('Q')); // Q's are worth 10;
            player.addPoints(points[i]);
            pm.goToNextPlayer();
        }
        /*
         30 points total on racks
         bob has empty rack and 0 points so 30 adjusted points
         rob has 10 points on rack and 0 points so -10 adjusted points
         robert has 20 points on rack and 50 points so 30 adjusted points
         robert has more point so should win on tiebreaker
        */
        Player winner = pm.getLeader();
        assertEquals("robert", winner.getName());
    }

}
