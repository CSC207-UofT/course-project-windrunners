package main.java.scrabblegame.gui;

import main.java.scrabblegame.game.elements.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles the input provided by the user (through mouse clicks) and interprets the user's actions as they
 * correspond with the Scrabble game.
 */
public class InputHandler extends MouseAdapter {
    private int indexOfRackTile = -1;
    private boolean moveComplete = false;
    private boolean swapMove = false;
    private  List<Tile> tilesToSwap = new ArrayList<>();
    private int currRow = 20;
    private int currColumn = 20;
    private List<Tile> tilesAccumulated = new ArrayList<>();
    private List<Integer> rowsOfTilesAccumulated = new ArrayList<>();
    private List<Integer> colsOfTilesAccumulated = new ArrayList<>();
    private boolean selectingWildcard = false;
    private int indexOfChallengingPlayer = -1;
    private boolean challengeActive = false;

     /**
     * Converts the location that the mouse was clicked in to a square on the Scrabble board
     * @param e MouseEvent representing a click made by the user
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int[] clickedSquare = convertClickToSquare(e.getX(), e.getY());
        currColumn = clickedSquare[0];
        currRow = clickedSquare[1];
//        if (isValidBoardSquare(clickedSquare[0], clickedSquare[1]))
//        System.out.println(convertClickToSquare(e.getX(), e.getY())[0] + "," + convertClickToSquare(e.getX(), e.getY())[1]);
//        System.out.println(convertClickToRackIndex(e.getX(), e.getY()));
    }

     /**
     * Getter method for the current column
     * @return current column
     */
    public int getCurrColumn() {
        return currColumn;
    }
    
    /**
     * Getter method for swap move (whether the swap move button was clicked on)
     * @return true iff swap move was clicked on
     */
    public boolean getSwapMove() {
        return swapMove;
    }

    /**
     * Getter method for the current row
     * @return current row
     */
    public int getCurrRow() {
        return currRow;
    }
    
    /**
     * Getter method for move complete (whether the user has completed their move)
     * @return true iff the user has indicated that they have completed their move by clicking on the complete move
     * button
     */
    public boolean getMoveComplete() {
        return moveComplete;
    }

     /**
     * Returns whether a move has been made
     * @return true iff a move has been made
     */
    public boolean isAMoveMade() {
        return tilesAccumulated.size() > 0 || tilesToSwap.size() > 0;
    }
    
    /**
     * Returns whether a placeMove is being made
     * @return true iff a placeMove is being made
     */
    public  boolean placeMoveBeingMade() {
        return tilesAccumulated.size() > 0;
    }

     /**
     * Returns the row indices of the squares on which a user has placed tiles during their turn
     * @return a list of the row indices
     */
    public List<Integer> getRowsOfTilesAccumulated() {
        return rowsOfTilesAccumulated;
    }

     /**
     * Getter method for the index of the tile in the player's rack of the tile they are clicking on
     * @return the index of the tile in the player's rack that they are clicking on
     */
    public int getIndexOfRackTile() {
        return indexOfRackTile;
    }

    /**
     * Getter method for the tiles that the user has decided to swap
     * @return the tiles that the user has decided to swap
     */
    public List<Tile> getTilesToSwap() {
        return tilesToSwap;
    }

    /**
     * Setter method to indicate that the user is still making a move
     */
    public void setMoveIncomplete() {
        moveComplete = false;
    }

    /**
     * Return whether a user has selected a wildcard
     * @return true iff a user has selected a wild card tile
     */
    public boolean getSelectingWildcard() { return selectingWildcard; }

    /**
    * Return whether a challenge is active
    * @return true iff a challenge is currently active
    */
    public boolean getChallengeActive() { return challengeActive; }

    
    /**
     * Converts the pixel coordinates of the user's click into board coordinates
     * @param x the x-coordinate of the user's click (in pixels)
     * @param y the y-coordinate of the user's click (in pixels)
     * @return a tuple representing the x and y-coodinates of the user's click in terms of the Scrabble board
     */
    public int[] convertClickToSquare(int x, int y) {
        return new int[]{pixelToBoardCoordinate(x), pixelToBoardCoordinate(y)};
    }

    /**
     * Converts the pixel coordinates of the user's click into rack coordinates
     * @param col the column of the user's click, in terms of board coordinates
     * @param row the row of the user's click, in terms of board coordinates
     * @return the coordinates of the user's click in terms of their rack
     */
    public int convertClickToRackIndex(int col, int row) {
        if (row == Board.BOARD_WIDTH + 1) {
            return col - 5;
        }
        else {
            return -1;
        }
    }

