package main.java.scrabblegame.game;

/**
 * An implementation of Move created when a Player decides to pass its move.
 */
public class PassMove implements Move {
    /**
     * Does nothing.
     */
    @Override
    public void execute(Bag bag, Player player, Board board, Dictionary dict) {
    }
}