package main.java;

/**
 * An abstract class representing a move made by a Player
 * A move can be of 3 types:
 * SWAP
 * PLACE
 * PASS
 */
public abstract class Move {
    private final String moveType;

    /**
     * Class Constructor.
     * @param moveType is the type of move made by the Player
     */
    public Move(String moveType) {
        this.moveType = moveType;
    }

    /**
     * @return the type of Move made by the Player
     */
    public String getMoveType() { return moveType; }
}

