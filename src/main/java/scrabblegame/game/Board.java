package main.java.scrabblegame.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * A Scrabble Board, which is a collection of Squares, each of which comes in different types
 * A string representation of the Board (along with the type of Squares) is given below
 */
public class Board {
    // used to indicate the direction along which a word is to be placed on the Board
    public static final boolean RIGHT = true;
    public static final boolean DOWN = false;
    public static final int BOARD_WIDTH = 15; // the width of the Board
    public static final int MIDDLE_SQUARE = 7; // the co-ordinates of the middle square are row 7, column 7
    private static final String[][] SQUARE_TYPES = {
            {"3W", "  ", "  ", "2L", "  ", "  ", "  ", "3W", "  ", "  ", "  ", "2L", "  ", "  ", "3W"},
            {"  ", "2W", "  ", "  ", "  ", "3L", "  ", "  ", "  ", "3L", "  ", "  ", "  ", "2W", "  "},
            {"  ", "  ", "2W", "  ", "  ", "  ", "2L", "  ", "2L", "  ", "  ", "  ", "2W", "  ", "  "},
            {"2L", "  ", "  ", "2W", "  ", "  ", "  ", "2L", "  ", "  ", "  ", "2W", "  ", "  ", "2L"},
            {"  ", "  ", "  ", "  ", "2W", "  ", "  ", "  ", "  ", "  ", "2W", "  ", "  ", "  ", "  "},
            {"  ", "3L", "  ", "  ", "  ", "3L", "  ", "  ", "  ", "3L", "  ", "  ", "  ", "3L", "  "},
            {"  ", "  ", "2L", "  ", "  ", "  ", "2L", "  ", "2L", "  ", "  ", "  ", "2L", "  ", "  "},
            {"3W", "  ", "  ", "2L", "  ", "  ", "  ", "2W", "  ", "  ", "  ", "2L", "  ", "  ", "3W"},
            {"  ", "  ", "2L", "  ", "  ", "  ", "2L", "  ", "2L", "  ", "  ", "  ", "2L", "  ", "  "},
            {"  ", "3L", "  ", "  ", "  ", "3L", "  ", "  ", "  ", "3L", "  ", "  ", "  ", "3L", "  "},
            {"  ", "  ", "  ", "  ", "2W", "  ", "  ", "  ", "  ", "  ", "2W", "  ", "  ", "  ", "  "},
            {"2L", "  ", "  ", "2W", "  ", "  ", "  ", "2L", "  ", "  ", "  ", "2W", "  ", "  ", "2L"},
            {"  ", "  ", "2W", "  ", "  ", "  ", "2L", "  ", "2L", "  ", "  ", "  ", "2W", "  ", "  "},
            {"  ", "2W", "  ", "  ", "  ", "3L", "  ", "  ", "  ", "3L", "  ", "  ", "  ", "2W", "  "},
            {"3W", "  ", "  ", "2L", "  ", "  ", "  ", "3W", "  ", "  ", "  ", "2L", "  ", "  ", "3W"}
    };
    private final Square[][] board = new Square[BOARD_WIDTH][BOARD_WIDTH];
    private int filledSquares; // represents the number of Squares which are empty

