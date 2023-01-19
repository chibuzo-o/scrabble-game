package testCases;

import models.Bag;
import models.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BagTest {

    private Bag bag;

    @Before
    public void setUp() throws Exception {
       bag= new Bag();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testgetRandomTile() {
        bag.getRandomTile();
        assertEquals(97,bag.getNumberRemaining());
    }

    @Test
    public void testadd() {
        bag.add(new Tile("O"));
        assertEquals(99, bag.getNumberRemaining());
    }

    @Test
    public void testcreateRack() {
        ArrayList<Tile> testRack = bag.createRack(7);
        assertEquals(7,testRack.size());
    }

    @Test
    public void testisEmpty() {
        assertEquals(false,bag.isEmpty());
    }

    @Test
    public void testgetNumberRemaining() {
        assertEquals(98,bag.getNumberRemaining());
    }

    @Test
    public void reset() {
        bag.getRandomTile();
        bag.getRandomTile();
        bag.getRandomTile();

        bag.reset();
        assertEquals(98 ,bag.getNumberRemaining());

    }
}