package models;

import java.io.Serializable;
import java.util.Scanner;

/**
 * class parser reads accepted commands and executes different action based on these commands
 *
 * @author Chibuzo Okpara
 * @version 1.0 25-10-22
 */
public class Parser {
    private final CommandWords commands;  // holds all valid command words
    private final Scanner reader;  // source of command input

    /**
     * constructor for class parser
     */
    public Parser() {
        this.commands = new CommandWords();
        this.reader = new Scanner(System.in);
    }

    /**
     * method gets inputted command and checks if it is an accepted command word
     * @return command() method with the input as the argument
     */
    public Command getCommand() {
        String inputLine;   // will hold the full input line
        String word1 = null;
        String word2 = null;
        String word3 = null;

        System.out.print("> ");     // print prompt

        inputLine = reader.nextLine();

        // Find up to two words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();      // get first word
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();  // get second word
                if(tokenizer.hasNextLine()){
                    word3 = tokenizer.next(); // get third word
                }
                // note: we just ignore the rest of the input line.
            }
        }

        // Now check whether this word is known. If so, create a command
        // with it. If not, create a "null" command (for unknown command).
        if(commands.isCommand(word1)) {
            return new Command(word1, word2, word3);
        }
        else {
            return new Command(null, word2, word3);
        }
    }
}
