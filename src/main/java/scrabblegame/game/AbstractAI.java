package main.java.scrabblegame.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public abstract class AbstractAI {

    private final HashSet<String> words;
    private final Board board;
    private final char[] charRack;

    public AbstractAI(char[] rack) {
        Arrays.sort(rack);
        Dictionary dict = new Dictionary();
        words = dict.getDictionary();
        board = new Board();
        this.charRack = rack;
    }

    public static int factorial(int n) {
        int num = 1;
        for (int i = 1; i <= n; i++) num *= i;
        return num;
    }
    public static int[] numToPerm(int n, int k, int permNum) {
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
    public ArrayList<char[]> getRackPerms(int lengthOfPerms) {
        int fact = factorial(charRack.length);
        int gaps = factorial(charRack.length-lengthOfPerms);
        HashSet<String> permsSet = new HashSet<>();
        ArrayList<char[]> perms = new ArrayList<>(fact / gaps);
        for (int i = 0; i < fact; i += gaps) {
            int[] numPerm = numToPerm(charRack.length, lengthOfPerms, i);
            char[] perm = new char[lengthOfPerms];
            for (int j = 0; j < lengthOfPerms; j++) {
                perm[j] = charRack[numPerm[j]];
            }
            if (permsSet.add(new String(perm))) {
                perms.add(perm);
            }
        }

        return perms;
    }
    public ArrayList<char[]> getRackPerms() {
        ArrayList<char[]> perms = new ArrayList<>(factorial(charRack.length) * 2);
        for (int i = 1; i <= charRack.length; i++) {
            perms.addAll(getRackPerms(i));
        }
        return perms;
    }

    private ArrayList<char[]> geValidMovesAtSquare(int x, int y, ArrayList<char[]> perms, CacheBoard cb) {
        ArrayList<char[]> validPerms = new ArrayList<>(perms.size());
        CacheSquare cs = cb.board[y][x];
        for (char[] perm : perms) {
            if (cs.canPlaceLetters(perm, words)) {
                validPerms.add(perm);
            }
        }
        return validPerms;
    }
    public abstract int evaluateMove(int x, int y, CacheBoard cb, char[] move);

    public Move makeMove() {
        ArrayList<char[]> perms = getRackPerms();
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
                    ArrayList<char[]> validMoves = geValidMovesAtSquare(x, y, perms, cb);
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
        return new PlaceMove(bestX, bestY, bestDirection, bestWord);
    }
}


