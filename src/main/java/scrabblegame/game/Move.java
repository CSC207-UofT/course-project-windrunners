package main.java.scrabblegame.game;

/**
 * An interface representing a move made by a Player. Implements the Command design pattern.
 */
public interface  Move {
    /**
     * A function to execute the move.
     * @param bag   the bag to replenish the currentPlayer's rack after the move has been made
     * @param pm    the Player Manager
     * @param board the Scrabble Board on which the word is to be inserted
     * @param dict  the Scrabble dictionary to check whether words are valid
     */
    void execute(Bag bag, PlayerManager pm, Board board, Dictionary dict);
}

