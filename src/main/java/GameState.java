package main.java;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class holds all the states of the game
 */
public class GameState {
    private Bag bag;
    private Board board;
    private PlayerManager playerManager;
    private int currentPlayerNum;
    private int numberOfPlayers;

    /**
     * Constructs an object that holds Game's important states.
     * This includes the Board, the currentPlayer, the number of points per player, and the player's racks.
     */
    public GameState(){
        this.bag = new Bag();
        this.playerManager = new PlayerManager(System.in, System.out, bag);
        this.board = new Board();
    }

    public GameState(String filePath) throws IOException {
        this.bag = (Bag) loadBagCurrentPlayerNumberOfPlayers(filePath)[0];
        this.currentPlayerNum = (Integer) loadBagCurrentPlayerNumberOfPlayers(filePath)[1];
        this.numberOfPlayers = (Integer) loadBagCurrentPlayerNumberOfPlayers(filePath)[2];
        this.playerManager = loadPlayerInfo(filePath);
        this.board = loadBoardInfo(filePath);
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
        saveBagCurrentPlayerNumberOfPlayers(filePath);
    }

    private PlayerManager loadPlayerInfo(String filePath) throws IOException{
        String line;
        Player[] players = new Player[numberOfPlayers];
        int i = 0;
        BufferedReader bufferedReader = new BufferedReader((new FileReader("playerInfo.csv")));
        while ((line = bufferedReader.readLine()) != null){
            String[] playerInfo = line.split(",");
            char[] tileLetters = playerInfo[2].toCharArray();
            List<Tile> rack = new ArrayList<>();
            for (char tileLetter : tileLetters){
                rack.add(new Tile(tileLetter));
            }
            players[i] = new Player(playerInfo[0]);
            players[i].addPoints(Integer.parseInt(playerInfo[1]));
            players[i].addTiles(rack);
            i++;
        }
        return new PlayerManager(players, currentPlayerNum);
    }

    private Board loadBoardInfo(String filePath) throws IOException{
        String line;
        char[][] boardLetters = new char[Board.BOARD_WIDTH][Board.BOARD_WIDTH];
        BufferedReader bufferedReader = new BufferedReader((new FileReader("boardInfo.csv")));
        int i = 0;
        while ((line = bufferedReader.readLine()) != null) {
            int j = 0;
            String[] boardLettersInRow = line.split(",");
            for (String letter : boardLettersInRow){
                if (!letter.equals("")) {
                    boardLetters[i][j] = letter.charAt(0);
                } else {
                    boardLetters[i][j] = '\0';
                } j++;
            } i++;
        }
        return new Board(boardLetters);
    }

    private Object[] loadBagCurrentPlayerNumberOfPlayers(String filePath) throws IOException{
        Object[] info = new Object[3];
        String line;
        BufferedReader bufferedReader = new BufferedReader((new FileReader("bagInfo.csv")));
        int i = 0;
        while ((line = bufferedReader.readLine()) != null) {
            if (i == 0) {
                String[] lettersAsStrings = line.split(",");
                char[] letters = new char[lettersAsStrings.length];
                for (int j = 0; j < letters.length; j++) {
                    letters[j] = lettersAsStrings[j].charAt(0);
                }
                Bag bag = new Bag(letters);
                info[0] = bag;
                i++;
            } else if (i == 1) {
                String[] currentPlayerNumAsString = line.split(",");
                info[1] = Integer.parseInt(currentPlayerNumAsString[0]);
                i++;
            } else {
                String[] currentPlayerNumAsString = line.split(",");
                info[2] = Integer.parseInt(currentPlayerNumAsString[0]);
                i++;
            }
        }
        return info;
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
        File boardCsv = new File("boardInfo.csv");
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

    private void saveBagCurrentPlayerNumberOfPlayers(String filePath) throws IOException{
        File bagCsv = new File("bagInfo.csv");
        FileWriter bagCsvWriter = new FileWriter(bagCsv);

        List<Character> bagLetters = bag.getBagLetters();
        for (char letter : bagLetters){
            bagCsvWriter.append(letter);
            bagCsvWriter.append(",");
        }

        bagCsvWriter.append("\n");
        bagCsvWriter.append(String.valueOf(currentPlayerNum));
        bagCsvWriter.append("\n");
        bagCsvWriter.append(String.valueOf(numberOfPlayers));

        bagCsvWriter.close();
    }
}
