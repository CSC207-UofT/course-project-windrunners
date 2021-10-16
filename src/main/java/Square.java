package main.java;

import java.util.Optional;

public class Square {
    private Tile tile;
    private String modifier;

    public Square() {
        tile = null;
        modifier = "none";
    }

    public Tile getTile() { return tile; }
    public void setTile(Tile tile) { this.tile = tile; }

    public String getModifier() { return modifier; }
}
