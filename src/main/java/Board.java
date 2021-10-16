package main.java;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Board {
    private Square[][] board;

    public static final boolean RIGHT = true;
    public static final boolean DOWN = false;

    public static final int BOARD_WIDTH = 15;

    public Board() {
        board = new Square[BOARD_WIDTH][BOARD_WIDTH];
    }

    private int countValue(List<Tile> word) {
        int wordValue = 0;
        for (Tile tile : word) {
            wordValue += tile.getValue();
        }

        return wordValue;
    }
}
