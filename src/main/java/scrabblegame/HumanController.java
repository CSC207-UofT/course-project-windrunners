package main.java.scrabblegame;

import main.java.scrabblegame.game.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class HumanController implements PlayerController {
    @Override
    public Move makeMove(Scanner sc, Game game) {
        System.out.println("What is your move? Answer 1 to place a word, 2 to swap tiles, 3 to pass: ");
        int choice = sc.nextInt();
        String moveType = (choice == 1) ? "place" : (choice == 2) ? "swap" : "pass";

        if (moveType.equals("pass")) {
            return new PassMove();
        } else if (moveType.equals("swap")) {
            return makeSwapMove(sc, game);
        } else {
            return makePlaceMove(sc);
        }

    }

    private static Move makeSwapMove(Scanner sc, Game game) {
        int maxTilesToSwap = Math.min(7, game.numTilesRemaining());
        int numTilesToSwap;
        while (true) {
            System.out.println("Number of tiles to swap? (between 1 and " + maxTilesToSwap + ")");
            numTilesToSwap = sc.nextInt();
            if (numTilesToSwap > maxTilesToSwap) {
                System.out.println("You can only swap " + maxTilesToSwap + " tiles. Try again.");
            } else {
                break;
            }
        }

        List<Tile> tilesToSwap = new ArrayList<>();
        List<Tile> rack = new ArrayList<>(game.getCurrPlayerRack());
        List<Character> charList;
        int i = 0;
        while (i < numTilesToSwap) {
            System.out.println("Tile number " + (i + 1) + " to swap? ");
            char letterToSwap = sc.next().toUpperCase().charAt(0);

            charList = rack.stream().map(Tile::getLetter).collect(Collectors.toList());
            if (charList.contains(letterToSwap)) {
                for (Tile tile : rack) {
                    if (tile.getLetter() == letterToSwap) {
                        rack.remove(tile);
                        tilesToSwap.add(tile);
                        break;
                    }
                }
                i++;
            } else {
                System.out.println(game.getCurrentPlayer().getName() + " doesn't have this tile. Try again");
            }
        }
        System.out.println("Tiles Swapped");
        return new SwapMove(tilesToSwap);
    }

    private static Move makePlaceMove(Scanner sc) {
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

}
