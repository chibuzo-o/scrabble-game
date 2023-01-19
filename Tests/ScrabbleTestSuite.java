import testCases.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BagTest.class,
        BoardSquareTest.class,
        CommandTest.class,
        CommandWordsTest.class,
        CoordinateTest.class,
        DictionaryTest.class,
        PlayerClassTest.class,
        PlayerMoveTest.class,
        TileTest.class,
        MoveValidatorTest.class,
        ScrabbleModelTest.class,
})
public class ScrabbleTestSuite {}
