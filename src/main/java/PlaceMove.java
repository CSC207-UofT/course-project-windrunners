package main.java;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A subclass of Move created when a Player decides to place a word on the Board
 */
public class PlaceMove implements Move {
    private final int x;
    private final int y;
    private final boolean direction;
    private final String word;

    /**
     * Class constructor.
     * the moveType is set to "PLACE"
     * @param word is the word that maybe placed on the Board
     * @param x is the column of the first letter of the word
     * @param y is the row of the first letter of the word
     * @param direction is the direction along which the word maybe placed
     */
    public PlaceMove(int x, int y, boolean direction, String word) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.word = word;
    }

    /**
     * a PlaceMove which stores the word to be inserted on the board, its co-ordinates,
     *      *              and the direction along which the word is to be inserted
     * @param bag   the bag to replenish the currentPlayer's rack after the move has been made
     * @param pm    the Player Manager
     * @param board the Scrabble Board on which the word is to be inserted
     * @param dict  the Scrabble dictionary
     */
    @Override
    public void execute(Bag bag, PlayerManager pm, Board board, Dictionary dict, PrintStream out) {
        if (!board.checkWord(x, y, direction, word, dict)) {
            System.out.println("Invalid word/placement");
            return;
        }
        List<Character> lettersNeeded = board.lettersNeeded(x, y, direction, word);
        if (!pm.currentPlayerHasLetters(lettersNeeded)) {
            System.out.println("Do not have letters required to make move");
            return;
        }
        List<Tile> tilesForWord = new ArrayList<>();
        for (char c : lettersNeeded) {
            tilesForWord.add(new Tile(c));
        }
        int points = board.insertWord(x, y, direction, tilesForWord);
        List<Tile> tilesToAdd = bag.drawTiles(tilesForWord.size());
        pm.updateCurrentPlayer(points, tilesToAdd, tilesForWord);
    }
}
