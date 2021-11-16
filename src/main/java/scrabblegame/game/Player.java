package main.java.scrabblegame.game;

import java.util.ArrayList;
import java.util.List;

/**
 * A Player of Scrabble
 * Each player has a name, the points earned so far, and a rack of Tiles
 */
public class Player {
    private final String name;
    private int points;
    private final List<Tile> rack;

    /**
     * Class constructor. A player initially has 0 points
     * @param name the name of the player
     */
    public Player(String name) {
        this.name = name;
        this.points = 0;
        this.rack = new ArrayList<>();
    }

    public Player(Player that){
        this.name = that.name;
        this.points = that.points;
        this.rack = new ArrayList<>();
        this.rack.addAll(that.rack);
    }

    /**
     * @return the Player's name
     */
    public String getName() { return name; }

    /**
     * @return the Player's points
     */
    public int getPoints() { return points; }

    /**
     * @return the points of the tiles on the Players rack (needed to find the winner at the end of the game)
     */
    public int getRackPoints() {
        int sum = 0;
        for (Tile tile: rack) sum += tile.getValue();
        return sum;
    }
    /**
     * @param value is added to the Player's points
     */
    public void addPoints(int value) {
        points += value;
    }

    /**
     * @param tiles is a List of tiles to be added to the Player's rack
     */
    public void addTiles(List<Tile> tiles) {
        rack.addAll(tiles);
    }

    /**
     * Remove the Tile containing the letter from the rack
     * @param letter is the letter to be removed
     * @return the Tile containing letter that has been removed
     */
    public Tile removeTile(char letter) {
        for (Tile tile : rack) {
            if (tile.getLetter() == letter) {
                rack.remove(tile);
                return tile;
            }
        }
        return null;
    }

    /**
     * Remove the Tiles containing the letters from the rack
     * @param letters is a list of letters to be removed
     * @return a list containing the Tiles that were removed
     */
    public List<Tile> removeTiles(List<Character> letters) {
        List<Tile> tiles = new ArrayList<>();
        for (char letter : letters) {
            Tile tile = removeTile(letter);
            if (tile != null) {
                tiles.add(tile);
            }
        }
        return tiles;
    }

    /**
     * @return the list of tiles in the Player's rack
     */
    public List<Tile> getRack() { return rack; }

    /**
     * @return the number of tiles in the Player's rack
     */
    public int getRackSize() {
        return rack.size();
    }

    /**
     * @return a string representing the tiles in the rack
     */
    public String getRackString() {
        StringBuilder rackString = new StringBuilder(this.name + "'s Letters: [");
        for (Tile tile : rack) {
            rackString.append(tile.getLetter());
            rackString.append(",");
        }
        rackString.append("]");
        return rackString.toString();
    }

    /**
     * @return a string with the letters on the rack only
     */
    public String getRackLetters() {
        StringBuilder rackLetters = new StringBuilder();
        for (Tile tile : rack) {
            rackLetters.append(tile.getLetter());
        }
        return rackLetters.toString();
    }

    /**
     * check if the Player's rack has all the letters in letterList
     * @param letterList: to be checked if it's contained in the rack
     * @return true iff letterList is a subset of the rack
     */
    public boolean hasLetters(List<Character> letterList) {
        List<Character> playerLetters = Tile.tilesToChars(rack);
        List<Character> letterListCopy = new ArrayList<>(letterList);
        for (Character playerLetter : playerLetters) {
            letterListCopy.remove(playerLetter);
        }
        return letterListCopy.size() == 0;
    }

    /**
     * check if the Player's rack has a Tile containing letter
     * @param letter: to be checked if it's contained in the rack
     * @return true iff letter belongs to one of the Tiles in the rack
     */
    public boolean hasLetter(char letter) {
        for (Tile tile : rack) {
            if (tile.getLetter() == letter) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Score: " + points + "\n" + getRackString() + "\n";
    }

}
