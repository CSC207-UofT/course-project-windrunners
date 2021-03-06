package main.java.scrabblegame.game.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.*;

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
     * Getter method for the board
     *
     * @return the board
     */
    public Square[][] getBoard() {
        return board;
    }

    /**
     * Getter method for the board that only returns the characters that are on the board.
     *
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
    
    /**
     * Getter method for the number of filled squares on the board.
     *
     * @return number of filled squares.
     */
    public int getFilledSquares() {
        return filledSquares;
    }

    /**
     * Check whether the board is empty (i.e. has no tiles on it)
     *
     * @return true iff the board is empty
     */
    public boolean containsNoTiles() {
        return filledSquares == 0;
    }

    /*
        In this method, and in all the methods below,
        D is 1 if we are checking/inserting the word down the board, and 0 otherwise
        R is 1 if we are checking/inserting the word to the right, and 0 otherwise
        This avoids the need to consider two cases according to the value of direction
    */

    /**
     * check if placing this word is valid (does not check for any dictionary validity)
     *
     * @param x         is the column of the first letter of the word
     * @param y         is the row of the last letter of the word
     * @param direction is the direction along which the word may be placed
     * @param word      is the word that is being checked
     * @return true iff the word can be placed on the board
     */
    public boolean checkWordPlacement(int x, int y, boolean direction, String word) {

        word = word.toUpperCase(Locale.ROOT);

        boolean ifTouchesOtherWord = false;

        int length = word.length();
        final int D = (direction == DOWN) ? 1 : 0;
        final int R = (direction == RIGHT) ? 1 : 0;

        if (x < 0 || y < 0 || x + R * (length - 1) >= BOARD_WIDTH || y + D * (length - 1) >= BOARD_WIDTH) {
            return false;
        }

        if (containsNoTiles()) {
            return ((x * D + y * R == MIDDLE_SQUARE) && (x * R + y * D <= MIDDLE_SQUARE) &&
                    (x * R + y * D + length - 1 >= MIDDLE_SQUARE));
        }

        for (int i = 0; i < length; i++) {
            int d = D * i;
            int r = R * i;
            if (!board[y + d][x + r].isEmpty() &&
                    board[y + d][x + r].getTile().getLetter() != word.charAt(i)) {
                return false;
            }
            boolean oppDirection = !direction;
            if (!board[y + d][x + r].isEmpty() ||
                    letterTouchesAnotherWord(y + d, x + r, oppDirection)) {
                ifTouchesOtherWord = true;
            }
        }

        return ifTouchesOtherWord;
    }

    /**
     * Check if word being placed on the board creates only valid Scrabble words
     *
     * @param x          is the column of the first letter of the word
     * @param y          is the row of the last letter of the word
     * @param direction  is the direction along which the word may be placed
     * @param word       is the word that is being checked
     * @param dictionary is the Scrabble dictionary; used to verify is a word is a valid Scrabble word
     * @return true iff all words created by placing this word are valid Scrabble words
     */
    public boolean checkWordValid(int x, int y, boolean direction, String word, Dictionary dictionary) {
        word = word.toUpperCase(Locale.ROOT);
        if (!dictionary.isValid(word)) {
            return false;
        }

        int length = word.length();
        final int D = (direction == DOWN) ? 1 : 0;
        final int R = (direction == RIGHT) ? 1 : 0;

        for (int i = 0; i < length; i++) {
            int d = D * i;
            int r = R * i;
            boolean oppDirection = !direction;
            if (board[y + d][x + r].isEmpty()) {
                if (!checkIfConnectedWordIsValid(y + d, x + r, dictionary, oppDirection, word.charAt(i))) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * check if the Square at row y, column x is adjacent to at least one other word on the Board
     *
     * @param x         is the column of the Square
     * @param y         is the row of the Square
     * @param direction is the direction along which we wish to check for adjacency
     * @return true iff the Square is adjacent to at least one word on the Board
     */
    private boolean letterTouchesAnotherWord(int y, int x, boolean direction) {
        final int D = (direction == DOWN) ? 1 : 0;
        final int R = (direction == RIGHT) ? 1 : 0;
        boolean ifTouchesAnotherWord = false;
        if (y - D >= 0 && x - R >= 0) {
            ifTouchesAnotherWord = !board[y - D][x - R].isEmpty();
        }
        if (y + D < BOARD_WIDTH && x + R < BOARD_WIDTH) {
            ifTouchesAnotherWord = ifTouchesAnotherWord || !board[y + D][x + R].isEmpty();
        }
        return ifTouchesAnotherWord;
    }

    /**
     * checks whether each of the words on the board that may potentially
     * *               contain this letter are valid Scrabble words
     *
     * @param x          is the column of the letter
     * @param y          is the row of the letter
     * @param direction  is the direction along which the word is to be checked
     * @param dictionary is the Scrabble dictionary; used to verify is a word is a valid Scrabble word
     * @param letter     is the letter being considered; any words on the board that may potentially
     *                   contain this letter are checked for validation
     * @return true iff all the words on the board that may potentially
     * *      *               contain this letter are valid Scrabble words
     */
    private boolean checkIfConnectedWordIsValid(int y, int x, Dictionary dictionary, boolean direction, char letter) {
        StringBuilder word = new StringBuilder();
        final int D = (direction == DOWN) ? 1 : 0;
        final int R = (direction == RIGHT) ? 1 : 0;
        while (y >= D && x >= R && !board[y - D][x - R].isEmpty()) {
            y -= D;
            x -= R;
        }
        while (y < BOARD_WIDTH && x < BOARD_WIDTH && !board[y][x].isEmpty()) {
            word.append(board[y][x].getTile().getLetter());
            y += D;
            x += R;
        }
        word.append(letter);
        y += D;
        x += R;
        while (y < BOARD_WIDTH && x < BOARD_WIDTH && !board[y][x].isEmpty()) {
            word.append(board[y][x].getTile().getLetter());
            y += D;
            x += R;
        }
        return dictionary.isValid(word.toString()) || word.length() == 1;
    }

    /**
     * return a string of the letters of word, not in the board
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
     * insert the required tiles to fill up the rest of the word, in the order they need to be inserted
     *
     * @param x         is the column of the first letter of the word
     * @param y         is the row of the last letter of the word
     * @param direction is the direction along which the Tiles are inserted
     * @param tiles     is a list of required tiles to fill up the rest of the word, in the order they need to be inserted
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
     * @param direction is the given direction
     * @return a List of Squares containing the word in the given direction containing the given location
     */
    private ArrayList<Square> getWordAt(int x, int y, boolean direction) {
        ArrayList<Square> word = new ArrayList<>();
        final int D = (direction == DOWN) ? 1 : 0;
        final int R = (direction == RIGHT) ? 1 : 0;
        int d = 0;
        int r = 0;
        while (y + d >= D && x + r >= R && !board[y + d - D][x + r - R].isEmpty()) {
            d -= D;
            r -= R;
        }

        while (y + d < BOARD_WIDTH && x + r < BOARD_WIDTH && !board[y + d][x + r].isEmpty()) {
            word.add(board[y + d][x + r]);
            d += D;
            r += R;
        }
        return word;
    }

    /**
     * given lists of rows and columns of a list of Tiles, return if the tiles are placed on the same row or column
     * Precondition: rows.size() > 0 && cols.size() > 0
     *
     * @param rows the rows of the tiles placed
     * @param cols the column of the tiles placed
     * @return "row" if all tiles are placed in the same row or rows.size() == 1 (i.e. only one tile is placed)
     * "col" if all tiles are placed in the same column
     * "none" otherwise
     */
    public String tilesInSameRowOrColumn(List<Integer> rows, List<Integer> cols) {
        String rowOrColumn = "none";
        if (rows.stream().distinct().limit(2).count() <= 1) {
            rowOrColumn = "row"; // all Tiles in same row
        } else {
            if (cols.stream().distinct().limit(2).count() <= 1) {
                rowOrColumn = "col"; // all Tiles in same column
            }
        }
        return rowOrColumn;
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
     * return the word formed by placing tiles on the board (in the direction specified),
     * along with the co-ordinates of the first letter. If the position of the tiles on the board is invalid,
     * return an empty list.
     * Precondition: the tiles are placed either in the same row or in the same column
     *
     * @param tiles                   the list of tiles placed on the board by the currentPlayer, in the order in which they were placed
     * @param positionsAlongDirection the co-ordinates of the tiles along the direction in which they are placed
     *                                positionsAlongDirection[k] is the co-ordinate of tiles[k]
     * @param r                       the other co-ordinate of the tiles (from the precondition, this must be the same for all tiles)
     * @param direction               the direction along which the tiles are placed
     * @return a List wordInfo such that wordInfo[0] = row of the first letter of the word,
     * wordInfo[1] = column of the first letter of the word,
     * wordInfo[2] = the word formed by the tiles along direction
     * wordInfo is empty if the tiles are placed on invalid position on the board.
     */
    public List<Object> findWordFormedByTiles(List<Tile> tiles, List<Integer> positionsAlongDirection, int r,
                                              boolean direction) {
        List<Object> wordInfo = new ArrayList<>();
        final int D = (direction == DOWN) ? 1 : 0;
        final int R = (direction == RIGHT) ? 1 : 0;
        List<Integer> duplicate = new ArrayList<>(positionsAlongDirection);
        Collections.sort(duplicate);
        int firstPosition = duplicate.get(0);
        int row = firstPosition * D + r * R;
        int col = firstPosition * R + r * D;
        StringBuilder word = new StringBuilder();
        while (row >= D && col >= R && !board[row - D][col - R].isEmpty()) {
            row -= D;
            col -= R;
        }
        while (row < BOARD_WIDTH && col < BOARD_WIDTH && (!duplicate.isEmpty() || !board[row][col].isEmpty())) {
            if (!board[row][col].isEmpty()) {
                word.append(board[row][col].getTile().getLetter());
            } else {
                int a = direction ? col : row;
                if (a == duplicate.get(0)) {
                    word.append(tiles.get(positionsAlongDirection.indexOf(duplicate.remove(0))).getLetter());
                } else {
                    return new ArrayList<>();
                }
            }
            if (word.length() == 1) {
                wordInfo.add(row);
                wordInfo.add(col);
            }
            row += D;
            col += R;
        }
        wordInfo.add(word.toString());
        return wordInfo;
    }

    /**
     * Return a string representation of the board
     *
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
