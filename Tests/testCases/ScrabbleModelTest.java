package testCases;

import models.ScrabbleModel;
import models.Tile;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;

public class ScrabbleModelTest {

    private ScrabbleModel model;

    @Before
    public void setup() throws FileNotFoundException {
        this.model = new ScrabbleModel();
    }

    @Test
    public void testScrabbleModelSerializeAndDeserialize(){
        try {
            String filename = "test";
            model.getBoard().placeLetters(7,7, new Tile('H'));
            model.getBoard().placeLetters(8,7, new Tile('E'));
            model.getBoard().placeLetters(9,7, new Tile('L'));
            model.getBoard().placeLetters(10,7, new Tile('L'));
            model.getBoard().placeLetters(11,7, new Tile('O'));


            model.serialize(filename);

            File file = new File(ScrabbleModel.SAVE_DIRECTORY + filename + ScrabbleModel.SAVE_EXTENSION);
            ScrabbleModel importedModel = ScrabbleModel.deserialize(file);

            assertNotNull(importedModel);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
