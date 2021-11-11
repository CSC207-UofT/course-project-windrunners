package main.java;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final Square[][] board = new Square[BOARD_WIDTH][BOARD_WIDTH];
    private static final String[][] SQUARE_TYPES = {
        {"3W","  ","  ","2L","  ","  ","  ","3W","  ","  ","  ","2L","  ","  ","3W"},
        {"  ","2W","  ","  ","  ","3L","  ","  ","  ","3L","  ","  ","  ","2W","  "},
        {"  ","  ","2W","  ","  ","  ","2L","  ","2L","  ","  ","  ","2W","  ","  "},
        {"2L","  ","  ","2W","  ","  ","  ","2L","  ","  ","  ","2W","  ","  ","2L"},
        {"  ","  ","  ","  ","2W","  ","  ","  ","  ","  ","2W","  ","  ","  ","  "},
        {"  ","3L","  ","  ","  ","3L","  ","  ","  ","3L","  ","  ","  ","3L","  "},
        {"  ","  ","2L","  ","  ","  ","2L","  ","2L","  ","  ","  ","2L","  ","  "},
        {"3W","  ","  ","2L","  ","  ","  ","2W","  ","  ","  ","2L","  ","  ","3W"},
        {"  ","  ","2L","  ","  ","  ","2L","  ","2L","  ","  ","  ","2L","  ","  "},
        {"  ","3L","  ","  ","  ","3L","  ","  ","  ","3L","  ","  ","  ","3L","  "},
        {"  ","  ","  ","  ","2W","  ","  ","  ","  ","  ","2W","  ","  ","  ","  "},
        {"2L","  ","  ","2W","  ","  ","  ","2L","  ","  ","  ","2W","  ","  ","2L"},
        {"  ","  ","2W","  ","  ","  ","2L","  ","2L","  ","  ","  ","2W","  ","  "},
        {"  ","2W","  ","  ","  ","3L","  ","  ","  ","3L","  ","  ","  ","2W","  "},
        {"3W","  ","  ","2L","  ","  ","  ","3W","  ","  ","  ","2L","  ","  ","3W"}
    };
    public static final boolean RIGHT = true;
    public static final boolean DOWN = false;

    public static final int BOARD_WIDTH = 15;
    public static final int MIDDLE_SQUARE = 7;

    public Board(Tile startingTile) {
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                String squareType = SQUARE_TYPES[i][j];
                int L = (squareType.equals("3L")) ? 3 : (squareType.equals("2L")) ? 2 : 1;
                int W = (squareType.equals("3W")) ? 3 : (squareType.equals("2W")) ? 2 : 1;
                board[i][j] = new Square(L, W);
            }
        }
        board[MIDDLE_SQUARE][MIDDLE_SQUARE].setTile(startingTile);
    }

    public Square[][] getBoard() { return board; }

    public boolean checkWord(int x, int y, boolean direction, String word) {
        int length = word.length();
        final int D = (direction == DOWN) ? 1 : 0;
        final int R = (direction == RIGHT) ? 1 : 0;
        if (x * R + y * D + length - 1 > BOARD_WIDTH) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            int d = D * i;
            int r = R * i;
            if (!board[y + d][x + r].isEmpty() &&
                    board[y + d][x + r].getTile().getLetter() != word.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public List<Character> lettersNeeded(int x, int y, boolean direction, String word) {
        List<Character> needed = new ArrayList<>();
        int length = word.length();
        final int D = (direction == DOWN) ? 1 : 0;
        final int R = (direction == RIGHT) ? 1 : 0;
        for (int i = 0; i < length; i++) {
            if (board[y + i * D][x + i * R].isEmpty()) {
                needed.add(word.charAt(i));
            }
        }

        return needed;
    }

    // tiles is a list of required tiles to fill up the rest of the word, in the order they need to be inserted
    public int insertWord(int x, int y, boolean direction, List<Tile> tiles) {
        List<Square> word = new ArrayList<>();
        final boolean OTHER_DIRECTION = (direction == DOWN) ? DOWN : RIGHT;
        List<ArrayList<Square>> crossingWords = new ArrayList<>();
        int d = 0;
        int r = 0;
        while (!tiles.isEmpty()) {
            if (board[y+d][x+r].isEmpty()) {
                board[y+d][x+r].setTile(tiles.remove(0));
                ArrayList<Square> crossingWord = getWordAt(x + r, y + d, OTHER_DIRECTION);
                if (crossingWord.size() >= 2) { crossingWords.add(crossingWord); }
            }
            word.add(board[y+d][x+r]);
            d += (direction == DOWN) ? 1 : 0;
            r += (direction == RIGHT) ? 1 : 0;
        }

        int points = countValue(word);
        for (ArrayList<Square> crossingWord : crossingWords) {
            points += countValue(crossingWord);
        }
        for (Square square: word) {
            square.setMultUsed();
        }
        if (tiles.size() == 7) { points += 50; }
        return points;
    }


    // get the word in the given direction containing the given location
    private ArrayList<Square> getWordAt(int x, int y, boolean direction) {
        ArrayList<Square> word = new ArrayList<>();
        final int D = (direction == DOWN) ? 1 : 0;
        final int R = (direction == RIGHT) ? 1 : 0;
        int d = 0;
        int r = 0;
        while (y + d >= 0 && x + r >= 0 && !board[y+d][x+r].isEmpty()) {
            d -= D;
            r -= R;
        }
        while (y + d <= BOARD_WIDTH && x + r <= BOARD_WIDTH && !board[y+d][x+r].isEmpty()) {
            word.add(board[y+d][x+r]);
            d += D;
            r += R;
        }
        return word;
    }

    private int countValue(List<Square> word) {
        int wordValue = 0;
        int mult = 1;
        for (Square square : word) {
            int letterValue = square.getTile().getValue();
            if (square.isMultActive()) {
                wordValue += square.getLetterMult() * letterValue;
                mult *= square.getWordMult();
            } else {
                wordValue += letterValue;
            }
        }
        return mult * wordValue;
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
                String str = String.format("%2s |", board[i][j].toString());
                output.append(str);
            }
            output.append(System.lineSeparator());
            output.append(row);
        }
        return output.toString();
    }
}
