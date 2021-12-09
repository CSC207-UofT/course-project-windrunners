package main.java.scrabblegame;


import main.java.scrabblegame.game.*;
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

    public static void cliGameLoopBody(Scanner sc) throws Exception {
        gamePanel.repaint();

        Player currPlayer = game.getCurrentPlayer();
        System.out.println(currPlayer.getName() + "'s Turn");
        System.out.println("Number of Tiles in the Bag = " + game.numTilesRemaining());
        System.out.println(currPlayer);
        System.out.println(game.getBoard());


         PlayerController pc;
         switch (currPlayer.getType()) {
             case "basicAI": {
                 pc = new BasicAIController();
                 break;
             }
             case "slightlyMoreAdvancedAI": {
                 pc = new SlightlyMoreAdvancedAIController();
                 break;
             }
             default: {
                 pc = new HumanController();
             }
         }
         Move move = pc.makeMove(sc, game);
         game.doMove(move);

         game.nextTurn();
    }

    public static void guiGameLoopBody() {
        Player currPlayer = game.getCurrentPlayer();
        inputHandler.setMoveIncomplete();

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
                game.doPlaceMove((int) wordInfo.get(1), (int) wordInfo.get(0), (boolean) wordInfo.get(3), (String) wordInfo.get(2));
                game.nextTurn();
                game.getGameState().saveGameState("gamestates/");
            } catch (Exception ignored) {

            }
        }
        if (inputHandler.getSwapMove()) {
            List<Tile> tilesToSwap = inputHandler.getTilesToSwap();
            try {
                game.doSwapMove(tilesToSwap);
                game.nextTurn();
                game.getGameState().saveGameState("gamestates/");
            } catch (Exception ignored) {
            }
        }
        inputHandler.resetAccumulators();
    }
}
