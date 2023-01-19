package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
/**
 * This Class models a dictionary used in the scrabble game.
 * It keeps track of all words valid words and
 * each score associated with each word, withs its internal data structure.
 *
 * @author Geoffery Koranteng
 * @version v1.0
 *
 * */
public class Dictionary implements Serializable {

    private static final long serialVersionUID = 4L;
    private final Map<String, Integer>  wordsMap; // Map of a word with its score

    /**
     * Initializes the dictionary and populates the map
     *
     * */
    public Dictionary() throws FileNotFoundException {

        this.wordsMap = new HashMap<>();
        this.createWordMap();
    }

    /**
     * Reads in the dictionary file and populates the HashMap
     * */
    private void createWordMap() throws FileNotFoundException {
        try{
            File file = new File("config/words.txt");
            Scanner reader = new Scanner(file);

            while(reader.hasNextLine()){
                String line = reader.nextLine();
                wordsMap.put(line, this.getGenerateWordScore(line));
            }

            reader.close();
        }
        catch (FileNotFoundException e){
            System.out.println("Error: File Not Found");
            e.printStackTrace();
        }
    }

    /**
     * Generates the score of a word passed to it
     *
     * @param line word, whose score is being generated
     * @return the score of the word
     * */
    private Integer getGenerateWordScore(String line) {
        int sum = 0;

        for(int i =0; i < line.length(); i++){
            Tile tl = new Tile(line.charAt(i));
            sum += tl.getPointValue();
        }

        return sum;
    }


    /**
     * Returns true if the dictionary contains a word
     *
     * @return true if a dictionary contains a word
     *
     * */
    public boolean contains(String word){
        return wordsMap.containsKey(word.toLowerCase());
    }


    /**
     * Returns the score of a word in the Hashmap
     *
     * @return the score of the word passed.
     *
     * */
    public int getScore(String word){
        return wordsMap.get(word.toLowerCase());
    }

}
