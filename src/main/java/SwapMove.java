package main.java;

import java.io.PrintStream;
import java.util.List;

/**
 * A subclass of Move created when a Player decides to swap Tiles with the Bag
 */
public class SwapMove implements Move {
    private final List<Tile> tilesToSwap;

    /**
     * Class constructor.
     * moveType is set to "SWAP"
     * @param tilesToSwap is the List of Tiles to swap with the Bag
     */
    public SwapMove(List<Tile> tilesToSwap) {
        this.tilesToSwap = tilesToSwap;
    }

    /**
     * @return the List of Tiles to swap with the Bag
     */
    public List<Tile> getTilesToSwap() { return tilesToSwap; }

    /**
     * a SwapMove which stores the tiles to swap
     * @param bag   the bag to replenish the currentPlayer's rack after the move has been made
     * @param pm    the Player Manager
     * @param board the Scrabble Board on which the word is to be inserted
     * @param dict  the Scrabble dictionary
     */
    @Override
    public void execute(Bag bag, PlayerManager pm, Board board, Dictionary dict, PrintStream out) {
        boolean hasLetters = pm.currentPlayerHasLetters(Tile.tilesToChars(tilesToSwap));
        if (hasLetters) {
            List<Tile> tilesReturned = bag.swapTiles(tilesToSwap);
            pm.updateCurrentPlayer(tilesReturned, tilesToSwap);
        } else {
            out.println("You don't have the correct letters");
        }
    }
}
