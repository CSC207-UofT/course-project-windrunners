package main.java.scrabblegame;

import main.java.scrabblegame.game.*;

import java.util.List;
import java.util.Scanner;

public class SlightlyMoreAdvancedAIController implements PlayerController{
    @Override
    public Move makeMove(Scanner sc, Game game) {
        Player player = game.getCurrentPlayer();
        SlightlyMoreAdvancedAI ai = new SlightlyMoreAdvancedAI(player.getRack());
        return ai.makeMove(game.getBoard());
    }
}
