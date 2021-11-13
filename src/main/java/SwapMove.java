package main.java;

import java.util.List;

public class SwapMove extends Move {
    private final List<Tile> tilesToSwap;

    public SwapMove(List<Tile> tilesToSwap) {
        super("SWAP");
        this.tilesToSwap = tilesToSwap;
    }

    public List<Tile> getTilesToSwap() { return tilesToSwap; }
}
