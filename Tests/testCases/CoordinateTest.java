package testCases;

import models.Coordinate;
import org.junit.*;
import static org.junit.Assert.*;

public class CoordinateTest {
    private Coordinate model1;
    private Coordinate model;
    @Before
    public void setup(){
        model1 = new Coordinate(3,2);
        model = new Coordinate("2-a");
    }
    @After
    public void tearDown(){;
    }

    /**
     *this method tests the getRow function in Coordinate class
     */
    @Test
    public void getRowTest(){
        assertEquals(3, model1.getRow());
    }

    /**
     *this method tests the getColumn function in Coordinate class
     */
    @Test
    public void getColumnTest(){
        assertEquals(2, model1.getColumn());
    }

    /**
     *this method tests isHorizontal function in Coordinate class
     */
    @Test
    public void isHorizontalTest(){
        assertTrue(model.isHorizontal());
    }

}
