package main.java.scrabblegame.game;

import java.util.List;
import main.java.scrabblegame.game.Bag;

/**
 * A subclass of Move created when a Player decides to swap Tiles with the Bag
 */
public class SwapMove implements Move {
    private final List<Tile> tilesToSwap;
    private final Bag bag;

    /**
     * Class constructor.
     * @param tilesToSwap is the List of Tiles to swap with the Bag
     */
    public SwapMove(List<Tile> tilesToSwap, Bag bag) {
        this.tilesToSwap = tilesToSwap;
        this.bag = bag;
    }

    /**
     * Attempts to swap tilesToSwap with the bag
     */
    @Override
    public void execute(Bag bag, PlayerManager pm, Board board, Dictionary dict) {
        boolean hasLetters = pm.currentPlayerHasLetters(Tile.tilesToChars(tilesToSwap));
        if (hasLetters) {
            List<Tile> tilesReturned = bag.swapTiles(tilesToSwap);
            pm.updateCurrentPlayer(tilesReturned, tilesToSwap);
        }
    }
}
