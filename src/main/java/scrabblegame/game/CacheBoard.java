package main.java.scrabblegame.game;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Takes a Board and creates a board of Cachesquares corresponding to it and caches all the information for it.
 */
public class CacheBoard {
    public final CacheSquare[][] board;
    public final int filledSquares;
    public final boolean direction; // whether the board is for finding words that go Board.RIGHT or Board.DOWN
    private final int D;
    private final int R;
    public CacheBoard(Board copyBoard, boolean direction, HashSet<String> words) {
        this.direction = direction;
        board = new CacheSquare[Board.BOARD_WIDTH][Board.BOARD_WIDTH];
        filledSquares = copyBoard.getFilledSquares();
        D = (direction == Board.DOWN) ? 1 : 0;
        R = (direction == Board.RIGHT) ? 1 : 0;
        Square[][] boardSquares = copyBoard.getBoard();
        for (int j = 0; j < Board.BOARD_WIDTH; j++) {
            for (int i = 0; i < Board.BOARD_WIDTH; i++) {
                board[j][i] = new CacheSquare(boardSquares[j][i]);
            }
        }
        for (int j = 0; j < Board.BOARD_WIDTH; j++) {
            initializeCrossingWords(j);
            for (int i = 0; i < Board.BOARD_WIDTH; i++) {
                initializeStartAtData(i,j);
            }
        }

        initializeValidChars(words);
    }

    public boolean isEmpty() {
        return filledSquares == 0;
    }

    /**
     * Finds and sets the caches for the square at board[y][x]
     */
    private void initializeStartAtData(int x, int y) {
        boolean[] canStart = new boolean[7];
        int[] nthEmptySquare = new int[8];
        CacheSquare[] wordSquares = new CacheSquare[Board.BOARD_WIDTH];
        Arrays.fill(canStart, false);
        if (isEmpty()) {
            // for a move to start at the square, it must be in middle row(/column if Board.DOWN) 0 to 6 slots before Board.MIDDLE_SQUARE
            int squaresFromMiddle = Board.MIDDLE_SQUARE - (x * R + y * D);
            if (x * D + y * R == Board.MIDDLE_SQUARE && 0 <= squaresFromMiddle && squaresFromMiddle <= 6) {
                Arrays.fill(canStart, squaresFromMiddle, 7, true);
                for (int i = 0; i <= 7; i++) {
                    nthEmptySquare[i] = i;
                    wordSquares[i] = board[y + i * D][x + i * R];
                }
            }
        } else if (x - R == -1 || y - D == -1 || board[y - D][x - R].isEmpty()) {
            // placing a move is possible unless the square before this one is nonempty
            int numsSet = 0;
            int fx = x;
            int fy = y;
            boolean hasTouchedLetter = false;
            while (fx < Board.BOARD_WIDTH && fy < Board.BOARD_WIDTH && numsSet <= 7) {
                int diff = fy - y + fx - x;
                if (board[fy][fx].isEmpty()) {
                    wordSquares[diff] = board[fy][fx];
                    nthEmptySquare[numsSet] = diff; // = fy * D + fx * R;
                    numsSet++;
                } else {
                    wordSquares[diff] = board[fy][fx];
                    hasTouchedLetter = true;
                }

                boolean touchesBefore = (fy - R <= -1) && (fx - D <= -1) && !board[fy - D][fx - R].isEmpty();
                boolean touchesAfter = (fy + R >= Board.BOARD_WIDTH) && (fx + D >= Board.BOARD_WIDTH) && !board[fy + R][fx + R].isEmpty();
                hasTouchedLetter = hasTouchedLetter || touchesBefore || touchesAfter;

                if (numsSet > 0) {
                    canStart[numsSet - 1] = hasTouchedLetter;
                }
                fx += R;
                fy += D;
            }
        }
        CacheSquare sq = board[y][x];
        sq.canStart = canStart;
        sq.nthEmptySquare = nthEmptySquare;
        sq.wordSquares = Arrays.copyOf(wordSquares, nthEmptySquare[7]);
    }

    /**
     * sets the word before and after the square in row/column i
     * @param n the row (if direction=Board.DOWN) or column(if direction=Board.RIGHT) we are setting
     */
    private void initializeCrossingWords(int n) {
        String word = "";
        CacheSquare prevEmpty = null;
        int prevWordPoints = 0;
        int currentWordPoints = 0;
        for (int coord = 0; coord < Board.BOARD_WIDTH; coord++) {
            // if going Board.DOWN then trying to find words across so second coord should be varying
            CacheSquare sq = board[R * n + D * coord][D * n + R * coord];
            if (sq.isEmpty()) {
                sq.crossingWordBefore = word;
                prevWordPoints = currentWordPoints;
                currentWordPoints = 0;
                word = "";
                prevEmpty = sq;
            } else {
                word += sq.getTile().getLetter();
                currentWordPoints += sq.getTile().getValue();
            }
            if (prevEmpty != null) {
                prevEmpty.crossingWordAfter = word;
                prevEmpty.crossingWordPoints = prevWordPoints + currentWordPoints;
            }
        }
    }

    private void initializeValidChars(HashSet<String> words) {
        for (int y = 0; y < Board.BOARD_WIDTH; y++) {
            for (int x = 0; x < Board.BOARD_WIDTH; x++) {
                board[y][x].filterValidCharacters(words);
            }
        }

    }
}
