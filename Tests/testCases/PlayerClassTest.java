package testCases;

import models.Bag;
import models.Dictionary;
import models.Player;
import org.junit.*;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class PlayerClassTest {
    private Player m;
    private String Chibuzo;
    private Bag bag;
    private Dictionary dict;

    @Before
    public void setup() throws FileNotFoundException {
        bag = new Bag();
        dict = new Dictionary();
        m = new Player(1, Chibuzo, bag);
    }

    @After
    public void tearDown() {

    }

    /**
     *this method tests the getName function in GamesEntity class
     */
    @Test
    public void getNameTest() {
        assertEquals(Chibuzo, m.getName());
    }

    /**
     *this method tests the getPlayerID function in GamesEntity class
     */
    @Test
    public void getPlayerIDTest() {
        assertEquals(1, m.getPlayerID());
    }

    /**
     *this method tests the getScore function in GamesEntity class
     */
    @Test
    public void getScoreTest() {
        assertEquals(0, m.getScore());
    }

    /**
     *this method tests the exchangeTiles function in GamesEntity class
     */
    @Test
    public void exchangeTilesTest() {

        assertTrue(m.exchangeTiles(m.getRack().get(0)));
    }

    /**
     *this method tests the instantiateRack function in GamesEntity class
     */
    @Test
    public void instantiateRackTest() {
        assertEquals(7, m.getRack().size());
    }

    /**
     *
     * @return ArrayList of words
     */
    private ArrayList<String> word() {
        ArrayList<String> words = new ArrayList<>();
        words.add("aa");
        return (words);
    }

    /**
     *method tests updateScore in GamesEntity
     */
    @Test
    public void updateScoreTest() {
        ArrayList<String> words = new ArrayList<>();
        words.add("aa");
        m.updateScore(words, dict);
        assertEquals(2, m.getScore());
    }
}





