package main.java;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The Game class which controls the Game
 */
public class Game {
    private static Board board = new Board();
    private static Bag bag = new Bag();
    private static PlayerManager playerManager = new PlayerManager(System.in, System.out, bag);

    /**
     * The main method. Sets up and controls the state of the Game.
     * The Game ends when the Bag empties.
     */
    public static void main(String[] args) {
        JFrame window = new JFrame("Scrabble");
        GamePanel gamePanel = new GamePanel();
        window.setContentPane(gamePanel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        Dictionary dictionary = new Dictionary();
        GameState gameState;
        Scanner sc = new Scanner(System.in);
        System.out.println("Start a new game (answer true) or load from previously saved game (answer false)?");
        if (sc.nextBoolean()) {
            gameState = new GameState(System.in, System.out);
        } else {
            // TODO: come up with an actual file path
            gameState = new GameState("REPLACE");
        }
        Bag bag = gameState.getBag();
        System.out.println(bag.numTilesRemaining());
        Board board = gameState.getBoard();
        PlayerManager playerManager = gameState.getPlayerManager();
        while (bag.numTilesRemaining() > 0) {
            gamePanel.repaint();
            Move move = playerManager.getNextMove(System.in, System.out, board, bag.numTilesRemaining());
            if (move.getMoveType().equals("SWAP")) {
                handleSwapMove((SwapMove) move, bag, playerManager);
            } else if (move.getMoveType().equals("PLACE")) {
                handlePlaceMove((PlaceMove) move, bag, playerManager, board, dictionary);
            }
            playerManager.goToNextPlayer();
            updateGameState(gameState, bag, board, playerManager);
        }
        Player winner = playerManager.getLeader();
        System.out.println("Congratulations " + winner.getName() + "! You won with " + winner.getPoints() + " points");
    }

    private static void updateGameState(GameState gameState, Bag bag, Board board, PlayerManager playerManager) {
        gameState.setBag(bag);
        gameState.setBoard(board);
        gameState.setPlayerManager(playerManager);
    }

    /**
     * calls the Player Manager to enable the Player swap tiles with the Bag
     * @param move a SwapMove which stores the tiles to swap
     * @param bag the bag to swap tiles with
     * @param pm is the Player Manager
     */
    public static void handleSwapMove(SwapMove move, Bag bag, PlayerManager pm) {
        List<Tile> tilesToSwap = move.getTilesToSwap();
        List<Tile> tilesReturned = bag.swapTiles(tilesToSwap);
        pm.updateCurrentPlayer(tilesReturned, tilesToSwap);
    }

    /**
     *
     * @param move a PlaceMove which stores the word to be inserted on the board, its co-ordinates,
     *         and the direction along which the word is to be inserted
     * @param bag the bag to replenish the currentPlayer's rack after the move has been made
     * @param pm the Player Manager
     * @param board the Scrabble Board on which the word is to be inserted
     * @param dict the Scrabble dictionary
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
        int points = board.insertWord(x,y,direction,tilesForWord);
        List<Tile> tilesToAdd = bag.drawTiles(tilesForWord.size());
        pm.updateCurrentPlayer(points, tilesToAdd, tilesForWord);
    }

    public static Board getBoard() {
        return board;
    }

    public static Player getCurrentPlayer() {
        return playerManager.getCurrentPlayer();
    }

    public static PlayerManager getPlayerManager() {
        return playerManager;
    }
}