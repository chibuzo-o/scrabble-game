package models;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class BoardSquares models the individual squares which tiles are placed on in the scrabble board game.
 *
 * @author Boma Iyaye  101197410
 * @version 1.0 16/10/2022
 */
public class BoardSquare implements Serializable

{
    /**
     * The combination of letters and numbers that identify a board square
     */
    private Tile tile;
    private static final long serialVersionUID = 4L;
    private  boolean isEmpty;

    public void setMultiplier(Board.Bonus multiplier) {
        this.multiplier = multiplier;
    }
    public void setMultiplier(String multiplier) {
        switch (multiplier) {
            case "3WS" -> this.multiplier = Board.Bonus.TW;
            case "3LS" -> this.multiplier = Board.Bonus.TL;
            case "2WS" -> this.multiplier = Board.Bonus.DW;
            case "2LS" -> this.multiplier = Board.Bonus.DL;
        }
    }

    private Board.Bonus multiplier;

    /**
     * Constructor for objects of class BoardSquares
     */
    public BoardSquare(Board.Bonus multi)
    {
        // initialise instance variables
        tile = null;
        isEmpty = true;
        this.multiplier = multi;
    }
    /**
     * Constructor for objects of class BoardSquares
     */
    public BoardSquare(Tile t){
        tile = t;
        isEmpty = false;
        this.multiplier = null;
    }

    /**
     * Returns the current tile on the board square
     * @return The tile on the board
     */
    public Tile getTile()
    {
        return tile;
    }
    
    /**
     * Returns the state of the board square if it is empty or has a tile on it

     * @return    the sum of x and y
     */
    public boolean isEmpty()
    {
        return isEmpty;
    }
    
    /**
     * Places the tile played on the board square.
     *
     * @param  tilePlayed this is the tile played by the player
     */
    public void setTile(Tile tilePlayed)
    {
        if(isEmpty()){
            tile = tilePlayed;
            isEmpty = false;
        }
    }
    
    /**
     * Returns the multiplier assigned to that board square
     *
     * @return    the sum of x and y
     */
    public Board.Bonus getMultiplier()
    {
        // put your code here
        return multiplier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardSquare that = (BoardSquare) o;
        return isEmpty == that.isEmpty && Objects.equals(tile, that.tile) && multiplier == that.multiplier;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tile, isEmpty, multiplier);
    }

    public void empty(){
        this.tile  = null;
        isEmpty = true;
    }
}
