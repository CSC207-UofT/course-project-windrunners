package main.java;

import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class holds all the states of the game
 */
public class GameState {
    private Bag bag;
    private Board board;
    private PlayerManager playerManager;

    /**
     * Constructs an object that holds Game's important states.
     * This includes the Board, the currentPlayer, the number of points per player, and the player's racks.
     */
    public GameState(){
        this.bag = new Bag();
        this.playerManager = new PlayerManager(System.in, System.out, bag);
        this.board = new Board();
        // update it with the main method in Game
    }

//    public void setPlayerManager(PlayerManager playerManager){
//        this.playerManager = playerManager;
//    }
//
//    public void setBoard(Board board){
//        this.board = board;
//    }
//
//    public void setBag(Bag bag){
//        this.bag = bag;
//    }

    public void saveGameState(String filePath) throws IOException{
        savePlayerInfo(filePath);
        saveBoardInfo(filePath);
        saveBag(filePath);
    }

    private void savePlayerInfo(String filePath) throws IOException{
        File playerInfoCsv = new File("playerInfo.csv");
        FileWriter playerInfoCsvWriter = new FileWriter(playerInfoCsv);

        Player[] playerList = playerManager.getPlayers();

        for (Player player : playerList) {
            String playerInfo = player.getName() + "," + player.getPoints() + "," + player.getRackLetters();
            playerInfoCsvWriter.append(playerInfo);
            playerInfoCsvWriter.append("\n");
        }
        playerInfoCsvWriter.close();
    }

    private void saveBoardInfo(String filePath) throws IOException{
        File boardCsv = new File("playerInfo.csv");
        FileWriter boardCsvWriter = new FileWriter(boardCsv);

        char[][] boardLetters = board.getBoardLetters();
        for (char[] row : boardLetters){
            for (char square : row){
                boardCsvWriter.append(square);
                boardCsvWriter.append(",");
            }
            boardCsvWriter.append("\n");
        }
        boardCsvWriter.close();
    }

    private void saveBag(String filePath) throws IOException{
        File bagCsv = new File("bag.csv");
        FileWriter bagCsvWriter = new FileWriter(bagCsv);

        List<Character> bagLetters = bag.getBagLetters();
        for (char letter : bagLetters){
            bagCsvWriter.append(letter);
            bagCsvWriter.append(",");
        }
        bagCsvWriter.close();
    }
}
