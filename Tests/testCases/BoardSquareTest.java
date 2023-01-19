package testCases;

import models.Board;
import models.BoardSquare;
import models.Tile;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardSquareTest {

    private BoardSquare emptytestSquare;
    private BoardSquare nonEmptytestSquare;

    @Before
    public void setUp() throws Exception {
        emptytestSquare = new BoardSquare(Board.Bonus.NM);
        nonEmptytestSquare = new BoardSquare(new Tile('O'));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testgetTileforEmptySquare() {
        assertNull(emptytestSquare.getTile());
    }
    @Test
    public void testgetTileforNonEmptySquare() {
        Assert.assertEquals(new Tile('O'), nonEmptytestSquare.getTile());
    }

    @Test
    public void testisEmptyforEmptySquare() {
        assertTrue(emptytestSquare.isEmpty());
    }
    @Test
    public void testisEmptyforNonEmptySquare() {
        assertFalse(nonEmptytestSquare.isEmpty());
    }

    @Test
    public void setTileforEmptySquare() {
        assertTrue(emptytestSquare.isEmpty());
    }

    @Test
    public void setTileforNonEmptySquare() {
        Tile newTile = new Tile("B");
        nonEmptytestSquare.setTile(newTile);
        Assert.assertNotEquals(nonEmptytestSquare.getTile(), newTile);
    }


}