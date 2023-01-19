package testCases;

import models.Dictionary;
import org.junit.*;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;

public class DictionaryTest {
    private Dictionary model;

    @Before
    public void setup() throws FileNotFoundException {
        model = new Dictionary();
    }
    @After
    public void tearDown(){

    }

    /**
     *this method tests the contains function in Dictionary class
     */
    @Test
    public void containsTest(){
        String word = "abandoned";
        assertTrue(model.contains(word));
    }

    /**
     *this method tests the getScore function in Dictionary class
     */
    @Test
    public void getScoreTest(){
        String word = "aa";
        assertEquals(2, model.getScore(word));
    }
}
