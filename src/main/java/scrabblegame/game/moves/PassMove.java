package main.java.scrabblegame.game.moves;

import main.java.scrabblegame.game.elements.Bag;
import main.java.scrabblegame.game.elements.Board;
import main.java.scrabblegame.game.elements.Dictionary;
import main.java.scrabblegame.game.elements.Player;

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