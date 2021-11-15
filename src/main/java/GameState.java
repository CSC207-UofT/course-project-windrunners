package main.java;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class keeps track of all the important information related to the game.
 * Has methods for saving and loading the game to/from csv files.
 */
public class GameState {
    private Bag bag;  // the current state of the bag
    private Board board;  // the current state of the board
    private PlayerManager playerManager;  // the current state of the player manager
    private int currentPlayerNum;  // the index of the current player in the list of players in the player manager
    private final int numberOfPlayers;

    /**
     * Class constructor. Initializes a GameState object that represents the beginning of a game.
     */
    public GameState() {
        this.bag = new Bag();
        this.playerManager = new PlayerManager(System.in, System.out, bag);
        this.board = new Board();
        this.currentPlayerNum = playerManager.getCurrentPlayerNum();
        this.numberOfPlayers = playerManager.getPlayers().length;
    }

    /**
     * Class constructor. Initializes a GameState object
     * @param bag the current state of the bag
     * @param playerManager the current state of the playerManager
     * @param board the current state of the board
     */
    public GameState(Bag bag, PlayerManager playerManager, Board board) {
        this.bag = bag;
        this.playerManager = playerManager;
        this.board = board;
        this.currentPlayerNum = playerManager.getCurrentPlayerNum();
        this.numberOfPlayers = playerManager.getPlayers().length;
    }

    /**
     * Class constructor. Initialize a GameState object from a saved game (that is saved into three csv files)
     * @param filePath path to the folder containing all the filed relevant to the game
     */
    public GameState(String filePath) {
        this.bag = (Bag) loadBagCurrentPlayerNumberOfPlayers(filePath)[0];
        this.currentPlayerNum = (Integer) loadBagCurrentPlayerNumberOfPlayers(filePath)[1];
        this.numberOfPlayers = (Integer) loadBagCurrentPlayerNumberOfPlayers(filePath)[2];
        this.playerManager = loadPlayerInfo(filePath);
        this.board = loadBoardInfo(filePath);
    }

    /**
     * @return the bag
     */
    public Bag getBag() {
        return bag;
    }

    /**
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @return the playerManager
     */
    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    /**
     * Saves the game state into three csv files.
     * @param filePath the path to folder to save the three csv files to
     */
    public void saveGameState(String filePath){
        savePlayerInfo(filePath);
        saveBoardInfo(filePath);
        saveBagCurrentPlayerNumberOfPlayers(filePath);
    }

    /**
     * Helper method that loads the player information (player's name, number of points, and rack) from a csv file
     * @param filePath the path to the folder with the csv files
     * @return the playerManager represented in playerInfo.csv
     */
    private PlayerManager loadPlayerInfo(String filePath) {
        Player[] players = new Player[numberOfPlayers];
        try {
            String line;
            int i = 0;
            BufferedReader bufferedReader =
                    new BufferedReader((new FileReader(filePath + "playerInfo.csv")));
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

    /**
     * Helper method that loads the board information (which tiles are placed where) from a csv file
     * @param filePath the path to the folder with the csv files
     * @return the board represented in boardInfo.csv
     */
    private Board loadBoardInfo(String filePath) {
        char[][] boardLetters = new char[Board.BOARD_WIDTH][Board.BOARD_WIDTH];
        try {
            String line;
            BufferedReader bufferedReader =
                    new BufferedReader((new FileReader(filePath + "boardInfo.csv")));
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

    /**
     * Helper method that loads the information about the bag, who the current player is,
     * and the number of players in the game from a csv file
     * @param filePath the path to the folder with the csv files
     * @return the board represented in bagInfo.csv
     */
    private Object[] loadBagCurrentPlayerNumberOfPlayers(String filePath){
        Object[] info = new Object[3];
        try {
            String line;
            BufferedReader bufferedReader =
                    new BufferedReader((new FileReader(filePath + "bagInfo.csv")));
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
                    String[] numPlayersAsString = line.split(",");
                    info[2] = Integer.parseInt(numPlayersAsString[0]);
                    i++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * Helper method to save the information about the players (names, number of points so far, rack) in a csv file
     * @param filePath the path to the folder to save the csv file in
     */
    private void savePlayerInfo(String filePath){
        try {
            File playerInfoCsv = new File(filePath + "playerInfo.csv");
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

    /**
     * Helper method to save the information about the baord in a csv file
     * @param filePath the path to the folder to save the csv file in
     */
    private void saveBoardInfo(String filePath){
        try {
            File boardCsv = new File(filePath + "boardInfo.csv");
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

    /**
     * Helper method to save the information about the bag, who the current player is,
     * and the number of players in the game in a csv file
     * @param filePath the path to the folder to save the csv file in
     */
    private void saveBagCurrentPlayerNumberOfPlayers(String filePath){
        try {
            File bagCsv = new File(filePath + "bagInfo.csv");
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