    /**
     * Converts a pixel coordinate to a Scrabble board coordinate
     * @param num the pixel coodinate
     * @return the board coordinate
     */
    public int pixelToBoardCoordinate(int num) {
        return num / Renderer.SQUARE_SIZE;
    }

    /**
     * Checks if the coordinates represent a valid square on the board
     * @param board the board
     * @param col the column of the square we are checking (in terms of board coordinates)
     * @param row the row of the square we are checking (in terms of board coordinates)
     * @return true iff the board square is valid
     */
    public boolean isValidBoardSquare(Board board, int col, int row) {
        return col < Board.BOARD_WIDTH && row < Board.BOARD_WIDTH && !swapMove;
    }

    /**
     * Updates the index of a tile on the rack
     * @param player the player whose rack we are updating
     * @param index the index of the player's rack that we are updating
     */
    public void updateIndexOfRackTile(Player player, int index) {
        if (index < player.getRackSize() && (this.indexOfRackTile == -1 || index != -1)) {
            this.indexOfRackTile = index;
            if (this.indexOfRackTile != -1 && !swapMove && player.getRack().get(indexOfRackTile).getLetter() == '~') {
                selectingWildcard = true;
            }
        }
    }

    /**
     * Updates both the board and the rack
     * @param board the board to update
     * @param player the player whose rack we are updating
     */
    public void updateBoardAndRack(Board board, Player player) {
        if (this.indexOfRackTile != -1) { // in which case a tile from the rack is selected
            int col = this.currColumn;
            int row = this.currRow;
            Square square = board.getBoard()[row][col];
            Tile tileInserted = player.getRack().get(indexOfRackTile);
            square.setTile(tileInserted);
            player.removeTile(tileInserted.getLetter());
            this.indexOfRackTile = -1;
            this.tilesAccumulated.add(tileInserted);
            this.rowsOfTilesAccumulated.add(row);
            this.colsOfTilesAccumulated.add(col);
            this.currColumn = 20;
            this.currRow  = 20;
        }
    }

    private boolean clickCompleteMoveBox(int col, int row) {
        return (this.rowsOfTilesAccumulated.size() > 0 || this.tilesToSwap.size() > 0) && row == Board.BOARD_WIDTH + 1
                && Board.BOARD_WIDTH <= col && col <= Board.BOARD_WIDTH + 2;
    }

    private boolean clickSwapMoveBox(int col, int row) {
        return this.rowsOfTilesAccumulated.size() == 0 && row == Board.BOARD_WIDTH - 1
                && Board.BOARD_WIDTH <= col && col <= Board.BOARD_WIDTH + 2 && !this.swapMove;
    }

    /**
     * Handles the case where the player has selected a wild card
     * @param player the player who has selected the wild card
     */
    public void handleWildcard(Player player) {
        if (Board.BOARD_WIDTH + 3 <= currRow && currRow <= Board.BOARD_WIDTH + 4 && currColumn < 13) {
            System.out.println(currColumn + 65 + 13 * (currRow - (Board.BOARD_WIDTH + 3)));
            player.getRack().get(indexOfRackTile).setLetter((char) (currColumn + 65 + 13 * (currRow - (Board.BOARD_WIDTH + 3))));
            selectingWildcard = false;
        }
    }

    /**
     * Handles the case where a challenge has been initiated
     */
    public void handleChallenge() {
        if (currColumn == Board.BOARD_WIDTH) {
            indexOfChallengingPlayer = currRow - 1;
            challengeActive = false;
        }
        if (currRow == Board.BOARD_WIDTH + 3 && 5 <= currColumn && currColumn <= 7) {
            indexOfChallengingPlayer = -1;
            challengeActive = false;
        }
    }

    /**
    * Getter method for the index of the challenging player.
    * @return index of challenging player
    */
    public int getIndexOfChallengingPlayer() { return indexOfChallengingPlayer; }

