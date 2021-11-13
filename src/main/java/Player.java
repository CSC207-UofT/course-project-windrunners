package main.java;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int points;
    private List<Tile> rack;

    public Player(String name) {
        this.name = name;
        this.points = 0;
        this.rack = new ArrayList<>();
    }

    public String getName() { return name; }

    public int getPoints() { return points; }

    public void addPoints(int value) {
        points += value;
    }

    public void addTiles(List<Tile> tiles) {
        rack.addAll(tiles);
    }

    public Tile removeTile(char letter) {
        for (Tile tile : rack) {
            if (tile.getLetter() == letter) {
                rack.remove(tile);
                return tile;
            }
        }
        return null;
    }

    public int getRackSize() {
        return rack.size();
    }

    public String getRackString() {
        StringBuilder rackString = new StringBuilder(this.name + "'s Letters: [");
        for (Tile tile : rack) {
            rackString.append(tile.getLetter());
            rackString.append(",");
        }
        rackString.append("]");
        return rackString.toString();
    }

    public boolean hasLetters(List<Character> letterList) {
        for (char letter : letterList) {
            if (!hasLetter(letter)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasLetter(char letter) {
        for (Tile tile : rack) {
            if (tile.getLetter() == letter) {
                return true;
            }
        }
        return false;
    }
}
