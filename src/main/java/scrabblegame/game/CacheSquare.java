package main.java.scrabblegame.game;

import java.util.HashSet;

/**
 * Sqaure which has lots of information cached to make checking lots of words faster. Is initialized by CacheBoard.
 *
 * canStart[n] is whether a word that places (n+1) tile from the rack starting at the square can be place legally
 * (i.e. it doesn't go out of bounds, it would touch a filled square etc.)
 *
 * nthEmptySquare[n-1] is the distance to the nth empty square from this square.
 *
 * wordSquares are the squares that could be involved in a word with at most 7 tiles placed
 *
 * crossingWordBefore/After is the letters before and after this tile.
 *
 * crossingWordPoints is the points of the crossing word.
 **/
class CacheSquare extends Square {
    public boolean[] canStart = new boolean[7];
    public int[] nthEmptySquare = new int[8];
    public CacheSquare[] wordSquares = new CacheSquare[1]; //
    public HashSet<Character> validChars = new HashSet<>(); // the characters that could complete crossing word

    public String crossingWordBefore = "";
    public String crossingWordAfter = "";
    public int crossingWordPoints = 0;

    public CacheSquare(Square sq) {
        super(sq);
    }

    public void filterValidCharacters(HashSet<String> words) {
        if (crossingWordAfter.length() + crossingWordBefore.length() > 0) {
            for (char c = 'A'; c <= 'Z'; c++) {
                String str = crossingWordBefore + c + crossingWordAfter;
                if (words.contains(str)) {
                    validChars.add(c);
                }
            }
        }
    }

    /**
     * checks whether you can place the letters without adding invalid words
     *
     * @param letters the letters to be checked. Should letters should be empty and placeable in the square
     * @return returns true if for each n, placing letters[n-1] wouldn't create an invalid word and false otherwise
     */
    public boolean areCrossingWordsValid(char[] letters) {
        for (int i = 0; i < letters.length; i++) {
            HashSet<Character> validChars = wordSquares[nthEmptySquare[i]].validChars;
            if (!validChars.contains(letters[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * returns the word created by a move with these letters at this square
     */
    public String getWord(char[] letters) {
        StringBuilder sb = new StringBuilder();
        for (int empty = 0, total = 0; total < nthEmptySquare[letters.length]; total++) {
            if (total == nthEmptySquare[empty]) {
                sb.append(letters[empty]);
                empty++;
            } else {
                sb.append(wordSquares[total].getTile().getLetter());
            }
        }
        return sb.toString();
    }


    /** Returns true if the letters can be legally placed in the first available empty squares
     * from this square and only form valid scrabble words
     * @param letters   the letters placed to create the word. Should have 1 <= letters.length <= 7.
     * @param words     the words which can be place
     * @return true if and only if placing letters in the first letters.length empty squares from this square
     * is a legal scrabble move.
     */
    public boolean canPlaceLetters(char[] letters, HashSet<String> words) {
        return canStart[letters.length - 1] && areCrossingWordsValid(letters) && words.contains(getWord(letters));
    }
}