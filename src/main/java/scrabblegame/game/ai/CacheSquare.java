package main.java.scrabblegame.game.ai;

import main.java.scrabblegame.game.elements.Square;

import java.util.HashSet;
import java.util.ArrayList;

/**
 * Square which has lots of information cached to make checking lots of words faster. Is initialized by CacheBoard.
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
    public boolean[] canStart;
    public int[] nthEmptySquare;
    public CacheSquare[] wordSquares; //
    public HashSet<Character> validChars; // the characters that could complete crossing word

    public String crossingWordBefore;
    public String crossingWordAfter;
    public int crossingWordPoints;

    public CacheSquare(Square sq) {
        super(sq);
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
            char letter = letters[i];
            if (letter != '~' && !validChars.contains(letter)) {
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

    /**
     * Get the perms that are valid if one of the characters is a wildcard.
     *
     * @param letters   the letters placed to create the word. Should have 1 <= letters.length <= 7
     *                  and should all be upper case or a wildcard.
     * @param words     the words which can be place
     * @return returns the list of perms which would create valid words when one character is a wild card.
     * The character replacing a wildcard is lower case.
     */
    public ArrayList<char[]> getValidPermsWildcard(char[] letters, HashSet<String> words) {
        ArrayList<char[]> validPerms = new ArrayList<>();
        if (!canStart[letters.length - 1] || !areCrossingWordsValid(letters)) {
            return validPerms;
        }
        String[] wordParts = getWord(letters).split("~", -1);
        ArrayList<HashSet<Character>> nthWildcardValidChars = new ArrayList<>(wordParts.length - 1);

        for (int i = 0, prevWildcardSlotWord = -1; i < wordParts.length - 1; i++) {
            prevWildcardSlotWord += wordParts[i].length() + 1;
            nthWildcardValidChars.add(wordSquares[prevWildcardSlotWord].validChars);
        }

        ArrayList<char[]> wildcardChoices = new ArrayList<>();
        wildcardChoices.add(new char[nthWildcardValidChars.size()]);
        for (int i = 0; i < nthWildcardValidChars.size(); i++) {
            ArrayList<char[]> temp = new ArrayList<>();
            HashSet<Character> validChars = nthWildcardValidChars.get(i);
            for (char[] wildcardChoice : wildcardChoices) {
                for (char letter : validChars) {
                    char[] copy = wildcardChoice.clone();
                    copy[i] = letter;
                    temp.add(copy);
                }
            }
            wildcardChoices = temp;
        }
        for (char[] wildcardChoice : wildcardChoices) {
            StringBuilder sb = new StringBuilder(wordParts[0]);
            for (int i = 0; i < wordParts.length - 1; i++) {
                sb.append(wildcardChoice[i]);
                sb.append(wordParts[i + 1]);
            }
            if (words.contains(sb.toString())) {
                char[] dupe = letters.clone();
                for (int i = 0, wildcardsSeen = 0; i < letters.length; i++) {
                    if (letters[i] == '~') {
                        char wcLetter = wildcardChoice[wildcardsSeen];
                        dupe[i] = Character.toLowerCase(wcLetter);
                        wildcardsSeen++;
                    }
                }
                validPerms.add(dupe);
            }
        }
        return validPerms;
    }
}