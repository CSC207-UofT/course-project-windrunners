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
    public void addPoints(int value) {
        points += value;
    }

    public void addTile(Tile tile) {
        rack.add(tile);
    }

    public void removeTile(char letter) {
        for (Tile tile : rack) {
            if (tile.getLetter() == letter) {
                rack.remove(tile);
            }
        }
    }

    public int getRackSize() {
        return rack.size();
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
}
