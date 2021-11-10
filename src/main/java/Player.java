package main.java;

import java.util.ArrayList;
import java.util.Arrays;
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

    public void addTile(Tile tile) {
        rack.add(tile);
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
        boolean hasLetter;
        for (char letter : letterList) {
            hasLetter = false;
            for (Tile tile : rack) {
                if (tile.getLetter() == letter) {
                    hasLetter = true;
                }
            }
            if (!hasLetter) {
                return false;
            }
        }
        return true;
    }

    public boolean hasLetter(char letter) {
        List<Character> temp = Arrays.asList(letter);
        return hasLetters(temp);
    }
}
