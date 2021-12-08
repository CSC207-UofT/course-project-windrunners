package main.java.scrabblegame.game;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of Move created when a Player decides to place a word on the Board
 */
public class PlaceMove implements Move {
    private final int x;
    private final int y;
    private final boolean direction;
    private final String word;

    /**
     * Class constructor.
     *
     * @param word      is the word to be placed on the Board
     * @param x         is the column of the first letter of the word
     * @param y         is the row of the first letter of the word
     * @param direction is the direction along which the word is to be placed
     */
    public PlaceMove(int x, int y, boolean direction, String word) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.word = word;
    }

    /**
     * Attempts to place the given word on the board in the given direction starting at the given coordinates.
     */
    @Override
    public void execute(Bag bag, PlayerManager pm, Board board, Dictionary dict) throws Exception {
        if (!board.checkWord(x, y, direction, word, dict)) {
            // throw invalid word error
            throw new Exception();
        }
        List<Character> lettersNeeded = board.lettersNeeded(x, y, direction, word);
        List<Character> lettersNeededCopy = new ArrayList<>(lettersNeeded)
        if (!pm.currentPlayerHasLetters(lettersNeeded)) {
            PlayerRack = pm.getCurrentPlayer().rack;
            int numberOfWildcardTiles = 0;
            for(Tile tile: PlayerRack){
                if(tile.getLetter() == '~'){
                    numberOfWildcardTiles = numberOfWildcardTiles + 1;
                }
            }
            if(numberOfWildcardTiles = 0){
                throw new Exception();
            }
            else{
                List<Character> playerLetters = Tile.tilesToChars(pm.getCurrentPlayer().rack);
                for (Character playerLetter : playerLetters) {
                    lettersNeededCopy.remove(playerLetter);
                }
                if(numberOfWildcardTiles < lettersNeededCopy.size())
                    throw new Exception();
            }
        }
        List<Tile> tilesForWord = new ArrayList<>();
        for (char c : lettersNeeded) {
            newTile = new Tile(c);
            if(lettersNeededCopy.contains(c)){
                lettersNeededCopy.remove(c);
                newTile.setValue(0);
            }
            tilesForWord.add(newTile);
        }
        int points = board.insertWord(x, y, direction, tilesForWord);
        List<Tile> tilesToAdd = bag.drawTiles(tilesForWord.size());
        pm.updateCurrentPlayer(points, tilesToAdd, tilesForWord);
        PlayerRack = pm.getCurrentPlayer().rack;
        for(int i = 0; i < PlayerRack.size();){
            if(PlayerRack[i].getLetter() == '~'){
                PlayerRack.remove(PlayerRack[i]);
            }
            else{
                i++;
            }
        }
    }
}
