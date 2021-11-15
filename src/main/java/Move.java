package main.java;

import java.io.PrintStream;

/**
 * An abstract class representing a move made by a Player
 * A move can be of 3 types:
 * SWAP
 * PLACE
 * PASS
 */
public interface  Move {
    void execute(Bag bag, PlayerManager pm, Board board, Dictionary dict, PrintStream out);
}

