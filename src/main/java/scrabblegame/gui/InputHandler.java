package main.java.scrabblegame.gui;

import main.java.scrabblegame.game.elements.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void mouseClicked(MouseEvent e) {
        int[] clickedSquare = convertClickToSquare(e.getX(), e.getY());
        currColumn = clickedSquare[0];
        currRow = clickedSquare[1];
//        if (isValidBoardSquare(clickedSquare[0], clickedSquare[1]))
//        System.out.println(convertClickToSquare(e.getX(), e.getY())[0] + "," + convertClickToSquare(e.getX(), e.getY())[1]);
//        System.out.println(convertClickToRackIndex(e.getX(), e.getY()));
    }

    public int getCurrColumn() {
        return currColumn;
    }

    public boolean getSwapMove() {
        return swapMove;
    }

    public int getCurrRow() {
        return currRow;
    }

    public boolean getMoveComplete() {
        return moveComplete;
    }

    public boolean isAMoveMade() {
        return tilesAccumulated.size() > 0 || tilesToSwap.size() > 0;
    }

    public  boolean placeMoveBeingMade() {
        return tilesAccumulated.size() > 0;
    }

    public List<Integer> getRowsOfTilesAccumulated() {
        return rowsOfTilesAccumulated;
    }

    public int getIndexOfRackTile() {
        return indexOfRackTile;
    }

    public List<Tile> getTilesToSwap() {
        return tilesToSwap;
    }

    public void setMoveIncomplete() {
        moveComplete = false;
    }

    public boolean getSelectingWildcard() { return selectingWildcard; }

    public boolean getChallengeActive() { return challengeActive; }

    public int[] convertClickToSquare(int x, int y) {
        return new int[]{pixelToBoardCoordinate(x), pixelToBoardCoordinate(y)};
    }

    public int convertClickToRackIndex(int col, int row) {
        if (row == Board.BOARD_WIDTH + 1) {
            return col - 5;
        }
        else {
            return -1;
        }
    }

    public int pixelToBoardCoordinate(int num) {
        return num / Renderer.SQUARE_SIZE;
    }

    public boolean isValidBoardSquare(Board board, int col, int row) {
        return col < Board.BOARD_WIDTH && row < Board.BOARD_WIDTH && !swapMove;
    }

    public void updateIndexOfRackTile(Player player, int index) {
        if (index < player.getRackSize() && (this.indexOfRackTile == -1 || index != -1)) {
            this.indexOfRackTile = index;
            if (this.indexOfRackTile != -1 && !swapMove && player.getRack().get(indexOfRackTile).getLetter() == '~') {
                selectingWildcard = true;
            }
        }
    }

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

    public void handleWildcard(Player player) {
        if (Board.BOARD_WIDTH + 3 <= currRow && currRow <= Board.BOARD_WIDTH + 4 && currColumn < 13) {
            System.out.println(currColumn + 65 + 13 * (currRow - (Board.BOARD_WIDTH + 3)));
            player.getRack().get(indexOfRackTile).setLetter((char) (currColumn + 65 + 13 * (currRow - (Board.BOARD_WIDTH + 3))));
            selectingWildcard = false;
        }
    }

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

    public int getIndexOfChallengingPlayer() { return indexOfChallengingPlayer; }

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

    public void resetAccumulators() {
        tilesAccumulated = new ArrayList<>();
        rowsOfTilesAccumulated = new ArrayList<>();
        colsOfTilesAccumulated = new ArrayList<>();
        swapMove = false;
        tilesToSwap = new ArrayList<>();
    }
}
