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
    private final int numberOfPlayers;

    /**
     * Constructs an object that holds Game's important states.
     */
    public GameState(){
        this.bag = new Bag();
        this.playerManager = new PlayerManager(System.in, System.out, bag);
        this.board = new Board();
        this.currentPlayerNum = playerManager.getCurrentPlayerNum();
        this.numberOfPlayers = playerManager.getPlayers().length;
    }

    public GameState(String filePath) {
        this.bag = (Bag) loadBagCurrentPlayerNumberOfPlayers(filePath)[0];
        this.currentPlayerNum = (Integer) loadBagCurrentPlayerNumberOfPlayers(filePath)[1];
        this.numberOfPlayers = (Integer) loadBagCurrentPlayerNumberOfPlayers(filePath)[2];
        this.playerManager = loadPlayerInfo(filePath);
        this.board = loadBoardInfo(filePath);
    }

    public Bag getBag() {
        return bag;
    }

    public Board getBoard() {
        return board;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

//    public int getCurrentPlayerNum() {
//        return currentPlayerNum;
//    }
//
//    public int getNumberOfPlayers() {
//        return numberOfPlayers;
//    }

    public void setPlayerManager(PlayerManager playerManager){
        this.playerManager = playerManager;
        this.currentPlayerNum = playerManager.getCurrentPlayerNum();
    }
//
    public void setBoard(Board board){
        this.board = board;
    }
//
    public void setBag(Bag bag){
        this.bag = bag;
    }

    public void saveGameState(String filePath){
        savePlayerInfo(filePath);
        saveBoardInfo(filePath);
        saveBagCurrentPlayerNumberOfPlayers(filePath);
    }

    private PlayerManager loadPlayerInfo(String filePath) {
        Player[] players = new Player[numberOfPlayers];
        try {
            String line;
            int i = 0;
            BufferedReader bufferedReader = new BufferedReader((new FileReader("playerInfo.csv")));
            while ((line = bufferedReader.readLine()) != null) {
                String[] playerInfo = line.split(",");
                char[] tileLetters = playerInfo[2].toCharArray();
                List<Tile> rack = new ArrayList<>();
                for (char tileLetter : tileLetters) {
                    rack.add(new Tile(tileLetter));
                }
                players[i] = new Player(playerInfo[0]);
                players[i].addPoints(Integer.parseInt(playerInfo[1]));
                players[i].addTiles(rack);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new PlayerManager(players, currentPlayerNum);
    }

    private Board loadBoardInfo(String filePath) {
        char[][] boardLetters = new char[Board.BOARD_WIDTH][Board.BOARD_WIDTH];
        try {
            String line;
            BufferedReader bufferedReader = new BufferedReader((new FileReader("boardInfo.csv")));
            int i = 0;
            while ((line = bufferedReader.readLine()) != null) {
                int j = 0;
                String[] boardLettersInRow = line.split(",");
                for (String letter : boardLettersInRow) {
                    if (!letter.equals("")) {
                        boardLetters[i][j] = letter.charAt(0);
                    } else {
                        boardLetters[i][j] = '\0';
                    }
                    j++;
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Board(boardLetters);
    }

    private Object[] loadBagCurrentPlayerNumberOfPlayers(String filePath){
        Object[] info = new Object[3];
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return info;
    }

    private void savePlayerInfo(String filePath){
        try {
            File playerInfoCsv = new File("playerInfo.csv");
            FileWriter playerInfoCsvWriter = new FileWriter(playerInfoCsv);

            Player[] playerList = playerManager.getPlayers();

            for (Player player : playerList) {
                String playerInfo = player.getName() + "," + player.getPoints() + "," + player.getRackLetters();
                playerInfoCsvWriter.append(playerInfo);
                playerInfoCsvWriter.append("\n");
            }
            playerInfoCsvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveBoardInfo(String filePath){
        try {
            File boardCsv = new File("boardInfo.csv");
            FileWriter boardCsvWriter = new FileWriter(boardCsv);

            char[][] boardLetters = board.getBoardLetters();
            for (char[] row : boardLetters) {
                for (char square : row) {
                    boardCsvWriter.append(square);
                    boardCsvWriter.append(",");
                }
                boardCsvWriter.append("\n");
            }
            boardCsvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveBagCurrentPlayerNumberOfPlayers(String filePath){
        try {
            File bagCsv = new File("bagInfo.csv");
            FileWriter bagCsvWriter = new FileWriter(bagCsv);

            List<Character> bagLetters = bag.getBagLetters();
            for (char letter : bagLetters) {
                bagCsvWriter.append(letter);
                bagCsvWriter.append(",");
            }

            bagCsvWriter.append("\n");
            bagCsvWriter.append(String.valueOf(currentPlayerNum));
            bagCsvWriter.append("\n");
            bagCsvWriter.append(String.valueOf(numberOfPlayers));

            bagCsvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
