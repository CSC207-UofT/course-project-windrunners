package main.java;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * A Player of Scrabble
 * Each player has a name, the points earned so far, and a rack of Tiles
 */
public class Player {
    private String name;
    private int points;
    private List<Tile> rack;

    /**
     * Class constructor. A player initially has 0 points
     * @param name the name of the player
     */
    public Player(String name) {
        this.name = name;
        this.points = 0;
        this.rack = new ArrayList<>();
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

    /**
     * displays the board to the Player and asks it for a move
     * @param in is an InputStream to allow the Player to get data from a user
     * @param out is a PrintStream to allow the Player to output data to a user
     * @param board is the Scrabble board
     * @param numTilesRemaining is the number of tiles remaining in the Bag
     *                       important if the player wishes to swap tiles with the bag
     * @return a Move object according to the type of Move being made
     */
    public Move makeMove(InputStream in, PrintStream out, Board board, int numTilesRemaining) {
        Scanner sc = new Scanner(in);
        printGameData(out, board, numTilesRemaining);
        String moveType = getMoveType(sc, out);
        if (moveType.equals("pass")) {
            return new PassMove();
        } else if (moveType.equals("swap")) {
            return makeSwapMove(sc, out, numTilesRemaining);
        } else {
            return makePlaceMove(sc, out);
        }
    }

    /**
     * displays the Player's information as well as important information
     * about the current state of the game
     * @param out is a PrintStream to allow the Player to output data to a user
     * @param board is the Scrabble board
     * @param numTilesRemaining is the number of tiles remaining in the Bag
     */
    private void printGameData(PrintStream out, Board board, int numTilesRemaining) {
        out.println(name + "'s Turn:");
        out.println("Number of Tiles in the Bag = " + numTilesRemaining);
        out.println("Score: " + points);
        out.println(getRackString());
        out.println(board);
    }

    /**
     * asks the Player about the type of move he wishes to make
     * moves are of 3 types:
     *          place a word
     *          swap tiles
     *          pass
     * @param sc is a Scanner object to the read the Player's input
     * @param out is an PrintStream to allow for asking a user for specific input
     * @return a string representing the move the Player makes
     */
    private String getMoveType(Scanner sc, PrintStream out) {
        out.println("What is your move? Answer 1 to place a word, 2 to swap tiles, 3 to pass: ");
        int choice = sc.nextInt();
        return (choice == 1) ? "place" : (choice == 2) ? "swap" : "pass";
    }

    /**
     * ask the Player for the number of tiles it wishes to swap with the bag, and swap accordingly
     * @param sc is a Scanner object to read the Player's input
     * @param out is an PrintStream to allow for asking a user for specific input
     * @param numTilesRemaining is the number of tiles remaining in the Bag
     * @return a SwapMove object, which is later used by Game to update the Player's rack
     */
    private Move makeSwapMove(Scanner sc, PrintStream out, int numTilesRemaining) {
        int maxTilesToSwap = Math.min(7, numTilesRemaining);
        int numTilesToSwap;
        while(true) {
            out.println("Number of tiles to swap? (between 1 and " + maxTilesToSwap + ")");
            numTilesToSwap = sc.nextInt();
            if (numTilesToSwap > maxTilesToSwap) {
                out.println("You can only swap " + maxTilesToSwap + " tiles. Try again.");
            } else {
                break;
            }
        }
        List<Tile> tilesToSwap = new ArrayList<>();
        int i = 0;
        while (i < numTilesToSwap) {
            out.println("Tile number " + (i + 1) + " to swap? ");
            char tileToSwap = sc.next().toUpperCase().charAt(0);
            if (hasLetter(tileToSwap)) {
                tilesToSwap.add(removeTile(tileToSwap));
                i++;
            } else {
                out.println(name + " doesn't have this tile. Try again");
            }
        }
        addTiles(tilesToSwap); // Tiles will be removed via a call by the owner
        out.println("Tiles Swapped");
        return new SwapMove(tilesToSwap);
    }

    /**
     * ask the Player for the word it wishes to place on the board along with its co-ordinates
     * @param sc is a Scanner object to the read the Player's input
     * @param out is a PrintStream to allow for asking a user for specific input
     * @return a PlaceMove object, which is later used by Game to place the word on the board
     */
    private Move makePlaceMove(Scanner sc, PrintStream out) {
        out.println("Position of 1st letter (e.g. A5): ");
        String position = sc.next();
        int y = (int) position.charAt(0) - 65;
        int x = Integer.parseInt(position.substring(1)) - 1;
        out.println("Does your word goes from left to right? (answer Y or N)");
        String directionStr = sc.next();
        boolean direction = (directionStr.charAt(0) == 'Y') ? Board.RIGHT : Board.DOWN;
        out.println("What is the word?");
        String word = sc.next().toUpperCase();
        return new PlaceMove(x, y, direction, word);
    }

}
