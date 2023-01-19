package models;

import java.io.Serializable;

/**
 * Class CommandWords models all the command words usable in the game.
 *
 * @author Boma Iyaye  101197410
 * @version 1.0 16/10/2022
 */
public class CommandWords implements Serializable {
    private static final String[] validCommands = {
            "play", "pass", "exchange", "help", "quit"
    };

    public CommandWords() {

    }

    private static final long serialVersionUID = 4L;


    /**
     * Checks if the input word is one of the command words
     * @param word a command word
     * @return true if the word is the command word else false
     */
    public boolean isCommand(String word) {
        for (String validCommand : validCommands) {
            if (validCommand.equals(word))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }

    /**
     * Print all valid commands to System.out.
     */
    public void showAll()
    {
        for(String command: validCommands) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }

    public String[] getValidCommands(){
        return validCommands;
    }
}
