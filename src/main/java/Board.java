package main.java;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final Square[][] board = new Square[BOARD_WIDTH][BOARD_WIDTH];

    public static final boolean RIGHT = true;
    public static final boolean DOWN = false;

    public static final int BOARD_WIDTH = 15;
    public static final int MIDDLE_SQUARE = 7;

    public Board(Tile startingTile) {
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                board[i][j] = new Square();
            }
        }
        board[MIDDLE_SQUARE][MIDDLE_SQUARE].setTile(startingTile);
    }

    public Square[][] getBoard() { return board; }

    public boolean checkWord(int x, int y, boolean direction, String word) {
        int length = word.length();
        if (direction == RIGHT) {
            if (x + length - 1 > BOARD_WIDTH) {
                return false;
            }

            for (int i = 0; i < length; i++) {
                if (!board[y][x + i].isEmpty() &&
                        board[y][x + i].getTile().getLetter() != word.charAt(i)) {
                    return false;
                }
            }
        } else {
            if (y + length - 1 > BOARD_WIDTH) {
                return false;
            }

            for (int i = 0; i < length; i++) {
                if (!board[y + i][x].isEmpty() &&
                        board[y + i][x].getTile().getLetter() != word.charAt(i)) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Character> lettersNeeded(int x, int y, boolean direction, String word) {
        List<Character> needed = new ArrayList<>();
        int length = word.length();
        if (direction == RIGHT) {
            for (int i = 0; i < length; i++) {
                if (board[y][x + i].isEmpty()) {
                    needed.add(word.charAt(i));
                }
            }
        } else {
            for (int i = 0; i < length; i++) {
                if (board[y + i][x].isEmpty()) {
                    needed.add(word.charAt(i));
                }
            }
        }
        return needed;
    }

    // tiles is a list of required tiles to fill up the rest of the word, in the order they need to be inserted
    public int insertWord(int x, int y, boolean direction, List<Tile> tiles) {
        List<Tile> word = new ArrayList<>();
        if (direction == RIGHT) {
            int i = 0;
            while (!tiles.isEmpty()) {
                if (board[y][x + i].isEmpty()) {
                    board[y][x + i].setTile(tiles.remove(0));
                }
                word.add(board[y][x + i].getTile());
                i++;
            }
        } else {
            int j = 0;
            while (!tiles.isEmpty()) {
                if (board[y + j][x].isEmpty()) {
                    board[y + j][x].setTile(tiles.remove(0));
                }
                word.add(board[y + j][x].getTile());
                j++;
            }
        }

        return countValue(word);
    }

    private int countValue(List<Tile> word) {
        int wordValue = 0;
        for (Tile tile : word) {
            wordValue += tile.getValue();
        }

        return wordValue;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("  ");
        for (int i = 0; i < BOARD_WIDTH; i ++) {
            output.append(" ".repeat(i < 9 ? 2 : 1));
            output.append(i + 1 + " ");
        }
        output.append(System.lineSeparator());
        String row = "  " + "-".repeat(4 * BOARD_WIDTH + 1) + System.lineSeparator();

        output.append(row);
        for (int i = 0; i < BOARD_WIDTH; i++) {
            output.append((char) (i + 65) + " |");
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (board[i][j].isEmpty()) {
                    output.append("   |");
                } else {
                    String display = " " + board[i][j].getTile().getLetter() + " |";
                    output.append(display);
                }
            }
            output.append(System.lineSeparator());
            output.append(row);
        }
        return output.toString();
    }
}
