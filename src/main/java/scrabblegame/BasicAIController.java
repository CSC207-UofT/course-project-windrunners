package main.java.scrabblegame;

import main.java.scrabblegame.game.*;

import java.util.Scanner;

public class BasicAIController implements PlayerController {
    public Move makeMove(Scanner sc, Game game) {
        Player player = game.getCurrentPlayer();
        BasicAI ai = new BasicAI(player.getRack());
        return ai.makeMove(game.getBoard());
    }
}
