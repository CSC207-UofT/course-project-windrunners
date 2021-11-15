package main.java;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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

    public Move makeMove(Board board, int numTilesRemaining) {
        Scanner sc = new Scanner(System.in);
        printGameData(board, numTilesRemaining);
        String moveType = getMoveType(sc);
        if (moveType.equals("pass")) {
            return new PassMove();
        } else if (moveType.equals("swap")) {
            return makeSwapMove(sc, numTilesRemaining);
        } else {
            return makePlaceMove(sc);
        }
    }

    private void printGameData(Board board, int numTilesRemaining) {
        System.out.println(name + "'s Turn:");
        System.out.println("Number of Tiles in the Bag = " + numTilesRemaining);
        System.out.println("Score: " + points);
        System.out.println(getRackString());
        System.out.println(board);
    }

    private String getMoveType(Scanner sc) {
        System.out.println("What is your move? Answer 1 to place a word, 2 to swap tiles, 3 to pass: ");
        int choice = sc.nextInt();
        return (choice == 1) ? "place" : (choice == 2) ? "swap" : "pass";
    }

    private Move makeSwapMove(Scanner sc, int numTilesRemaining) {
        int maxTilesToSwap = Math.min(7, numTilesRemaining);
        int numTilesToSwap;
        while(true) {
            System.out.println("Number of tiles to swap? (between 1 and " + maxTilesToSwap + ")");
            numTilesToSwap = sc.nextInt();
            if (numTilesToSwap > maxTilesToSwap) {
                System.out.println("You can only swap " + maxTilesToSwap + " tiles. Try again.");
            } else {
                break;
            }
        }
        List<Tile> tilesToSwap = new ArrayList<>();
        int i = 0;
        while (i < numTilesToSwap) {
            System.out.println("Tile number " + (i + 1) + " to swap? ");
            char tileToSwap = sc.next().toUpperCase().charAt(0);
            if (hasLetter(tileToSwap)) {
                tilesToSwap.add(removeTile(tileToSwap));
                i++;
            } else {
                System.out.println(name + " doesn't have this tile. Try again");
            }
        }
        addTiles(tilesToSwap); // Tiles will be removed via a call by the owner
        System.out.println("Tiles Swapped");
        return new SwapMove(tilesToSwap);
    }

    private Move makePlaceMove(Scanner sc) {
        System.out.println("Position of 1st letter (e.g. A5): ");
        String position = sc.next();
        int y = (int) position.charAt(0) - 65;
        int x = Integer.parseInt(position.substring(1)) - 1;
        System.out.println("Does your word goes from left to right? (answer Y or N)");
        String directionStr = sc.next();
        boolean direction = (directionStr.charAt(0) == 'Y') ? Board.RIGHT : Board.DOWN;
        System.out.println("What is the word?");
        String word = sc.next().toUpperCase();
        return new PlaceMove(x, y, direction, word);
    }

    public void renderRack(Graphics g) {
        g.drawString(this.getName() + "'s rack:", 40, 16 * 40 + 20);
        for (int i = 0; i < this.getRackSize(); i++) {
            this.rack.get(i).renderTile(g, i + 5, 16);
        }
    }

}
