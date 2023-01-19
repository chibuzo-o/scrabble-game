package models;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class models a playing tile,
 * It keeps track of the letter on the tile and its point value.
 *
 * @author Geoffery Koranteng
 * @version v1.0
 * */
public class Tile implements Serializable {
    private static final long serialVersionUID = 4L;
    private final char tileValue; //  The Letter value of the tile
    private final int pointValue; // The point value of the tile

    private final boolean isBlank;  // flag tracking is a tile is black or not

    /**
     * Instantiates the Model.Tile class
     *
     * @param tileValue the letter value of the tile as a character
     *
     * */
    public Tile(char tileValue) {
        this.tileValue = Character.toUpperCase(tileValue);
        this.pointValue = this.generatePointValue();
        this.isBlank = false;
    }

    /**
     * Instantiates the Model.Tile class
     *
     * @param tileValue the letter value of the tile as a string
     *
     * */
    public Tile(String tileValue){
        this(tileValue.charAt(0));
    }


    /**
     * Instantiates the Model.Tile class as a blank tile
     *
     * */
    public Tile(){
        this.pointValue = 0;
        this.tileValue = '\0';
        this.isBlank = true;
    }


    /**
     * Returns the letter value of the tile
     *
     * @return the letter value of the tile
     *
     * */
    public char getTileValue() {
        return tileValue;
    }


    /**
     * Returns the point value of the tile
     *
     * @return the point value of the tile
     *
     * */
    public int getPointValue() {
        return pointValue;
    }

    /**
     * Returns if the tile is blank or not
     *
     * @return true if the tile is a blank or false otherwise
     *
     * */
    public boolean isBlank() {
        return this.isBlank;
    }

    /**
     * Generates the point value of the tile based of
     * its letter value.
     *
     * @return the point value of the tile's letter value
     *
     * */
    private int generatePointValue(){
        return switch (this.tileValue){
            case 'A', 'E', 'I', 'O', 'U', 'L', 'N', 'S', 'T', 'R' -> 1;
            case 'D', 'G' -> 2;
            case 'B', 'C', 'M', 'P' -> 3;
            case 'F', 'H', 'V', 'W', 'Y' -> 4;
            case 'K' -> 5;
            case 'J', 'X' -> 8;
            case 'Q','Z' -> 10;
            case '@' -> 0;
            default -> throw new IllegalStateException("Unexpected value: " + this.tileValue);
        };
    }

    /**
     * Returns if the tile is equal to another tile object
     *
     * @return true if the two tiles are equal and false otherwise
     *
     * */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return tileValue == tile.tileValue && pointValue == tile.pointValue && isBlank == tile.isBlank;
    }

    /**
     * Returns the hashcode for the tile object
     *
     * @return the hashcode of the object.
     * */
    @Override
    public int hashCode() {
        return Objects.hash(tileValue, pointValue, isBlank);
    }
}
