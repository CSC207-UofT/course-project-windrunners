package main.java.scrabblegame;

import main.java.scrabblegame.game.*;

import java.util.Scanner;

public interface PlayerController {
    Move makeMove(Scanner sc, Game game);
}
