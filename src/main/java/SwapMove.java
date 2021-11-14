package main.java;

import java.util.List;

/**
 * A subclass of Move created when a Player decides to swap Tiles with the Bag
 */
public class SwapMove extends Move {
    private final List<Tile> tilesToSwap;

    /**
     * Class constructor.
     * moveType is set to "SWAP"
     * @param tilesToSwap is the List of Tiles to swap with the Bag
     */
    public SwapMove(List<Tile> tilesToSwap) {
        super("SWAP");
        this.tilesToSwap = tilesToSwap;
    }

    /**
     * @return the List of Tiles to swap with the Bag
     */
    public List<Tile> getTilesToSwap() { return tilesToSwap; }
}
