package main.java;

public class Square {
    private Tile tile;
    private final int letterMult;
    private final int wordMult;
    private boolean multActive;

    public Square() {
        tile = null;
        multActive = true;
        letterMult = 1;
        wordMult = 1;
    }

    public Square(int letterMult, int wordMult) {
        tile = null;
        multActive = true;
        this.letterMult = letterMult;
        this.wordMult = wordMult;
    }

    public Tile getTile() { return tile; }
    public void setTile(Tile tile) { this.tile = tile; }

    public boolean isMultActive() { return multActive; }
    public void setMultUsed() { multActive = false; }

    public int getLetterMult() { return letterMult; }
    public int getWordMult() { return wordMult; }

    public boolean isEmpty() {
        return tile == null;
    }

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