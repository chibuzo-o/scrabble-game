package testCases;

import models.Command;
import org.junit.*;
import static org.junit.Assert.*;

public class CommandTest {

    Command command;
    Command unknownCommand;

    @Before
    public void setUp(){
        command = new Command("play","with","me");
        unknownCommand = new Command(null, null, null);

    }

    @Test
    public void testCommand(){
        assertEquals("play", command.getCommandWord());
        assertEquals("with", command.getSecondWord());
        assertEquals("me", command.getThirdWord());
    }

    @Test
    public void testisUnknown(){
        assertFalse(command.isUnknown());
        assertTrue(unknownCommand.isUnknown());
    }

    @Test
    public void testhasSecondWord(){
        assertTrue(command.hasSecondWord());
        assertFalse(unknownCommand.hasSecondWord());
    }

    @Test
    public void testhasThirdWord(){
        assertTrue(command.hasThirdWord());
        assertFalse(unknownCommand.hasThirdWord());
    }


}
