package models;

import views.ScrabbleEvent;
import views.ScrabbleView;
import views.gamesetup.GameTypePanel;

import javax.swing.*;
import java.awt.*;
import java.io.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * class game contains methods that instantiate a game and allows it to operate
 *
 * @author Chibuzo Okpara
 * @version 1.0 25-10-22
 */
public class ScrabbleModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 4L;
    private final Bag bag;
    private final MoveValidator validator;
    private final Dictionary dict;
    private final ArrayList<Player> players;
    private final Board board;
    private final Parser parser;
    private int turn;
    private final ArrayList<ScrabbleView> views;
    private final ArrayList<Tile> exchangeList;
    private final ArrayList<Word> word;
    private final Stack<Integer> playedMoves;
    private final Stack<Integer> undidMoves;

    public static final String SAVE_DIRECTORY = "savedGames/";
    public static final String SAVE_EXTENSION = ".scb";

    public enum EventType {
        PLAY,
        PASS,
        EXCHANGE,
        INVALID_MOVE,
        UNDO, EMPTY_BOARD, REDO, UPDATE_BOARD
    }

    private record Move(ArrayList<Player> players, int turn, Board board){}

    /**
     * constructor for Game class
     *
     * @throws FileNotFoundException if file is not found
     */
    public ScrabbleModel() throws FileNotFoundException {
        this.bag = new Bag();
        this.dict = new Dictionary();
        this.validator = new MoveValidator(dict);
        this.parser = new Parser();
        this.board = new Board(validator);
        this.players = new ArrayList<>();
        this.turn = 0;
        this.views = new ArrayList<>();
        this.exchangeList = new ArrayList<>();
        this.word = new ArrayList<>();
        playedMoves = new Stack<>();
        undidMoves = new Stack<>();
    }

    public ScrabbleModel(ArrayList<Player> players, Bag bag, GameTypePanel.BOARD_TYPE type) throws FileNotFoundException {
        this.bag = bag;
        this.dict = new Dictionary();
        this.validator = new MoveValidator(dict);
        this.parser = new Parser();

        if(type.equals(GameTypePanel.BOARD_TYPE.Default)) this.board = new Board(validator);
        else this.board = new Board(validator, type);
        this.players = players;
        this.turn = 0;
        this.views = new ArrayList<>();
        this.exchangeList = new ArrayList<>();
        this.word = new ArrayList<>();
        playedMoves = new Stack<>();
        undidMoves = new Stack<>();
    }

    public ScrabbleModel(ArrayList<Player> players, Bag bag, Board board, int turn) throws FileNotFoundException {
        this.bag = bag;
        this.dict = new Dictionary();
        this.validator = new MoveValidator(dict);
        this.parser = new Parser();
        this.board = board;
        this.players = players;
        this.turn = turn;
        this.views = new ArrayList<>();
        this.exchangeList = new ArrayList<>();
        this.word = new ArrayList<>();
        playedMoves = new Stack<>();
        undidMoves = new Stack<>();
    }

    /**
     * play() method allows players play the game till it is ended
     * it instantiates and welcomes the players
     * prints the result of the game
     */
    public void play() {
        // print welcome message
        printWelcome();

        //Get player Details and create players
        createPlayers();

        //Start Event Loop
        boolean gameEnded = false;
        while (!gameEnded) {
            drawBoard();
            Command command = parser.getCommand();
            gameEnded = processCommand(command);

            if (gameEnded()) {
                break;
            }
        }

        //print game results
        printResult();
    }

    /**
     * gameEnded() keeps track of if the game is still being played
     *
     * @return true if both the bag and rack are empty then ends the game
     */
    private boolean gameEnded() {
        if (bag.isEmpty()) {
            for (Player p : players) {
                if (p.getRack().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * drawBoard method draws out the board then shows players turn and score of players tiles after turn is played
     */
    private void drawBoard() {
        System.out.println(board);
        for (Player p : players) {
            if (turn == p.getPlayerID()) {
                System.out.print("Player " + turn + " rack: ");
                for (Tile t : p.getRack()) {
                    System.out.print(t.getTileValue() + " ");
                }
                System.out.println();
                System.out.println("Player " + turn + " score: " + p.getScore());
            }
        }
    }

    /**
     * prints the result(winner) of the game
     */
    private void printResult() {
        int winner = players.get(0).getScore();
        int id = 0;
        for (Player i : players) {
            int s = i.getScore();
            if (s > winner) {
                winner = s;
                id = i.getPlayerID();
            }

        }
        System.out.println("The winner is: " + id);
    }

    /**
     * method processes the command words ad executes based on specific case word
     *
     * @param command The command being played
     * @return endgame
     */
    private boolean processCommand(Command command) {
        boolean endGame = false;

        if (command.isUnknown()) {
            System.out.println("Invalid Command....");
            System.out.println("Try Again!");
            return false;
        }

        String commandWord = command.getCommandWord();

        switch (commandWord) {
            case "help" -> printHelp();
            case "play" -> endGame = playMove(command);
            case "pass" -> playerPass();
            case "exchange" -> playExchange(command);
            case "quit" -> endGame = quit(command);
        }

        return endGame;
    }

    /**
     * method queries the player if second word is added to quit
     * should only be "Quit"
     *
     * @param command the command played
     * @return true if the game is ending and false otherwise
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        return true;
    }

    /**
     * method queries the player if second word is not added to exchange
     * tile to be exchanged should be specified
     *
     * @param command
     */
    private void playExchange(Command command) {
        boolean success = false;
        if (!command.hasSecondWord()) {
            System.out.println("Exchange what?");
            return;
        }

        for (Player p : players) {
            if (p.getPlayerID() == turn) {
                while (!success) {
                    success = p.exchangeTiles(new Tile(command.getSecondWord()));

                    if (!success) {
                        System.out.println("You must exchange a tile from your rack!!");
                    }
                }

            }
        }
        changeTurn();
    }

    /**
     * method allows players to skip turns
     */
    private void playerPass() {
        System.out.println("You passed your turn");
        changeTurn();
    }

    /**
     * allows players to make a move with their tiles on the board
     *
     * @param command
     * @return
     */
    private boolean playMove(Command command) {
        boolean success = false;
        ArrayList<String> words;

        if (!command.hasSecondWord()) {
            System.out.println("Play where?");
        }

        if (!command.hasThirdWord()) {
            System.out.println("What word would you like to place at " + command.getThirdWord());
        }

        for (Player p : players) {
            if (p.getPlayerID() == turn) {
                PlayerMove move = p.play(command.getSecondWord(), command.getThirdWord(), board);
                words = board.placeMove(move);

                if (words.size() > 0) success = true;

                if (success) {
                    p.updateRack(move.getWord());
                    p.updateScore(words, dict);
                }
            }
        }

        if (!success) {
            System.out.println("Invalid move, Try again!");
            return false;
        }

        changeTurn();
        return false;
    }

    /**
     * method gives a quick description of the command word to play the game or quit
     */
    private void printHelp() {
        System.out.println("Quit- ends the game");
        System.out.println("Exchange- exchanges a tile between the rack and the bag");
        System.out.println("Play- allow a player to play the game");
        System.out.println("help- Shows the command words and their function");
    }

    /**
     * method instantiates the players of the game
     * defaults to 2 players if a specific number of players is not given
     */
    private void createPlayers() {
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter the number of players (Defaults to 2) : ");
        boolean validInput = false;
        String userInput = "";

        while (!validInput) {
            userInput = input.nextLine();
            if (userInput.equals("")) userInput = "2";

            if (!isNumeric(userInput)) {
                System.out.println("Please Enter a number: ");
            } else validInput = true;
        }

        int playerCount = Integer.parseInt(userInput);
        System.out.println("Player Count: " + playerCount);

        for (int i = 0; i < playerCount; i++) {
            Player player = new Player(i, "", this.bag);
            this.players.add(player);
        }
    }

    /**
     * methods welcomes the players to the scrabble game
     */
    private void printWelcome() {

        System.out.println("\nWelcome to the World's Best Scrabble Game");
        System.out.println("This is a board-and-tile game in which at least 2 players form different words with lettered tiles on a board. \nThe player(s) with the highest score at the end win(s)!");
        System.out.println("Type 'help' if you need help.\n");
    }

    /**
     * method converts the string passed to int
     *
     * @param string
     * @return true if the string is numeric or not
     */
    private static boolean isNumeric(String string) {
        if (string == null || string.equals("")) {
            System.out.println("String cannot be parsed, it is null or empty.");
            return false;
        }

        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException ignored) {
        }

        return false;
    }

    /**
     * method changes moves the turn of the current player to the next
     */
    private void changeTurn() {
        this.turn = (this.turn + 1) % players.size();
    }

    /**
     * method getter players of the game
     * @return ArrayList of player
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * method getter for the board at its current state
     * @return a board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * method getter for player with current turn
     * @return player
     */
    public Player getCurrentPlayer(){
        return players.get(turn);
    }

    /**
     * method to pass a players turn to the next player
     */
    public void pass() {
        playedMoves.push(turn);
        changeTurn();
        updateViews(new ScrabbleEvent(EventType.PASS));
    }

    /**
     * method to add current view of the scrabble game
     * @param view
     */
    public void addScrabbleView(ScrabbleView view) {
        views.add(view);
    }

    /**
     * method to update views in response to a possible event
     * @param e
     */
    public void updateViews(ScrabbleEvent e) {
        for (ScrabbleView v : views) {
            v.update(e);
        }
    }

    /**
     * method to up
     */
    public void exchangeTiles() {
        playedMoves.push(turn);
        this.getCurrentPlayer().saveState();

        for (Tile t : exchangeList) {
            this.getCurrentPlayer().exchangeTiles(t);
        }

        changeTurn();
        updateViews(new ScrabbleEvent(EventType.EXCHANGE));
    }

    public void addToExchangeList(char tileValue) {
        Tile tile = new Tile(tileValue);

        exchangeList.add(tile);
    }

    public void playWord() {
        StringBuilder s = new StringBuilder();
        ArrayList<String> words;

        for (Word w : word) {
            w.coordinate().decrement();
        }

        Coordinate startCoordinate = word.get(0).coordinate();

        if (word.size() > 1) {
            Coordinate secondCoordinate = word.get(1).coordinate();
            startCoordinate.setOrientation(secondCoordinate);
        }

        for (Word w : word) {
            s.append(w.tile().getTileValue());
        }

        String wordToPlay = s.toString();

        PlayerMove move = this.getCurrentPlayer().playMove(startCoordinate, wordToPlay, board);

        move.validateSequence(word);
        words = board.placeMove(move);
        if (!words.isEmpty()) {
            playedMoves.push(turn);
            this.getCurrentPlayer().saveState();

            this.getCurrentPlayer().updateRack(wordToPlay);
            this.getCurrentPlayer().updateScore(words, dict);
            changeTurn();
            updateViews(new ScrabbleEvent(EventType.PLAY));
        } else {
            JOptionPane.showMessageDialog((Component) views.get(0), "Invalid Move.\n\n Try Again!!");
            ArrayList<Coordinate> coordinates = new ArrayList<>();
            word.forEach(w -> coordinates.add(w.coordinate()));

            ScrabbleEvent event = new ScrabbleEvent(EventType.INVALID_MOVE);
            event.setRemoveList(coordinates);
            updateViews(event);
        }
        word.clear();
    }



    public void addToWordList(char tileValue, Coordinate c) {
        Tile tile = new Tile(tileValue);
        word.add(new Word(tile, c));
    }

    public void removeFromWordList(char tileValue, Coordinate c) {
        word.removeIf(w -> w.tile().getTileValue() == tileValue && w.coordinate() == c);
    }

    public void serialize(String filePrefix) throws IOException {
        String filename = SAVE_DIRECTORY + filePrefix + SAVE_EXTENSION;
        File file = new File(filename);

        try {
            FileOutputStream stream = new FileOutputStream(file);

            try (ObjectOutputStream outputStream = new ObjectOutputStream(stream)) {
                outputStream.writeObject(bag);
                outputStream.writeObject(players);
                outputStream.writeObject(board);
                outputStream.writeObject(turn);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error Creating File");
        }
    }

    public static ScrabbleModel deserialize(File file) throws IOException {
        Bag loadedBag;
        ArrayList<Player> loadedPlayers;
        Board loadedBoard;
        int loadedTurn;


        boolean exist = file.exists();

        if (!exist) {
            return null;
        }

        FileInputStream stream = new FileInputStream(file);
        try(ObjectInputStream inputStream = new ObjectInputStream(stream)){

            loadedBag = (Bag)inputStream.readObject();
            loadedPlayers = (ArrayList<Player>) inputStream.readObject();
            loadedBoard = (Board) inputStream.readObject();
            loadedTurn = (int) inputStream.readObject();

        } catch (IOException e){
            throw new IOException("An Error occurred while reading the file");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return new ScrabbleModel(loadedPlayers, loadedBag, loadedBoard, loadedTurn);
    }

    public void updateBoard() {
        for(int i = 0; i < Board.BOARD_SIZE; i++){
            for(int  j = 0;  j < Board.BOARD_SIZE;  j++){
                BoardSquare bs = board.get(i, j);
                if(!bs.isEmpty()){
                    updateViews(new ScrabbleEvent(EventType.UPDATE_BOARD,  new Coordinate(i, j), bs));
                }
                if(bs.isEmpty()){
                    updateViews(new ScrabbleEvent(EventType.EMPTY_BOARD, new Coordinate(i, j), bs));
                }
            }
        }
    }

    public void undoPlay() {
        int newState = playedMoves.pop();
        undidMoves.push(newState);

        this.turn = newState;
        this.getCurrentPlayer().undo();
        board.undo();

        updateViews(new ScrabbleEvent(EventType.UNDO));
        updateBoard();
    }

    //Todo: Redo
    public void redoPlay() {
        int oldState = undidMoves.pop();
        playedMoves.push(oldState);

        this.turn = oldState;
        this.getCurrentPlayer().redo();
        board.redo();

        updateViews(new ScrabbleEvent(EventType.REDO));
        updateBoard();
    }

}
