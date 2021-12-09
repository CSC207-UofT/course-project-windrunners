package main.java.scrabblegame.game;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of Move created when a Player decides to place a word on the Board
 */
public class PlaceMove implements Move {
    private final int x;
    private final int y;
    private final boolean direction;
    private final String word;

    /**
     * Class constructor.
     *
     * @param word      is the word to be placed on the Board
     * @param x         is the column of the first letter of the word
     * @param y         is the row of the first letter of the word
     * @param direction is the direction along which the word is to be placed
     */
    public PlaceMove(int x, int y, boolean direction, String word) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.word = word;
    }

    /**
     * Attempts to place the given word on the board in the given direction starting at the given coordinates.
     */
    @Override
    public void execute(Bag bag, Player player, Board board, Dictionary dict) {
        if (!board.checkWord(x, y, direction, word, dict)) {
            // throw invalid word error
            throw new Exception();
        }
        List<Character> lettersNeeded = board.lettersNeeded(x, y, direction, word);
        List<Tile> tilesForWord = new ArrayList<>();
        for (char c : lettersNeeded) {
            Tile newTile = new Tile(c);
            tilesForWord.add(newTile);
        }
        int points = board.insertWord(x, y, direction, tilesForWord);
        List<Tile> tilesToAdd = bag.drawTiles(tilesForWord.size());
        player.addPoints(points);
        player.swapTiles(tilesToAdd, tilesForWord);
    }
}
