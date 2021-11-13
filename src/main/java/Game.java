package main.java;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        Bag bag = new Bag();
        System.out.println(bag.numTilesRemaining());
        Board board = new Board();
        PlayerManager playerManager = new PlayerManager(bag);
        while (bag.numTilesRemaining() > 0) {
            Move move = playerManager.getNextMove(board, bag.numTilesRemaining());
            if (move.getMoveType().equals("SWAP")) {
                handleSwapMove((SwapMove) move, bag, playerManager);
            } else if (move.getMoveType().equals("PLACE")) {
                handlePlaceMove((PlaceMove) move, bag, playerManager, board, dictionary);
            }
            playerManager.goToNextPlayer();
        }
        Player winner = playerManager.getLeader();
        System.out.println("Congratulations " + winner.getName() + "! You won with " + winner.getPoints() + " points");
    }
    public static void handleSwapMove(SwapMove move, Bag bag, PlayerManager pm) {
        List<Tile> tilesToSwap = move.getTilesToSwap();
        List<Tile> tilesReturned = bag.swapTiles(tilesToSwap);
        pm.updateCurrentPlayer(tilesReturned, tilesToSwap);
    }

    public static void handlePlaceMove(PlaceMove move, Bag bag, PlayerManager pm, Board board, Dictionary dict) {
        int x = move.getX(), y = move.getY();
        boolean direction = move.getDirection();
        String word = move.getWord();
        if (!board.checkWord(x, y, direction, word, dict)) {
            System.out.println("Invalid word/placement");
            return;
        }
        List<Character> lettersNeeded = board.lettersNeeded(x, y, direction, word);
        if (!pm.currentPlayerHasLetters(lettersNeeded)) {
            System.out.println("Do not have letters required to make move");
            return;
        }
        List<Tile> tilesForWord = new ArrayList<>();
        for (char c : lettersNeeded) {
            tilesForWord.add(new Tile(c));
        }
        int points = board.insertWord(x,y,direction,tilesForWord);
        List<Tile> tilesToAdd = bag.drawTiles(tilesForWord.size());
        pm.updateCurrentPlayer(points, tilesToAdd, tilesForWord);
    }
}