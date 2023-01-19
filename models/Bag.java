package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


/**
 * Class Bag models a bag of tiles in the game of scrabble
 * It contains 100 tiles which are used to play the game.
 * All players use the same bag
 *
 * @author Geoffery Koranteng
 * @version v1.0
 */
public class Bag implements Serializable {

    private static final long serialVersionUID = 4L;
    private final ArrayList<Tile> bag;    /* ArrayList to represent the bag */
    private final ArrayList<Tile> reserves; /* ArrayList to represent a copy of the bag, used to reset the bag */
    private final Random  random;

    public static final int RACK_SIZE = 7;

    /**
     * Instantiates the Bag class and initializes the bag lists
     */
    public Bag() throws FileNotFoundException {
        bag = generateBag();
        reserves = generateBag();
        random = new Random();
    }

    /**
     * This method populates the bag list buy reading the bag config file
     *
     * @return An arraylist of tiles.
     */
    private ArrayList<Tile> generateBag() throws FileNotFoundException{
        ArrayList<Tile> tl = new ArrayList<>();

        try{
            File file = new File("config/bag.cfg");
            Scanner reader = new Scanner(file);

            while(reader.hasNextLine()){
              String line = reader.nextLine();
              String[] arr = line.split("-");

              for(int i = 0; i < Integer.parseInt(arr[1]); i++){
                  if(arr[0].equals("@")) tl.add(new Tile());
                  else tl.add(new Tile(arr[0].charAt(0)));
              }
            }

            reader.close();

        }  catch (FileNotFoundException e){
            System.out.println("Error: File Not Found");
            e.printStackTrace();
        }



        return tl;
    }

    /**
     * Removes a  random tile from the bag list
     *
     * @return A tile object
    */
    public Tile getRandomTile(){
        return bag.remove(random.nextInt(bag.size()));
    }


    /**
     * Adds a tile object to the bag list
     *
     * @param tl The tile to be added to the list.
     */
    public void add(Tile tl){
        bag.add(tl);
    }

    /**
     * Creates a rack of tiles to be used by the play in the game
     *
     * @param rackSize The size  of the rack being created.
     * @return An Arraylist of tile.
     */
    public ArrayList<Tile> createRack(int rackSize){
        ArrayList<Tile> rk = new ArrayList<>();
        for(int i = 0; i < rackSize; i++){
            rk.add(this.getRandomTile());
        }

        return rk;
    }


    /**
     * Returns is the bag is empty or not
     *
     * @return True if bag is empty and  false otherwise
     */
    public boolean isEmpty(){
        return bag.isEmpty();
    }

    /**
     * The method returns the number of tiles remaining in the bag
     *
     * @return the number of tiles left in the bag.
     */
    public int getNumberRemaining(){
        return bag.size();
    }


    /**
     * Resets the bag to its originals to state.g
     */
    public void reset(){
        bag.clear();
        bag.addAll(reserves);
    }

}
