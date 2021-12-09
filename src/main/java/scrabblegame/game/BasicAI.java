package main.java.scrabblegame.game;

import java.util.List;

/**
 * An AI that just counts the points a move will make to evaluate it.
 */
public class BasicAI extends AbstractAI {
    public BasicAI(List<Tile> rack) {
        super(rack);
    }
    public static String checkString = "basicAI";

    /**
     * Returns the points that would be made by the prospective move.
     * @param x     the x coordinate where the prospective move starts
     * @param y     the y coordinate where the prospective move starts
     * @param cb    a cacheBoard created from the current board for use in evaluating
     * @param move  the characters from the players rack to be placed in the prospective move
     * @return      the points that the prospective move would make.
     */
    @Override
    public int evaluateMove(int x, int y, CacheBoard cb, char[] move) {
        int[] vals = Tile.toLetterVals(move);
        CacheSquare sq = cb.board[y][x];
        int crossingPoints = 0;
        int mult = 1;
        int wordPoints = 0;
        for (int empty = 0, total = 0; total < sq.nthEmptySquare[vals.length]; total++) {
            CacheSquare wordSq = sq.wordSquares[total];
            if (total == sq.nthEmptySquare[empty]) {
                int W = wordSq.getWordMult();
                int letterPoints = vals[empty] * wordSq.getLetterMult();
                mult *= W;
                wordPoints += letterPoints;
                if (wordSq.crossingWordPoints != 0) {
                    crossingPoints += wordSq.crossingWordPoints + letterPoints;
                }
                empty++;
            } else {
                wordPoints += wordSq.getTile().getValue();
            }
        }
        int totalPoints = mult * wordPoints + crossingPoints;
        if (move.length == 7) {
            totalPoints += 50;
        }
        return totalPoints;
    }
}
