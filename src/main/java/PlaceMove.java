package main.java;

public class PlaceMove extends Move {
    private final int x;
    private final int y;
    private final boolean direction;
    private final String word;

    public PlaceMove(int x, int y, boolean direction, String word) {
        super("PLACE");
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.word = word;
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public boolean getDirection() { return direction; }

    public String getWord() { return word; }
}
