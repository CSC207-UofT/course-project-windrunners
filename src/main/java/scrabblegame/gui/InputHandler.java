package main.java.scrabblegame.gui;

import main.java.scrabblegame.game.Board;
import main.java.scrabblegame.game.Player;
import main.java.scrabblegame.game.Square;
import main.java.scrabblegame.game.Tile;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.Math;
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
        return (int) Math.floor(num / Renderer.SQUARE_SIZE);
    }

    public boolean isValidBoardSquare(Board board, int col, int row) {
        return col < Board.BOARD_WIDTH && row < Board.BOARD_WIDTH && board.getBoard()[row][col].isEmpty() && !swapMove;
    }

    public void updateIndexOfRackTile(Player player, int index) {
        if (index < player.getRackSize() && (this.indexOfRackTile == -1 || index != -1)) {
            this.indexOfRackTile = index;
        }
    }

    public void updateBoardAndRack(Board board, Player player) {
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
    }

    public boolean clickCompleteMoveBox(int col, int row) {
        return (this.rowsOfTilesAccumulated.size() > 0 || this.tilesToSwap.size() > 0) && row == Board.BOARD_WIDTH + 1
                && Board.BOARD_WIDTH <= col && col <= Board.BOARD_WIDTH + 2;
    }

    public boolean clickSwapMoveBox(int col, int row) {
        return this.rowsOfTilesAccumulated.size() == 0 && row == Board.BOARD_WIDTH - 1
                && Board.BOARD_WIDTH <= col && col <= Board.BOARD_WIDTH + 2 && this.tilesToSwap.size() == 0;
    }

    public void processInput(Board board, Player player) {
        System.out.println(indexOfRackTile);
        if (clickSwapMoveBox(this.currColumn, this.currRow)) {
            currColumn = 20;
            currRow = 20;
            swapMove = true;
            indexOfRackTile = -1;
        }
        if (clickCompleteMoveBox(this.currColumn, this.currRow)) {
            this.moveComplete = true;
            currColumn = 20;
            currRow = 20;
        }
        updateIndexOfRackTile(player, convertClickToRackIndex(this.currColumn, this.currRow));
        if (isValidBoardSquare(board, this.currColumn, this.currRow) && this.indexOfRackTile != -1) {
            updateBoardAndRack(board, player);
        }
        if (this.indexOfRackTile != -1 && swapMove) {
            updateRack(player);
        }
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
                System.out.println("die");
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
