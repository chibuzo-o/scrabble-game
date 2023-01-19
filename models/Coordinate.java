package models;

import java.io.Serializable;

/**
 * Coordinate models the coordinate system of the scrabble board.
 * It keeps track of the rows and  columns and translate the positional
 * input of a move into the appropriate coordinate on the board
 *
 * @author Geoffery Koranteng
 * @version v1.0
 *
 */
public class Coordinate implements Serializable {
    private static final long serialVersionUID = 4L;

    private int row;  /** Row on the board */
    private int column;  /** Column on the board */

    /**
     * boolean tracking if the translated coordinate is horizontal or not
     */
    private boolean isHorizontal;

    /**
     * Constructor Instantiates an new coordinate class
     *
     * @param row The row on the board.
     * @param column The column on the board.
     */
    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Constructor Instantiates an new coordinate class
     *
     * @param s A string in the format 8-h where the integer is the row
     *          and the character is the column. A coordinate 8-h represents
     *          a horizontal move, whereas the coordinate with a letter first such as
     *          h-8 represents a vertical move with the row and column
     *          still being an integer and a character respectively
     */
    public Coordinate(String s){
        this.row = translate(s)[0];
        this.column = translate(s)[1];
    }

    /**
     * The method translates a string coordinate into
     * the right row and column on the board
     *
     * @param s A string coordinate
     */
    private int[] translate(String s){
        int[] result = new int[2];
        String[] sArr  = s.split("-");

        if(isNumeric(sArr[0])){
            result[0] = Integer.parseInt(sArr[0]);  //Arraylist  is zero indexed
            result[1] = charToInt(sArr[1].charAt(0)); //Arraylist  is zero indexed
            isHorizontal = true;
            return result;
        }


        if(isNumeric(sArr[1])) {
            result[0] = Integer.parseInt(sArr[1]); //Arraylist  is zero indexed
            result[1] = charToInt(sArr[0].charAt(0)); //Arraylist  is zero indexed
            isHorizontal = false;
            return result;
        }

        return result;
    }

    /**
     * This method returns if a string is an integer or not
     *
     * @param string A string input
     * */
    private static boolean isNumeric(String string){
        if(string == null || string.equals("")) {
            System.out.println("String cannot be parsed, it is null or empty.");
            return false;
        }

        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException ignored) {}
        return false;
    }

    /**
     * This method converts a character to its matching integer on the board
     *
     * @param c The character to be converted
     * */
    private int charToInt(char c){
        return switch (Character.toUpperCase(c)){
           case 'A' -> 0;
           case 'B' -> 1;
           case 'C' -> 2;
           case 'D' -> 3;
           case 'E' -> 4;
           case 'F' -> 5;
           case 'G' -> 6;
           case 'H' -> 7;
           case 'I' -> 8;
           case 'J' -> 9;
           case 'K' -> 10;
           case 'L' -> 11;
           case 'M' -> 12;
           case 'N' -> 13;
           case 'O' -> 14;
           default -> throw new IllegalStateException("Unexpected value: " + c);
        };
    }

    /**
     * Returns the row component of the coordinate.
     *
     * @return The row of a coordinate.
     * */
    public int getRow() {
        return row;
    }


    /**
     * Returns the column component of the coordinate
     *
     * @return The column of the coordinate
     * */
    public int getColumn() {
        return column;
    }

    /**
     * Returns if a coordinate is equal to another coordinate object.
     *
     * @param c A coordinate object
     * */
    @Override
    public boolean equals(Object c){
        if (!(c instanceof Coordinate)) return false;
        return (row == ((Coordinate) c).getRow() && column == ((Coordinate) c).getColumn());
    }


    /**
     * Returns if the coordinate is horizontal of vertical
     */
    public boolean isHorizontal() {
        return isHorizontal;
    }

    public void setOrientation(Coordinate that) {
        this.isHorizontal = this.row == that.row;
    }

    public void decrement() {
        this.row -= 1;
        this.column -= 1;
    }
}
