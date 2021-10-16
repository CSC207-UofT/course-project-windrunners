package main.java;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private int points;
    private final List<Tile> rack;

    public Player(String name) {
        this.name = name;
        this.points = 0;
        this.rack = new ArrayList<>();
    }

    public String getName() { return name; }
    public void addPoints(int value) {
        points += value;
    }
    public int getPoints() { return points; }

    public void addTile(Tile tile) {
        rack.add(tile);
    }

    public Tile removeTile(char letter) {
        for (int i = 0; i < rack.size(); i++) {
            if (rack.get(i).getLetter() == letter) {
                return rack.remove(i);
            }
        }
        return null;
    }

    public int getRackSize() {
        return rack.size();
    }

    public boolean hasLetters(List<Character> letterList) {
        List<Tile> rackCopy = new ArrayList<>(rack);

        boolean hasLetter;
        for (char letter : letterList) {
            hasLetter = false;
            for (Tile tile : rackCopy) {
                if (tile.getLetter() == letter) {
                    hasLetter = true;
                    rackCopy.remove(tile);
                }
            }
            if (!hasLetter) {
                return false;
            }
        }
        return true;
    }
}
