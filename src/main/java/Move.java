package main.java;

public abstract class Move {
    private final String moveType;

    public Move(String moveType) {
        this.moveType = moveType;
    }

    public String getMoveType() { return moveType; }
}

