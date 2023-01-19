package testCases;

import models.Board;
import models.Dictionary;
import models.MoveValidator;
import models.PlayerMove;
import models.Coordinate;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

public class MoveValidatorTest {
    private MoveValidator validator;
    private Board b;

    @Before
    public void setUp() throws FileNotFoundException {
        Dictionary dict = new Dictionary();
        validator = new MoveValidator(dict);
        b = new Board(validator);
    }

    @Test
    public void testWordNotInDictionary(){
        PlayerMove move = new PlayerMove(true, "qwik", new Coordinate(8,8), b);
        assertFalse(validator.validate(move, b));
    }

    @Test
    public void testFirstMoveCoversCenter(){
        PlayerMove move = new PlayerMove(true, "ten", new Coordinate(7,7), b);
        assertTrue(validator.validate(move, b));
    }

    @Test
    public void testFirstMoveDoesNotCoversCenter(){
        PlayerMove move = new PlayerMove(true, "ten", new Coordinate(9,9), b);
        assertFalse(validator.validate(move, b));
    }

    @Test
    public void testMoveConnectsWithWordOnBoard(){
        PlayerMove firstMove = new PlayerMove(true, "ten", new Coordinate(7,7), b);
        b.placeMove(firstMove);

        PlayerMove move = new PlayerMove(false, "ake", new Coordinate(8,7), b);
        assertTrue(validator.validate(move, b));
    }

    @Test
    public void testMoveDoesNotConnectsWithWordOnBoard(){
        PlayerMove firstMove = new PlayerMove(true, "ten", new Coordinate(7,7), b);
        b.placeMove(firstMove);

        PlayerMove move = new PlayerMove(false, "ake", new Coordinate(2,2), b);
       assertFalse(validator.validate(move, b));
    }

    @Test
    public void testIfConnectionFormsAValidWordOnBoard(){
        PlayerMove firstMove = new PlayerMove(true, "ten", new Coordinate(7,7), b);
        b.placeMove(firstMove);

        PlayerMove move = new PlayerMove(false, "ake", new Coordinate(8,7), b);
        assertTrue(validator.validate(move, b));
    }

    @Test
    public void testIfConnectionFormsAnInvalidWordOnBoard(){
        PlayerMove firstMove = new PlayerMove(true, "ten", new Coordinate(7,7), b);
        b.placeMove(firstMove);

        PlayerMove move = new PlayerMove(false, "w", new Coordinate(8,7), b);
        assertFalse(validator.validate(move, b));
    }
}
