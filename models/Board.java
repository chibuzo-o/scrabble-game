package models;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import views.gamesetup.GameTypePanel;

import java.io.*;
import java.util.*;

/**
 * Class Board models the board in the scrabble board game
 *
 * @author Chibuzo Okpara, Boma Iyaye & Geoffery Koranteng
 * @version 1.0 16/10/2022
 */
public class Board implements Serializable {
    public static final int BOARD_SIZE = 15;
    private static final long serialVersionUID = 4L;
    private HashMap<Coordinate, String> horizontalWords;
    private HashMap<Coordinate, String> verticalWords;
    private ArrayList<ArrayList<BoardSquare>> squares;
    private MoveValidator validator;
    private boolean firstword;
    private final Stack<PlayerMove> playedMoves;
    private final Stack<PlayerMove> undidMoves;

    public static String DEFAULT_BOARD = "boardMain.json";
    public static String CUSTOM_BOARD_ONE = "boardOne.json";
    public static String CUSTOM_BOARD_TWO = "boardTwo.json";

    /**
     * Constructor for objects of class Board
     */
    public Board(MoveValidator validator) {
        squares = generateGrid();

        try {
            populateMultipliers(DEFAULT_BOARD);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        horizontalWords = new HashMap<>();
        verticalWords = new HashMap<>();
        this.validator = validator;
        this.firstword = true;
        this.playedMoves = new Stack<>();
        this.undidMoves = new Stack<>();

    }

    public Board(MoveValidator validator, GameTypePanel.BOARD_TYPE type) {
        // initialise instance variables
        squares = generateGrid();

        try {
            if(type.equals(GameTypePanel.BOARD_TYPE.Custom_One)){
                populateMultipliers(CUSTOM_BOARD_ONE);
            } else if (type.equals(GameTypePanel.BOARD_TYPE.Custom_Two)) {
                populateMultipliers(CUSTOM_BOARD_TWO);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        horizontalWords = new HashMap<>();
        verticalWords = new HashMap<>();
        this.validator = validator;
        this.firstword = true;
        this.playedMoves = new Stack<>();
        this.undidMoves = new Stack<>();
    }

    private void populateMultipliers(String filename) throws IOException {
        ArrayList<Multiplier> multipliers = parse(filename);

        for(Multiplier m : multipliers){
            this.get(m.coordinate()).setMultiplier(m.multiplier());
        }
    }

    /**
     * Generates the 15 by 15 grid which the game is played on
     * @return a 15 by 15 arraylist of board squares
     */
    private ArrayList<ArrayList<BoardSquare>> generateGrid() {
        ArrayList<ArrayList<BoardSquare>> sq = new ArrayList<>();

        for(int i = 0; i < BOARD_SIZE; i++){
            sq.add(createArray());
        }
       return sq;
    }

    /**
     *
     * @return
     */
    private ArrayList<BoardSquare> createArray(){
        ArrayList<BoardSquare> ar   = new ArrayList<>();

        for(int j = 0; j < BOARD_SIZE; j++) {
            ar.add(new BoardSquare(Bonus.NM));
        }

        return ar;
    }

    /**
     * gets the box square at a specific coordinate
     *
     * @param c  the coordinate of the box square to be returned
     * @return the square at a specific coordinate
     */
    public BoardSquare get(Coordinate c){
        return get(c.getRow(), c.getColumn());
    }

    public BoardSquare get(int row, int  col){
        return squares.get(row).get(col);
    }

    /**
     *Sets a board square to a coordinate
     * @param c coordinate the board square is set to
     * @param bs board square to be set
     */
    public void set(Coordinate c, BoardSquare bs){
        squares.get(c.getRow()).set(c.getColumn(), bs);
    }

    /**
     * gets the size of the board
     *
     * @return the number of squares
     */
    public int getSize(){
        return squares.size();
    }

    /**
     * Returns the horizontal word at a specific coordinate.
     * @param c coordinate where the first letter of the word starts from
     * @return the word horizontal from the coordinate
     */
    public String getHorizontalWordAt(Coordinate c){
        if(horizontalWords.containsKey(c)) return horizontalWords.get(c);
        return "";
    }

    /**
     *
     * @param c Returns the horizontal word at a specific coordinate.
     * @return  the word horizontal from the coordinate
     */
    public String getVerticalWordAt(Coordinate c){
        if(verticalWords.containsKey(c))return verticalWords.get(c);
        return "";
    }

    /**
     * places the word played by the player on the board if the word is valid.
     * @param move the move the player makes
     * @return the word played by the player
     */
    public ArrayList<String> placeMove(PlayerMove move){
        ArrayList<String> words = new ArrayList<>();

        if(this.validator.validate(move, this)){
            if(move.isHorizontal()){
                for (int i = 0; i < move.getSize(); i++) {
                    int row = move.getStartCoordinate().getRow();
                    int column = i + move.getStartCoordinate().getColumn();

                    BoardSquare bs = squares.get(row).get(column);
                    if(bs.isEmpty()){
                        bs.setTile(new Tile(move.getLetterAt(i)));

                    }
                }
            }
            else {
                for (int i = 0; i < move.getSize(); i++) {
                    int row = i + move.getStartCoordinate().getRow();
                    int column = move.getStartCoordinate().getColumn();

                    BoardSquare bs = squares.get(row).get(column);
                    if(bs.isEmpty()){
                        bs.setTile(new Tile(move.getLetterAt(i)));
                    }
                }
            }

            words.add(move.getWord());
            populateWordList();
            playedMoves.push(move);
        }
        if(this.firstword) firstword = false;
        return words;
    }

    /**
     * Adds the words played to a total list of all words played
     */
    public void populateWordList(){
        horizontalWords.clear();
        verticalWords.clear();

        //Populate Horizontal Words
        for(int row = 0; row < getSize(); row++){
            int column = 0;
            while(column < getSize()){
              StringBuilder word = new StringBuilder();
              Coordinate start = new Coordinate(row,column);

              Coordinate c = new Coordinate(row,column);

              while(!this.get(c).isEmpty()){
                  word.append(this.get(c).getTile().getTileValue());
                  column++;
                  c = new Coordinate(row, column);
              }

              if(!word.toString().equals("")){
                  for(int i = 0; i < word.toString().length(); i++){
                      horizontalWords.put(start, word.toString());
                      start = new Coordinate(row, start.getColumn() + 1);
                  }
              }

              column++;
            }
        }

        //Populate Vertical Words
        for(int column = 0; column < getSize(); column++){
            int row = 0;
            while(row < getSize()){
                StringBuilder word = new StringBuilder();
                Coordinate start = new Coordinate(row,column);

                Coordinate c = new Coordinate(row,column);

                while(!this.get(c).isEmpty()){
                    word.append(this.get(c).getTile().getTileValue());
                    row++;
                    c = new Coordinate(row, column);
                }

                if(!word.toString().equals("")){
                    for(int i = 0; i < word.toString().length(); i++){
                        horizontalWords.put(start, word.toString());
                        start = new Coordinate(start.getRow()+1, column);
                    }
                }

                row++;
            }
        }

    }

    public void placeLetters(int row, int col, Tile t){
        squares.get(row).get(col).setTile(t);
    }

    /**
     * returns the board as a string
     * @return a string rtepresentation of the board
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int i = 0 ;

        s.append("    | A | B | C | D | E | F | G | H | I | J | K | L | M | N | 0 |\n");
        for(ArrayList<BoardSquare> ar : squares){
            if(i > 9){
                s.append("  ").append(i).append("|");
            }
            else s.append("  ").append(i).append(" |");

            for(BoardSquare bs : ar ){
                if(bs.isEmpty()){
                   s.append("   |");
                }
                else{
                    s.append(" ").append(bs.getTile().getTileValue()).append(" |");
                }

            }

            if(i > 9){
                s.append("  ").append(i);
            }
            else s.append("  ").append(i).append(" ");
            i++;

            s.append("\n");
        }
        s.append("    | A | B | C | D | E | F | G | H | I | J | K | L | M | N | 0 |\n");
        return s.toString();
    }

    public enum Bonus{DW ,TL ,DL ,TW,NM}

    /**
     * Returns the first word played
     * @return the first word played
     */
    public boolean isFirstWord(){
        return this.firstword;
    }

    public void undo() {
        PlayerMove move = playedMoves.pop();
        undidMoves.push(move);

        removeMove(move);
    }

    public void redo() {
        PlayerMove move = undidMoves.pop();
        playedMoves.push(move);

        redoMove(move);
    }

    private void redoMove(PlayerMove move) {
        if(move.isHorizontal()){
            for (int i = 0; i < move.getSize(); i++) {
                int row = move.getStartCoordinate().getRow();
                int column = i + move.getStartCoordinate().getColumn();

                placeLetters(row,  column, new Tile(move.getLetterAt(i)));
            }
        }
        else  {
            for (int i = 0; i < move.getSize(); i++) {
                int row = i + move.getStartCoordinate().getRow();
                int column = move.getStartCoordinate().getColumn();

                placeLetters(row,  column, new Tile(move.getLetterAt(i)));
            }
        }
    }

    private void removeMove(PlayerMove move) {
        if(move.isHorizontal()){
            for (int i = 0; i < move.getSize(); i++) {
                int row = move.getStartCoordinate().getRow();
                int column = i + move.getStartCoordinate().getColumn();

                BoardSquare bs = squares.get(row).get(column);
                bs.empty();
            }
        }
        else  {
            for (int i = 0; i < move.getSize(); i++) {
                int row = i + move.getStartCoordinate().getRow();
                int column = move.getStartCoordinate().getColumn();

                BoardSquare bs = squares.get(row).get(column);
                bs.empty();
            }
        }

    }

    public ArrayList<Multiplier> parse(String fileSuffix) throws IOException {
        ArrayList<Multiplier> multipliers = new ArrayList<>();
        String filename = "config/" + fileSuffix;
        JSONParser p = new JSONParser();
        try {
            FileReader r = new FileReader(filename);
            JSONObject jj = (JSONObject) p.parse(r);
            String name = (String) ((JSONObject) jj).get("name");
            String type = (String) ((JSONObject) jj).get("type");
            JSONObject boardConfig = (JSONObject) ((JSONObject) jj).get("board-config");

            for (Object o : boardConfig.keySet()) {
                String key = (String) o;
                JSONArray values = (JSONArray) boardConfig.get(key);
                for (Object value : values) {
                    multipliers.add(new Multiplier(key, new Coordinate((String) value)));
                }
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return multipliers;
    }

}
