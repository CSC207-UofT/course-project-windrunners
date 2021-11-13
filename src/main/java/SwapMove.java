package main.java;

import java.util.List;

public class SwapMove extends Move {
    private final List<Tile> letters;

    public SwapMove(List<Tile> letters) {
        super("SWAP");
        this.letters = letters;
    }

    public List<Tile> getLetters() {
        return letters;
    }
}
