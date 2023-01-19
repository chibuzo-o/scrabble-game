package testCases;

import models.CommandWords;
import org.junit.*;

import java.lang.reflect.Array;

import static org.junit.Assert.*;

public class CommandWordsTest {

    CommandWords commandWords;

    @Before
    public void setUp(){
        commandWords = new CommandWords();
    }

    @Test
    public void testCommandWords(){
        assertEquals(5,commandWords.getValidCommands().length);
        assertEquals("play", (String)Array.get(commandWords.getValidCommands(),0));
        assertEquals("pass", (String)Array.get(commandWords.getValidCommands(),1));
        assertEquals("exchange", (String)Array.get(commandWords.getValidCommands(),2));
        assertEquals("help", (String)Array.get(commandWords.getValidCommands(),3));
        assertEquals("quit", (String)Array.get(commandWords.getValidCommands(),4));
    }

    @Test
    public void testisCommand(){
        String invalidCommand = "test";
        String validCommand = "play";
        String capCommand = "PASS";
        assertFalse(commandWords.isCommand(invalidCommand));
        assertTrue(commandWords.isCommand(validCommand));
        assertFalse(commandWords.isCommand(capCommand));
    }

    @Test
    public void getValidCommands(){
        assertEquals(5,commandWords.getValidCommands().length);
    }



}
