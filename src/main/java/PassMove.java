package main.java;

import java.io.PrintStream;

/**
 * An implementation of Move created when a Player decides to pass its move.
 */
public class PassMove implements Move {
    /**
     * Does nothing.
     */
    @Override
    public void execute(Bag bag, PlayerManager pm, Board board, Dictionary dict, PrintStream out) {
    }
}
