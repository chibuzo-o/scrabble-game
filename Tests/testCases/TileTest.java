package testCases;

import models.Tile;
import org.junit.*;
import static org.junit.Assert.*;

public class TileTest {

    Tile validTile;
    Tile invalidTile;
    Tile stringTile;
    Tile blankTile;
    int pointValue;

    @Before
    public void setUp() throws Exception{
        validTile = new Tile('a');
        stringTile = new Tile("ay");
        blankTile = new Tile();
    }

    @Test
    public void testTile(){
        assertTrue(validTile.getTileValue()=='A');
        assertTrue(validTile.getPointValue()==1);
        assertFalse(validTile.isBlank());
    }

    @Test
    public void teststringTile(){
        assertTrue(stringTile.getTileValue()=='A');
        assertTrue(stringTile.getPointValue()==1);
        assertFalse(stringTile.isBlank());
    }

    @Test
    public void testblankTile(){
        assertEquals(0, blankTile.getPointValue());
        assertEquals('\0', blankTile.getTileValue());
        assertTrue(blankTile.isBlank());
    }

    @Test
    public void testinvalidTile(){
        assertThrows(IllegalStateException.class, () -> invalidTile = new Tile('5'));
    }

    @Test
    public void testgetPointValue(){
        assertEquals(1, validTile.getPointValue());
        assertEquals(0, blankTile.getPointValue());
    }

    @Test
    public void testgetTileValue(){
        assertTrue('A'== validTile.getTileValue());
        assertTrue('\0' == blankTile.getTileValue());
    }

    @Test
    public void testisBlank(){
        assertFalse(validTile.isBlank());
    }


    @Test
    public void testequals(){
        assertEquals(validTile, validTile);
        assertNotEquals(null, validTile);
        assertNotEquals(5, validTile);
        assertEquals(validTile, stringTile);
    }

    @Test
    public void testhashCode(){
        assertEquals(validTile.hashCode(), stringTile.hashCode());
        assertEquals(validTile.hashCode(), validTile.hashCode());
        assertNotEquals(validTile.hashCode(), blankTile.hashCode());
    }

}
