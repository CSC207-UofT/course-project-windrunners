package main.java.scrabblegame.game;

import java.util.List;

/**
 * A subclass of Move created when a Player decides to swap Tiles with the Bag
 */
public class SwapMove implements Move {
    private final List<Tile> tilesToSwap;

    /**
     * Class constructor.
     *
     * @param tilesToSwap is the List of Tiles to swap with the Bag
     */
    public SwapMove(List<Tile> tilesToSwap) {
        this.tilesToSwap = tilesToSwap;
    }

    /**
     * Attempts to swap tilesToSwap with the bag
     */
    @Override
    public void execute(Bag bag, Player player, Board board, Dictionary dict) {
        if(bag.getNumOfLettersInBag() < 7){
            //Move Skipped, does nothing (explicit return for clarity)
            return;
        }
        boolean hasLetters = player.hasLetters(Tile.tilesToChars(tilesToSwap));
        if (hasLetters) {
            List<Tile> tilesReturned = bag.swapTiles(tilesToSwap);
            player.swapTiles(tilesReturned, tilesToSwap);
        }
        // Else, Move Skipped, does nothing (explicit return for clarity)
        return;
    }
}