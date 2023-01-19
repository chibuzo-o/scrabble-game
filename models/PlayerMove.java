package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class models a move made by a player.
 * It tracks the start coordinate of the move , the word played
 * and the orientation of the move.
 *
 * @author Geoffery Koranteng
 * @version v1.0
 * */
public class PlayerMove implements Serializable {
    private static final long serialVersionUID = 4L;
    private final boolean isHorizontal; // Track the orientation of the move
    private String word; // The word being played
    private final Coordinate start;  //  The starting coordinate of the move
    private final boolean hasNeighbor; // Tracks if the move has any neighbours
    private boolean isSequential;  // Tracks if a move is sequential or not
    private final ArrayList<Tile> newTiles; // A list of new tiles being added to the board
    private String sequences; // A list of sequences if the move is not sequential

    /**
     * Instantiates the a player move ov object.
     * */
    public PlayerMove(boolean isHorizontal, String word, Coordinate start, Board b) {
        this.isHorizontal = isHorizontal;
        this.word = word;
        this.start = start;
        this.newTiles = createNewTileArray(b);
        this.hasNeighbor = checkForNeighbours();
        this.sequences = "";

    }

    /**
     * Returns a list of all new tiles being added to the board
     *
     * @return An arraylist of new words.
     */
    private ArrayList<Tile> createNewTileArray(Board b) {
        ArrayList<Tile> tl = new ArrayList<>();

        if(isHorizontal){
            for(int i = start.getColumn(); i < (word.length() + start.getColumn()); i++){
                Coordinate c = new Coordinate(start.getRow(), i);
                BoardSquare bs  = b.get(c);

                if(bs.isEmpty()){
                    tl.add(new Tile(this.word.charAt(i - start.getColumn())));
                }
            }
        }
        else {
            for(int i = start.getRow(); i < (word.length() + start.getRow()); i++){
                Coordinate c = new Coordinate(i, start.getColumn());
                BoardSquare bs  = b.get(c);

                if(bs.isEmpty()){
                    tl.add(new Tile(this.word.charAt(i - start.getRow())));
                }
            }
        }

        return tl;
    }

    /**
     * Returns true if a move has neighbours on the board
     *
     * @return true if a move has neighbours and false otherwise.
     */
    private boolean checkForNeighbours() {
        return !(word.length() == newTiles.size());
    }

    /**
     * Returns true if a move is horizontal
     *
     * @return true if a move is horizontal and false otherwise.
     */
    public boolean isHorizontal(){
        return isHorizontal;
    }

    /**
     * Returns the word of being played
     *
     * @return the word being played
     */
    public String getWord(){
        return  word;
    }

    /**
     * Returns the list of new tiles being placed on the board
     *
     * @return an arraylist of all new tiles
     */
    public ArrayList<Tile> getNewTiles() {
        return newTiles;
    }

    /**
     * Returns the start coordinate of the word being played
     *
     * @return the start coordinate of the word being played
     */
    public Coordinate getStartCoordinate() {
        return start;
    }

    /**
     * Returns the end coordinate of the word being played
     *
     * @return the end coordinate of the word being played
     */
    public Coordinate getEndCoordinate(){
        if (isHorizontal){
            return new Coordinate(start.getRow(), start.getColumn()+word.length()-1);
        }
        return new Coordinate(start.getRow()+word.length()-1, start.getColumn());
    }

    /**
     * Returns if the move has a neighbour
     *
     * @return true if the move has a neighbour and false otherwise
     */
    public boolean hasNeighbor() {
        return hasNeighbor;
    }

    /**
     * Returns the size of the word
     *
     * @return the size of the word.
     */
    public int getSize() {
        return  word.length();
    }

    /**
     * Returns the letter at a given position in the word being played
     *
     * @param index the index of the letter
     * @return the letter at a given position in the word being played a character
     */
    public char getLetterAt(int index){return word.charAt(index);}

    /**
     * Returns if a move is sequential or  not
     * */
    public boolean isSequential(){
        return isSequential;
    }

    /**
     * Returns the sequence list
     *
     * @return the sequence list
     * */
    public String getSequences() {
        return sequences;
    }

    public void validateSequence(ArrayList<Word> word) {
        assert start == word.get(0).coordinate();
        StringBuilder s = new StringBuilder();

        if (this.isHorizontal) {
            int column = start.getColumn();
            for (Word w : word) {
                if (w.coordinate().getColumn() == column) {
                    s.append(w.tile().getTileValue());
                    isSequential = true;
                    column += 1;
                    continue;
                }
                isSequential = false;
            }
        } else {
            int row = start.getRow();
            for (Word w : word) {
                if (w.coordinate().getRow() == row) {
                    s.append(w.tile().getTileValue());
                    isSequential = true;
                    row += 1;
                    continue;
                }
                isSequential = false;
            }
        }
        this.sequences = s.toString();
    }

    public void setWord(String word) {
        this.word = word;
    }
}
