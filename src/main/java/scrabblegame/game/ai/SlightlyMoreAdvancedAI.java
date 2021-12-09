package main.java.scrabblegame.game.ai;

import main.java.scrabblegame.game.elements.Tile;

import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

/**
 * An AI with a slightly more complex evaluate method. Specifically it accounts for some letters being more
 * useful than others(i.e. "s" is very useful for connecting words) and so should only be used if they increase
 * the points of the move by enough. Currently, the weights are complete guesswork, however with research they could
 * be improved.
 */

public class SlightlyMoreAdvancedAI extends AbstractAI{
    public SlightlyMoreAdvancedAI(List<Tile> rack) {
        super(rack);
    }
    public static String checkString = "slightlyMoreAdvancedAI";
    /**
     * Takes a prospective move and evaluates it.
     * @param x     the x coordinate where the prospective move starts
     * @param y     the y coordinate where the prospective move starts
     * @param cb    a cacheBoard created from the current board for use in evaluating
     * @param move  the characters from the players rack to be placed in the prospective move
     * @return      The points that would be made by the move minus the weights of the letters from the
     * players rack required to make it.
     */
    @Override
    public int evaluateMove(int x, int y, CacheBoard cb, char[] move) {
        int[] vals = Tile.toLetterVals(move);
        CacheSquare sq = cb.board[y][x];
        int crossingPoints = 0;
        int mult = 1;
        int wordPoints = 0;
        int letterWeights = 0;
        for (int empty = 0, total = 0; total < sq.nthEmptySquare[vals.length]; total++) {
            CacheSquare wordSq = sq.wordSquares[total];
            if (total == sq.nthEmptySquare[empty]) {
                int W = wordSq.getWordMult();
                int letterPoints = vals[empty] * wordSq.getLetterMult();
                char letter = move[empty];
                if ('a' <= letter && letter <= 'z') {
                    letter = '~';
                }
                letterWeights += CHAR_WEIGHTS.get(letter);
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
        return totalPoints + letterWeights;
    }

    private static final Map<Character, Integer> CHAR_WEIGHTS = Map.ofEntries(
            entry('A', -2),
            entry('B', -1),
            entry('C', 0),
            entry('D', -1),
            entry('E', -2),
            entry('F', 0),
            entry('G', 0),
            entry('H', -1),
            entry('I', -1),
            entry('J', -4),
            entry('K', -4),
            entry('L', -1),
            entry('M', -1),
            entry('N', -1),
            entry('O', -1),
            entry('P', 0),
            entry('Q', -3),
            entry('R', -1),
            entry('S', -5),
            entry('T', 0),
            entry('U', -2),
            entry('V', -1),
            entry('W', -1),
            entry('X', -3),
            entry('Y', -1),
            entry('Z', -4),
            entry('~', -6)
    );

}
