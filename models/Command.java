package models;

import java.io.Serializable;

/**
 * Class Command accepts the input command words
 *
 * @author Boma Iyaye  101197410
 * @version 1.0 16/10/2022
 */
public class Command implements Serializable {
    private final String commandWord;

    private static final long serialVersionUID = 4L;
    private final String secondWord;
    private final String thirdWord;

    /**
     * Constructs a command for the game based of the words typed by user
     * @param word1 first word
     * @param word2 second word
     * @param word3 third word
     */
    public Command(String word1, String word2, String word3) {
        this.commandWord = word1;
        this.secondWord = word2;
        this.thirdWord = word3;
    }

    /**
     * Returns the command word input
     * @return first command word written by user
     */
    public String getCommandWord() {
        return commandWord;
    }
    /**
     * Returns the second word input
     * @return second command word written by user
     */

    public String getSecondWord() {
        return secondWord;
    }

    /**
     * Returns the command word input
     * @return first command word written by user
     */
    public String getThirdWord() {
        return thirdWord;
    }

    /**
     * Returns true if command word is unknown
     * @return true if command word is unknown else false
     */
    public boolean isUnknown(){
        return (this.commandWord == null);
    }

    /**
     * Returns true if the player plays a second word amongst the commands else false
     * @return true if the player plays a second word amongst the commands else false
     */
    public boolean hasSecondWord(){
        return (this.secondWord != null);
    }

    /**
     * Returns true if the player plays a third word amongst the commands else false
     * @return true if the player plays a third word amongst the commands else false
     */
    public boolean hasThirdWord(){
        return (this.thirdWord != null);
    }
}