    /**
     * Class constructor. All the squares on the Board are initially empty
     */
    public Board() {
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                String squareType = SQUARE_TYPES[i][j];
                int L = (squareType.equals("3L")) ? 3 : (squareType.equals("2L")) ? 2 : 1;
                int W = (squareType.equals("3W")) ? 3 : (squareType.equals("2W")) ? 2 : 1;
                board[i][j] = new Square(L, W);
            }
        }
    }

    /**
     * Class constructor. Fills the squares on the Board with the tiles in the locations as specified by boardLetters
     *
     * @param boardLetters a BOARD_WIDTH^2 array with the letters that are already on the Board
     */
    public Board(char[][] boardLetters) {
        int filled = 0;
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                String squareType = SQUARE_TYPES[i][j];
                int L = (squareType.equals("3L")) ? 3 : (squareType.equals("2L")) ? 2 : 1;
                int W = (squareType.equals("3W")) ? 3 : (squareType.equals("2W")) ? 2 : 1;
                board[i][j] = new Square(L, W);
                if (boardLetters[i][j] != '\0') {
                    filled++;
                    board[i][j].setTile(new Tile(boardLetters[i][j]));
                    board[i][j].setMultUsed();
                }
            }
        }
        this.filledSquares = filled;
    }

    /**
     * Copy constructor. Make a deep copy of the Board from another Board
     *
     * @param that the Board to copy from
     */
    public Board(Board that) {
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                board[i][j] = new Square(that.board[i][j]);
            }
        }
        this.filledSquares = that.getFilledSquares();
    }

    /**
     * @return the board
     */
    public Square[][] getBoard() {
        return board;
    }

    /**
     * @return a 2D array of chars containing only the string values of the tiles that are on the board
     */
    public char[][] getBoardLetters() {
        char[][] boardLetters = new char[BOARD_WIDTH][BOARD_WIDTH];
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (!board[i][j].isEmpty())
                    boardLetters[i][j] = board[i][j].getTile().getLetter();
            }
        }
        return boardLetters;
    }

    public int getFilledSquares() {
        return filledSquares;
    }

    /**
     * @return true iff the board is empty
     */
    public boolean containsNoTiles() {
        return filledSquares == 0;
    }

    public boolean checkWord(int x, int y, boolean direction, String word, Dictionary dictionary) {
        if (!isLegalPlacement(x, y, direction, word)) {
            return false;
        }
        List<String> words = getCrossingWords(x, y, direction, word);
        words.add(word);
        for (String str : words) {
            if (!dictionary.isValid(str)) {
                return false;
            }
        }
        return true;
    }
    /*
        In this method, and in all the methods below,
        D is 1 if we are checking/inserting the word down the board, and 0 otherwise
        R is 1 if we are checking/inserting the word to the right, and 0 otherwise
        This avoids the need to consider two cases according to the value of direction
    */

    /**
     * check if the given word can be placed on the board at the given location in the given direction.
     * Specifically, it wouldn't go over the bounds of the board or overwrite an already filled square
     * and is adjacent to an already existing word (or the board is empty, and it uses the middle square)
     *
     * @param x          is the column of the first letter of the word
     * @param y          is the row of the first letter of the word
     * @param direction  is the direction along which the word may be placed
     * @param word       is the word that is being checked
     * @return true iff the word can be placed on the board
     */
    public boolean isLegalPlacement(int x, int y, boolean direction, String word) {
        word = word.toUpperCase(Locale.ROOT);
        final int length = word.length();
        final int D = (direction == DOWN) ? 1 : 0;
        final int R = (direction == RIGHT) ? 1 : 0;

        if (x < 0 || y < 0 || x + R * (length - 1) >= BOARD_WIDTH || y + D * (length - 1) >= BOARD_WIDTH) {
            return false;
        }

        // check letter before and after placement are empty
        if ( (y + D*length < BOARD_WIDTH && x + R*length < BOARD_WIDTH && !board[y + D*length][x + R*length].isEmpty()) ||
                (y - D >= 0 && x - R >= 0 && !board[y - D][x - R].isEmpty()) ) {
            return false;
        }

        if (containsNoTiles()) {
            return (x * R + y * D <= MIDDLE_SQUARE) && (x * R + y * D + length - 1 >= MIDDLE_SQUARE)
                    && (x * D + y * R == MIDDLE_SQUARE);
        }

        boolean touchesFilledSquare = false;
        for (int i = 0; i < length; i++) {
            int d = D * i;
            int r = R * i;
            if (!board[y + d][x + r].isEmpty()) {
                touchesFilledSquare = true;
                if (board[y + d][x + r].getTile().getLetter() != word.charAt(i)) {
                    return false;
                }
            }
            touchesFilledSquare = touchesFilledSquare || letterTouchesAnotherWord(x + r ,y + d, !direction);
        }
        return touchesFilledSquare;
    }

    /**
     * Returns a List of words that cross the squares where the word is to be placed and would be
     * created/modified by adding word starting at board[y][x] in direction
     * Assumes that the word can be placed legally (in particular it won't run out of bounds of the board)
     *
     * @param x         is the column of the first letter of the word
     * @param y         is the row of the first letter of the word
     * @param direction is the direction along which the word may be placed
     * @param word      is the word that would be pla
     * @return a list of Strings that represent words that would cross the given word
     */
    public List<String> getCrossingWords(int x, int y, boolean direction, String word) {
        final int R = (direction == RIGHT) ? 1 : 0;
        final int D = (direction == DOWN) ? 1 : 0;
        final boolean oppDir = !direction;
        List<String> words = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            int r = R * i;
            int d = D * i;
            if (board[y + d][x + r].isEmpty()) {
                StringBuilder addWord = new StringBuilder();
                for (Square square : SquaresOnLineFrom(x + r, y + d, oppDir, false)) {
                    addWord.append(square.getTile().getLetter());
                }
                addWord.append(word.charAt(i));
                for (Square square : SquaresOnLineFrom(x + r, y + d, oppDir, true)) {
                    addWord.append(square.getTile().getLetter());
                }
                if (addWord.length() > 1) {
                    words.add(addWord.toString());
                }
            }
        }
        return words;
    }

    /**
     * check if the Square at row y, column x is adjacent to at least one other word on the Board
     *
     * @param x         is the column of the Square
     * @param y         is the row of the Square
     * @param direction is the direction along which we wish to check for adjacency
     * @return true iff the Square is adjacent to at least one word on the Board
     */
    private boolean letterTouchesAnotherWord(int x, int y, boolean direction) {
        final int D = (direction == DOWN) ? 1 : 0;
        final int R = (direction == RIGHT) ? 1 : 0;
        boolean touchesBefore = (y - D >= 0) && (x - R >= 0) && !board[y - D][x - R].isEmpty();
        boolean touchesAfter = (y + D < BOARD_WIDTH) && (x + R < BOARD_WIDTH) && !board[y + D][x + R].isEmpty();
        return touchesBefore || touchesAfter;
    }

    /**
     * Get the Squares that are on a straight uninterrupted line in a direction from the given spot
     *
     * @param x          is the column of the letter
     * @param y          is the row of the letter
     * @param direction  is whether to go across (right) or down
     * @param goForward  is true to go forward and false to go backwards
     * @return ArrayList of squares (ordered left to right/top to bottom) that are traversed by going either
     *   forward or backward from the given square (board[y][x]) in the given direction until reaching an empty square
     */
    private ArrayList<Square> SquaresOnLineFrom(int x, int y, boolean direction, boolean goForward) {
        final int F = goForward ? 1 : -1;
        final int R = (direction == RIGHT) ? F : 0;
        final int D = (direction == DOWN) ? F : 0;
        ArrayList<Square> word = new ArrayList<>();
        while (y + D >= 0 && x + R >= 0 && y + D < BOARD_WIDTH && x + R < BOARD_WIDTH
                && !board[y + D][x + R].isEmpty()) {
            word.add(board[y+D][x+R]);
            y += D;
            x += R;
        }
        if (!goForward) {
            Collections.reverse(word);
        }
        return word;
    }

    /**
     * return a string of the letters of word that are not already on the board
     *
     * @param x         is the column of the first letter of the word
     * @param y         is the row of the last letter of the word
     * @param direction is the direction along which the word may be placed
     * @param word      is the word to be placed on the board
     * @return a List of the letters of the word that are not in the board
     */
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

    /**
     * insert the required tiles to fill up the rest of the word, in the order they need to be inserted.
     * assumes that it has already been checked that it can be inserted
     *
     * @param x         is the column of the first letter of the word
     * @param y         is the row of the first letter of the word
     * @param direction is the direction along which the Tiles are inserted
     * @param tiles     is a list of required tiles to fill up the rest of the word,
     *                  in the order they need to be inserted. Does not mutate it.
     * @return the points earned on inserting this word
     */
    public int insertWord(int x, int y, boolean direction, List<Tile> tiles) {
        List<Square> word = new ArrayList<>();
        final boolean OTHER_DIRECTION = (direction == DOWN) ? RIGHT : DOWN;
        List<ArrayList<Square>> crossingWords = new ArrayList<>();
        int d = 0;
        int r = 0;
        int used = 0;
        while (used < tiles.size()) {
            if (board[y + d][x + r].isEmpty()) {
                board[y + d][x + r].setTile(tiles.get(used));
                filledSquares++;
                used++;
                ArrayList<Square> crossingWord = getWordAt(x + r, y + d, OTHER_DIRECTION);
                if (crossingWord.size() >= 2) {
                    crossingWords.add(crossingWord);
                }
            }
            word.add(board[y + d][x + r]);
            d += (direction == DOWN) ? 1 : 0;
            r += (direction == RIGHT) ? 1 : 0;
        }

        int points = countValue(word);
        for (ArrayList<Square> crossingWord : crossingWords) {
            points += countValue(crossingWord);
        }
        for (Square square : word) {
            square.setMultUsed();
        }
        if (tiles.size() == 7) {
            points += 50;
        }
        return points;
    }


    /**
     * get the word in the given direction containing the given location
     *
     * @param x         is the column of the given location
     * @param y         is the row of the given location
     * @param direction is the direction the word should be in
     * @return a List of Squares containing the word in the given direction containing the given location
     */
    private ArrayList<Square> getWordAt(int x, int y, boolean direction) {
        ArrayList<Square> word = SquaresOnLineFrom(x, y, direction, false);
        word.add(board[x][y]);
        word.addAll(SquaresOnLineFrom(x, y, direction, true));
        return word;
    }

    /**
     * return the points earned according to the word placed on the List word
     *
     * @param word a List of Squares used to calculate the points
     * @return the points earned according to the word placed on the List word
     */
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

    /**
     * @return a string representation of the Board
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("  ");
        for (int i = 0; i < BOARD_WIDTH; i++) {
            output.append(" ".repeat(i < 9 ? 2 : 1));
            output.append(i + 1);
            output.append(" ");
        }
        output.append(System.lineSeparator());
        String row = "  " + "-".repeat(4 * BOARD_WIDTH + 1) + System.lineSeparator();

        output.append(row);
        for (int i = 0; i < BOARD_WIDTH; i++) {
            output.append((char) (i + 65));
            output.append(" |");
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
