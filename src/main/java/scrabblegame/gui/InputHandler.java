package main.java.scrabblegame.gui;

import main.java.scrabblegame.game.Board;
import main.java.scrabblegame.game.Player;
import main.java.scrabblegame.game.Square;
import main.java.scrabblegame.game.Tile;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.Math;

public class InputHandler extends MouseAdapter {
    private int indexOfRackTile = -1;
    private boolean moveComplete = false;
    private int currRow = 20;
    private int currColumn = 20;

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

    public int getCurrRow() {
        return currRow;
    }

    public boolean getMoveComplete() {
        return moveComplete;
    }

    public int getIndexOfRackTile() {
        return indexOfRackTile;
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
        return col <= Board.BOARD_WIDTH && row <= Board.BOARD_WIDTH && board.getBoard()[row][col].isEmpty();
    }

    public void updateIndexOfRackTile(Player player, int index) {
        if (index <= player.getRackSize() && this.indexOfRackTile == -1) {
            this.indexOfRackTile = index;
        }
    }

    public void updateBoardAndRack(Board board, Player player) {
        int col = this.currColumn;
        int row = this.currRow;
        Square square = board.getBoard()[row][col];
        Tile newTile = player.getRack().get(indexOfRackTile);
        square.setTile(newTile);
        player.removeTile(newTile.getLetter());
        this.indexOfRackTile = -1;
    }

    public boolean clickCompleteMoveBox(int col, int row) {
        return row == Board.BOARD_WIDTH + 1 && Board.BOARD_WIDTH <= col && col <= Board.BOARD_WIDTH + 2;
    }

    public void processInput(Board board, Player player) {
        if (clickCompleteMoveBox(this.currColumn, this.currRow)) {
            this.moveComplete = true;
        }
        updateIndexOfRackTile(player, convertClickToRackIndex(this.currColumn, this.currRow));
        if (isValidBoardSquare(board, this.currColumn, this.currRow) && this.indexOfRackTile != -1) {
            updateBoardAndRack(board, player);
        }
    }
}
