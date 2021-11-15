package main.java;

/**
 * A subclass of Move created when a Player decides to place a word on the Board
 */
public class PlaceMove extends Move {
    private final int x;
    private final int y;
    private final boolean direction;
    private final String word;

    /**
     * Class constructor.
     * the moveType is set to "PLACE"
     * @param word is the word that maybe placed on the Board
     * @param x is the column of the first letter of the word
     * @param y is the row of the first letter of the word
     * @param direction is the direction along which the word maybe placed
     */
    public PlaceMove(int x, int y, boolean direction, String word) {
        super("PLACE");
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.word = word;
    }

    /**
     * @return the column of the first letter of the word
     */
    public int getX() { return x; }

    /**
     * @return the row of the first letter of the word
     */
    public int getY() { return y; }

    /**
     * @return the direction along which the word maybe placed
     */
    public boolean getDirection() { return direction; }

    /**
     * @return the word
     */
    public String getWord() { return word; }
}
