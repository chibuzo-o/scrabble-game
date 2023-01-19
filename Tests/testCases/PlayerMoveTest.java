package testCases;

import models.*;
import org.junit.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerMoveTest {

    PlayerMove verticalMove;
    PlayerMove horizontalMove;
    Coordinate c1;
    Coordinate c2;
    Dictionary d;
    MoveValidator mv;
    Board board;
    String word;

    @Before
    public void setUp() throws FileNotFoundException {

        d = new Dictionary();
        mv = new MoveValidator(d);
        board = new Board(mv);
        c1 = new Coordinate(1,1);
        c2 = new Coordinate(5,5);
        horizontalMove = new PlayerMove(true,"word",c1,board);
        verticalMove = new PlayerMove(false,"test",c2,board);

    }

    @Test
    public void testPlayerMove(){
        assertTrue(horizontalMove.isHorizontal());
        Assert.assertEquals("word",horizontalMove.getWord());
        Assert.assertEquals(c1,horizontalMove.getStartCoordinate());
        assertFalse(horizontalMove.hasNeighbor());
        Assert.assertEquals(4,horizontalMove.getNewTiles().size());
        //assertTrue(board.get(c).isEmpty());
    }

    @Test
    public void testisHorizontal(){
        assertTrue(horizontalMove.isHorizontal());
        assertFalse(verticalMove.isHorizontal());
    }

    @Test
    public void testgetWord(){
        Assert.assertEquals("word", horizontalMove.getWord());
    }

    @Test
    public void testgetNewTiles(){
        ArrayList<Tile> newTiles = new ArrayList<Tile>();
        newTiles.add(new Tile('w'));
        newTiles.add(new Tile('o'));
        newTiles.add(new Tile('r'));
        newTiles.add(new Tile('d'));

        Assert.assertEquals(newTiles,horizontalMove.getNewTiles());
        Assert.assertEquals(4,horizontalMove.getNewTiles().size());
    }

    @Test
    public void testgetStartCoordinate(){
        Assert.assertEquals(c1, horizontalMove.getStartCoordinate());
        Assert.assertEquals(c2, verticalMove.getStartCoordinate());
    }

    @Test
    public void testgetEndCoordinate(){
        Assert.assertEquals((new Coordinate(1,4)), horizontalMove.getEndCoordinate());
        Assert.assertEquals((new Coordinate(8,5)), verticalMove.getEndCoordinate());
    }

    @Test
    public void testhasNeighbor(){
        assertFalse(horizontalMove.hasNeighbor());

        PlayerMove neighbor = new PlayerMove(true,"tank", new Coordinate(5,5),board);
        //assertEquals(3,neighbor.getNewTiles().size());
        //assertTrue(neighbor.hasNeighbor());
        //assertTrue(verticalMove.hasNeighbor());
    }

    @Test
    public void testgetSize(){
        Assert.assertEquals(4, horizontalMove.getSize());
    }

    @Test
    public void testgetLetterAt(){
        Assert.assertEquals('w', horizontalMove.getLetterAt(0) );
    }

    @Test
    public void testinvalidgetLetterAt(){
        assertThrows(StringIndexOutOfBoundsException.class, () -> horizontalMove.getLetterAt(4));
    }









}
