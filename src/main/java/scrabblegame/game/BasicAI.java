package main.java.scrabblegame.game;

import java.util.List;

public class BasicAI extends AbstractAI {
    public BasicAI(List<Tile> rack) {
        super(rack);
    }


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
