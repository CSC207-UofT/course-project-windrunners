package main.java.scrabblegame;


import main.java.scrabblegame.game.*;
import main.java.scrabblegame.game.ai.BasicAI;
import main.java.scrabblegame.game.ai.SlightlyMoreAdvancedAI;
import main.java.scrabblegame.game.elements.Player;
import main.java.scrabblegame.game.elements.Tile;
import main.java.scrabblegame.game.moves.Move;
import main.java.scrabblegame.game.moves.PassMove;
import main.java.scrabblegame.game.moves.PlaceMove;
import main.java.scrabblegame.game.moves.SwapMove;
import main.java.scrabblegame.gui.GamePanel;
import main.java.scrabblegame.gui.InputHandler;

import javax.swing.*;
import java.util.*;

public class ScrabbleGame {

    private static Game game;
    private static GameState gameState;
    private static GamePanel gamePanel;
    private static InputHandler inputHandler;

    /**
     * The main method. Sets up and controls the state of the Game.
     * The Game ends when the Bag empties.
     */
    public static void main(String[] args) throws Exception {

        JFrame window = new JFrame("Scrabble");

        Scanner sc = new Scanner(System.in);

        initGame(sc, window);
        while (game.getBag().numTilesRemaining() > 0) {
            guiGameLoopBody();
        }
        Player winner = game.getLeader();
        System.out.println("Congratulations " + winner.getName() + "! You won with " + winner.getPoints() + " points");
    }

    private static void initGame(Scanner sc, JFrame window) {

        game = new Game();
        inputHandler = game.getInputHandler();
        System.out.println("How many players are there?");
        int numPlayers = Math.max(sc.nextInt(), 1);
        List<String> names = new ArrayList<>();
        List<Integer> types = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            System.out.println();
            System.out.print("Enter Player " + (i + 1) + "'s Name: ");
            names.add(sc.next());
            System.out.print("Enter Player " + (i + 1) + "'s type: 1 if human, 2 if basic AI, 3 if more advanced AI ");
            types.add(sc.nextInt());

        }
        game.initPlayers(numPlayers, names, types);
        gameState = game.getGameState();
        gameState.saveGameState("gamestates/");
        gameState = new GameState("gamestates/");
        gamePanel = new GamePanel(game);
        window.setContentPane(gamePanel);
        window.getContentPane().addMouseListener(inputHandler);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public static void guiGameLoopBody() {
        Player currPlayer = game.getCurrentPlayer();
        GameState prevState = game.getGameState();
        inputHandler.setMoveIncomplete();

        if (currPlayer.getSkip()) {
            currPlayer.setSkip(false);
            game.nextTurn();
            game.getGameState().saveGameState("gamestates/");
            return;
        }

        if (currPlayer.getType().equals(BasicAI.checkString)) {
            BasicAI ai = new BasicAI(currPlayer.getRack());
            Move move = ai.makeMove(game.getBoard());
            try {
                game.doMove(move);
            } catch (Exception ignored) {

            }
            game.nextTurn();
            game.getGameState().saveGameState("gamestates/");
            return;
        } else if (currPlayer.getType().equals(SlightlyMoreAdvancedAI.checkString)) {
            SlightlyMoreAdvancedAI ai = new SlightlyMoreAdvancedAI(currPlayer.getRack());
            Move move = ai.makeMove(game.getBoard());
            try {
                game.doMove(move);
            } catch (Exception ignored) {

            }
            game.nextTurn();
            game.getGameState().saveGameState("gamestates/");
            return;
        }

        while (!inputHandler.getMoveComplete()) {
            gamePanel.repaint();
            inputHandler.processInput(game.getBoard(), currPlayer);
        }
        if (inputHandler.checkIfAccumulatorsResetted()) {
            game.nextTurn();
            game.getGameState().saveGameState("gamestates/");
            return;
        }
        gameState = new GameState("gamestates/");
        game.loadGameState(gameState);
        List<Object> wordInfo = inputHandler.completeMove(game.getBoard());
        if (wordInfo != null && !inputHandler.getSwapMove()) {
            try {
                int x = (int) wordInfo.get(1);
                int y = (int) wordInfo.get(0);
                boolean direction = (boolean) wordInfo.get(3);
                String word = (String) wordInfo.get(2);
                Move move = new PlaceMove(x, y, direction, word);
                game.doMove(move);

                while (inputHandler.getChallengeActive()) {
                    gamePanel.repaint();
                    inputHandler.processInput(game.getBoard(), currPlayer);
                }

                if (inputHandler.getIndexOfChallengingPlayer() >= 0 && inputHandler.getIndexOfChallengingPlayer() < game.getPlayerManager().getPlayers().length) {
                    GameState currState = game.getGameState();
                    String name = game.getPlayerManager().getPlayers()[inputHandler.getIndexOfChallengingPlayer()].getName();
                    game.loadGameState(prevState);
                    boolean successful = game.challenge(name, x, y, direction, word);
                    if (!successful) {
                        game.loadGameState(currState);
                        game.setPlayerSkip(name);
                    }
                }

                game.nextTurn();
                game.getGameState().saveGameState("gamestates/");
            } catch (Exception ignored) {

            }
        }
        if (inputHandler.getSwapMove()) {
            List<Tile> tilesToSwap = inputHandler.getTilesToSwap();
            try {
                Move move = new SwapMove(tilesToSwap);
                game.doMove(move);
                game.nextTurn();
                game.getGameState().saveGameState("gamestates/");
            } catch (Exception ignored) {
            }
        }

        inputHandler.resetAccumulators();
    }
}
