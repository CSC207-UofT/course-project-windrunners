package main.java;

public class PlaceMove extends Move {
    private final int x;
    private final int y;
    private final String word;

    public PlaceMove(int x, int y, String word) {
        super("PLACE");
        this.x = x;
        this.y = y;
        this.word = word;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getWord() {
        return word;
    }
}
