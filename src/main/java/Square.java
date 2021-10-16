package main.java;

public class Square {
    private Tile tile;
    private final String modifier;

    public Square() {
        tile = null;
        modifier = "none";
    }

    public Tile getTile() { return tile; }
    public void setTile(Tile tile) { this.tile = tile; }

    public String getModifier() { return modifier; }
}
