package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

/**
 *super class GamesEntity contains inheritable methods for players and potential computer class
 *
 * @author Chibuzo Okpara
 * @version  1.0 25-10-22
 */
public abstract class GamesEntity implements Serializable {

    private static final long serialVersionUID = 4L;
    private final int id;
    private final Bag bag;
    private final String name;
    private ArrayList<Tile> rack;
    private int score;
    private final Stack<PlayerState> savedState;
    private final Stack<PlayerState> unsavedState;
    public record PlayerState(Integer score, ArrayList<Tile> rack){}

    /**
     * constructor for the GamesEntity class
     * @param id
     * @param name
     * @param bag
     */
    public GamesEntity(int id, String name, Bag bag) {
        this.id = id;
        this.bag = bag;
        this.name = name;
        this.rack = instantiateRack();
        this.score = 0;
        this.savedState = new Stack<>();
        this.unsavedState = new Stack<>();
    }

    /**
     * getter method for player's name
     * @return name of the player.
     */
    public String getName(){
        return this.name;
    }

    /**
     * getter method for the player's ID
     * @return ID of the player.
     */
    public int getPlayerID(){return this.id;}

    /**
     * Method to get score for a player
     * @return player's score.
     */
    public int getScore(){
        return this.score;
    }

    /**
     * Method to get a player's rack
     * @return tile's on the  player's rack if any.
     */
    public ArrayList<Tile> getRack(){
        return this.rack;
    }

    /**
     * method exchanges a specific tile  between a player's rack and the bag
     * @param t
     * @return a random tile from the bag.
     */
    public boolean exchangeTiles(Tile t){
        if(!this.bag.isEmpty()){
            if(this.rack.contains(t)){
                this.rack.remove(t);
                this.bag.add(t);
                this.rack.add(this.bag.getRandomTile());
                return true;
            }
        }
        return false;
    }

    /**
     * method updates the score of each player based on the points attributed to the tiles in their word
     * @param words
     * @param dict
     */
    public void updateScore(ArrayList<String> words, Dictionary dict){
        for(String s : words){
            score += dict.getScore(s);
        }
    }

    /**
     * method instantiates a rack at default size of 7
     * @return instantiated rack
     */
    private ArrayList<Tile> instantiateRack(){
        return this.bag.createRack(Bag.RACK_SIZE);
    }

    /**
     * method executes a player's move on the board
     * @param coordinate
     * @param word
     * @param b
     * @return player's move on the board
     */
    public PlayerMove play(String coordinate, String word, Board b){
        Coordinate c = new Coordinate(coordinate);
        return new PlayerMove(c.isHorizontal(), word, c, b);
    }

    public PlayerMove playMove(Coordinate coordinate, String word, Board b){
        return new PlayerMove(coordinate.isHorizontal(), word, coordinate, b);
    }

    /**
     * method updates the player's rack by removing tiles played by the player and replacing with new tiles
     * @param word
     */
    public void updateRack(String word) {
        for(String s : word.split("")){
            this.rack.remove(new Tile(s));
        }

        if(!bag.isEmpty()){
            for (int i = 0; i < word.length(); i++) {
                this.rack.add(bag.getRandomTile());
            }
        }
    }

    public void saveState(){
        savedState.push(new PlayerState(score, rack));
    }

    public void undo(){
        PlayerState savedPlayer = savedState.pop();
        unsavedState.push(savedPlayer);

        this.score = savedPlayer.score;
        this.rack = savedPlayer.rack;
    }

    public void redo() {
        PlayerState savedPlayer = unsavedState.pop();
        savedState.push(savedPlayer);

        this.score = savedPlayer.score;
        this.rack = savedPlayer.rack;
    }
}
