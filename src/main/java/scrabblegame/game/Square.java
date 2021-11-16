package main.java.scrabblegame.game;

/**
 * Tiles are placed on Squares, a collection of which form a Board
 */

public class Square {
    private Tile tile;
    private final int letterMult;
    private final int wordMult;
    private boolean multActive;

    /**
     * Class constructor. tile is null when no tiles are placed on the Square
     * @param letterMult controls the proportion of points gained for every letter placed on the Square
     * @param wordMult controls the proportion of points gained when a word passed through the
     */

    public Square(int letterMult, int wordMult) {
        tile = null;
        multActive = true;
        this.letterMult = letterMult;
        this.wordMult = wordMult;
    }

    /**
     * Class constructor. tile is null when no tiles are placed on the Square
     * For Squares that don't give extra points when a word/letter is placed on them,
     * letterMult and wordMult are 1 by default
     */

    public Square() {
        this(1,1);
    }

    /**
     * Class constructor. Make a deep copy.
     * @param that the Square to make a deep copy of
     */
    public Square(Square that) {
        tile = that.getTile();
        multActive = that.isMultActive();
        letterMult = that.getLetterMult();
        wordMult = that.getWordMult();
    }

    /**
     * @return the Tile on the Square (null if the Square is empty)
     */
    public Tile getTile() { return tile; }

    /**
     * place tile on this Square
     * @param tile the Tile to be placed on Square
     */
    public void setTile(Tile tile) { this.tile = tile; }

    /**
     * @return the value of multActive
     */
    public boolean isMultActive() { return multActive; }

    /**
     * set multActive to false.
     */
    public void setMultUsed() { multActive = false; }

    /**
     * @return the value of letterMult
     */
    public int getLetterMult() { return letterMult; }

    /**
     *
     * @return the value of wordMult
     */
    public int getWordMult() { return wordMult; }

    /**
     * check if the Square is empty (i.e. has no tile placed on it)
     * @return true iff the Square is empty
     */
    public boolean isEmpty() {
        return tile == null;
    }

    /**
     * @return a string representation of the Square
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            String str = "";
            if (letterMult != 1) { str += letterMult + "L"; }
            if (wordMult != 1) { str += wordMult + "W"; }
            return str;
        } else {
            return tile.getLetter() + "";
        }
    }

}