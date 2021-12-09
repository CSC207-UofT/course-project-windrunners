package main.java.scrabblegame.game.ai;

import main.java.scrabblegame.game.elements.*;
import main.java.scrabblegame.game.elements.Dictionary;
import main.java.scrabblegame.game.moves.*;

import java.util.*;

/**
 * A class which implements most the details an AI needs. Specifically it handles iterating through
 * every move that can be made. A concrete implementation only needs to implement a method that
 * takes a prospective and evaulates it
 */
public abstract class AbstractAI {
    private final HashSet<String> words;
    private final char[] charRack;
    public AbstractAI(List<Tile> rack) {
        char[] charRack = new char[rack.size()];
        for (int i = 0; i< rack.size(); i++) {
            charRack[i] = rack.get(i).getLetter();
        }
        Arrays.sort(charRack);
        Dictionary dict = new Dictionary();
        words = dict.getDictionary();
        this.charRack = charRack;
    }

    private static int factorial(int n) {
        int num = 1;
        for (int i = 1; i <= n; i++) num *= i;
        return num;
    }

    private static boolean containsWildcard(char[] perm) {
        for (char c : perm) {
            if (c == '~') {
                return true;
            }
        }
        return false;
    }

    private static int[] numToPerm(int n, int k, int permNum) {
        int fact = factorial(n);
        int[] perm = new int[n];
        for (int i = 0; i < n; i++) {
            perm[i] = i;
        }
        for (int i = 0; i < n; i++) {
            fact /= (n - i);
            int j = permNum / fact + i;
            permNum %= fact;
            int temp = perm[i];
            perm[i] = perm[j];
            perm[j] = temp;
        }
        return Arrays.copyOf(perm, k);
    }

    private static ArrayList<char[]> getRackPerms(int lengthOfPerms, char[] letters) {
        int fact = factorial(letters.length);
        int gaps = factorial(letters.length - lengthOfPerms);
        HashSet<String> permsSet = new HashSet<>();
        ArrayList<char[]> perms = new ArrayList<>(fact / gaps);
        for (int i = 0; i < fact; i += gaps) {
            int[] numPerm = numToPerm(letters.length, lengthOfPerms, i);
            char[] perm = new char[lengthOfPerms];
            for (int j = 0; j < lengthOfPerms; j++) {
                perm[j] = letters[numPerm[j]];
            }
            if (permsSet.add(new String(perm))) {
                perms.add(perm);
            }
        }

        return perms;
    }

    private static ArrayList<char[]> getRackPerms(char[] letters) {
        ArrayList<char[]> perms = new ArrayList<>(factorial(letters.length) * 2);
        for (int i = 1; i <= letters.length; i++) {
            perms.addAll(getRackPerms(i, letters));
        }
        return perms;
    }

    private ArrayList<char[]> geValidMovesAtSquare(CacheSquare sq, ArrayList<char[]> perms) {
        ArrayList<char[]> validPerms = new ArrayList<>();
        for (char[] perm : perms) {
            if (!containsWildcard(perm)) {
                if (sq.canPlaceLetters(perm, words)) {
                    validPerms.add(perm);
                }
            } else {
                validPerms.addAll(sq.getValidPermsWildcard(perm, words));
            }
        }
        return validPerms;
    }

    /**
     * Takes a prospective move and evaluates it. The larger return value the better the move.
     * @param x     the x coordinate where the prospective move starts
     * @param y     the y coordinate where the prospective move starts
     * @param cb    a cacheBoard created from the current board for use in evaluating
     * @param move  the characters from the players rack to be placed in the prospective move
     * @return      an integer representing the quality of the prospective move
     */
    public abstract int evaluateMove(int x, int y, CacheBoard cb, char[] move);

    /**
     * Takes the board and makes the best move possible
     * @param board The current board
     * @return the move to be made
     */
    public Move makeMove(Board board) {
        ArrayList<char[]> perms = getRackPerms(charRack);
        int bestPoints = Integer.MIN_VALUE;
        int bestX = -1;
        int bestY = -1;
        boolean bestDirection = false;
        String bestWord = "";
        for (int i = 0; i <= 1; i++) {
            boolean direction = i == 0 ? Board.DOWN : Board.RIGHT;
            CacheBoard cb = new CacheBoard(board, direction, words);
            for (int x = 0; x < Board.BOARD_WIDTH; x++) {
                for (int y = 0; y < Board.BOARD_WIDTH; y++) {
                    CacheSquare sq = cb.board[y][x];
                    ArrayList<char[]> validMoves = geValidMovesAtSquare(sq, perms);
                    for (char[] move : validMoves) {
                        int points = evaluateMove(x, y, cb, move);
                        if (points > bestPoints) {
                            bestPoints = points;
                            bestX = x;
                            bestY = y;
                            bestDirection = direction;
                            bestWord = sq.getWord(move);
                        }
                    }
                }
            }
        }
        if (bestX == -1) {
            return new PassMove();
        } else {
            return new PlaceMove(bestX, bestY, bestDirection, bestWord.toUpperCase(Locale.ENGLISH));
        }
    }
}
