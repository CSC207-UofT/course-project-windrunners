package main.java;

import java.io.PrintStream;

/**
 * A subclass of Move created when a Player decides to pass its move.
 */
public class PassMove implements Move {
    /**
     * the moveType is set to "PASS"
     */
    @Override
    public void execute(Bag bag, PlayerManager pm, Board board, Dictionary dict, PrintStream out) {

    }
}
