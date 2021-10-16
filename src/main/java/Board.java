package main.java;

import java.util.HashMap;

public class Board {
    private Square[][] board;

    public Board() {
        board = new Square[15][15];
    }

    public int countValue(Tile[] word) {
        int wordValue = 0;
        for (Tile tile : word) {
            wordValue += tile.getValue();
        }
        return wordValue;
    }
}
