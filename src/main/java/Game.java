package main.java;

import java.util.ArrayList;
import java.util.List;

/**
 * The Game class which controls the Game
 */
public class Game {

    /**
     * The main method. Sets up and controls the state of the Game
     * The Game ends when the Bag empties
     */
    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        Bag bag = new Bag();
        System.out.println(bag.numTilesRemaining());
        Board board = new Board();
        PlayerManager playerManager = new PlayerManager(System.in, System.out, bag);
        while (bag.numTilesRemaining() > 0) {
            Move move = playerManager.getNextMove(System.in, System.out, board, bag.numTilesRemaining());
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

    /**
     * calls the Player Manager to enable the Player swap tiles with the Bag
     *
     * @param move a SwapMove which stores the tiles to swap
     * @param bag  the bag to swap tiles with
     * @param pm   is the Player Manager
     */
    public static void handleSwapMove(SwapMove move, Bag bag, PlayerManager pm) {
        List<Tile> tilesToSwap = move.getTilesToSwap();
        List<Tile> tilesReturned = bag.swapTiles(tilesToSwap);
        pm.updateCurrentPlayer(tilesReturned, tilesToSwap);
    }

    /**
     * @param move  a PlaceMove which stores the word to be inserted on the board, its co-ordinates,
     *              and the direction along which the word is to be inserted
     * @param bag   the bag to replenish the currentPlayer's rack after the move has been made
     * @param pm    the Player Manager
     * @param board the Scrabble Board on which the word is to be inserted
     * @param dict  the Scrabble dictionary
     */
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
        int points = board.insertWord(x, y, direction, tilesForWord);
        List<Tile> tilesToAdd = bag.drawTiles(tilesForWord.size());
        pm.updateCurrentPlayer(points, tilesToAdd, tilesForWord);
    }
}