    /**
     * Processes the input provided by a player through the GUI
     * @param board the board that the game is currently being played on
     * @param player the player whose input we are handling
     */
    public void processInput(Board board, Player player) {
        if (clickCompleteMoveBox(this.currColumn, this.currRow) && !swapMove) {
            challengeActive = true;
        }

        if (clickPassMoveBox(this.currColumn, this.currRow) || clickCompleteMoveBox(this.currColumn, this.currRow)) {
            this.moveComplete = true;
            currColumn = 20;
            currRow = 20;
        }

        if (challengeActive) {
            handleChallenge();
        }
        if (clickSwapMoveBox(this.currColumn, this.currRow)) {
            currColumn = 20;
            currRow = 20;
            swapMove = true;
            indexOfRackTile = -1;
        }

        if (clickCancelSwapMoveBox(this.currColumn, this.currRow)) {
            cancelSwap(player);
        }

        if (selectingWildcard) {
            handleWildcard(player);
        } else {
            updateIndexOfRackTile(player, convertClickToRackIndex(this.currColumn, this.currRow));
        }
        if (isValidBoardSquare(board, this.currColumn, this.currRow)) {
            Square square = board.getBoard()[this.currRow][this.currColumn];
            if (square.isEmpty()) {
                updateBoardAndRack(board, player);
            }
            else {
                removeTileFromBoardIfNecessary(board, player);
            }
        }
        if (this.indexOfRackTile != -1 && swapMove) {
            updateRack(player);
        }
    }

    private void removeTileFromBoardIfNecessary(Board board, Player player) {
        Square squareContainingTile = board.getBoard()[this.currRow][this.currColumn];
        Tile tileToRemove = squareContainingTile.getTile();
        if (tilesAccumulated.contains(tileToRemove)) { // remove tile and add to player's rack
            rowsOfTilesAccumulated.remove(tilesAccumulated.indexOf(tileToRemove));
            colsOfTilesAccumulated.remove(tilesAccumulated.indexOf(tileToRemove));
            tilesAccumulated.remove(tileToRemove);
            squareContainingTile.setTile(null);
            List<Tile> tile = new ArrayList<>();
            tile.add(tileToRemove);
            player.addTiles(tile);
        }
    }

    private void cancelSwap(Player player) {
        swapMove = false;
        player.addTiles(tilesToSwap);
        tilesToSwap = new ArrayList<>();
    }

    private boolean clickCancelSwapMoveBox(int col, int row) {
        return swapMove && row == Board.BOARD_WIDTH + 3
                && Board.BOARD_WIDTH <= col && col <= Board.BOARD_WIDTH + 2;
    }

    private boolean clickPassMoveBox(int col, int row) {
        return row == Board.BOARD_WIDTH - 3 && Board.BOARD_WIDTH <= col && col <= Board.BOARD_WIDTH + 2 &&
                checkIfAccumulatorsResetted();
    }

    /**
     * Checks whether the number of tiles accumulated has been reset
     * @return true iff the number of tiles accumulated has been reset
     */
    public boolean checkIfAccumulatorsResetted() {
        return tilesAccumulated.size() == 0 && !swapMove;
    }

    private void updateRack(Player player) {
        Tile tileToSwap = player.getRack().get(indexOfRackTile);
        Tile tileRemoved = player.removeTile(tileToSwap.getLetter());
        tilesToSwap.add(tileRemoved);
        this.currColumn = 20;
        this.currRow = 20;
        indexOfRackTile = -1;
    }

    /**
     * Completes and move and returns the information associated with teh word that the user has placed (such as the
     * word itself, the direction of the placement of the word)
     * @param board the board that the word has been placed on
     * @return the word info if the move made was a placeMove; otherwise, return null
     */
    public List<Object> completeMove(Board board) {
        if (!swapMove &&
                !board.tilesInSameRowOrColumn(this.rowsOfTilesAccumulated, this.colsOfTilesAccumulated).equals("none")) {
            boolean direction = board.tilesInSameRowOrColumn(this.rowsOfTilesAccumulated, this.colsOfTilesAccumulated).equals("row");
            List<Integer> positionsAlongDirection = direction ? this.colsOfTilesAccumulated : this.rowsOfTilesAccumulated;
            int r = direction ? this.rowsOfTilesAccumulated.get(0) : this.colsOfTilesAccumulated.get(0);
            List<Object> wordInfo = board.findWordFormedByTiles(this.tilesAccumulated, positionsAlongDirection, r, direction);
            wordInfo.add(direction);
            return wordInfo;
        }
        return null;
    }

    /**
    * Resets all accumulators in class
    */
    public void resetAccumulators() {
        tilesAccumulated = new ArrayList<>();
        rowsOfTilesAccumulated = new ArrayList<>();
        colsOfTilesAccumulated = new ArrayList<>();
        swapMove = false;
        tilesToSwap = new ArrayList<>();
    }
}
