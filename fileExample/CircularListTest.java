import lab01.tdd.CircularList;
import lab01.tdd.newImplement.NewCircularList;
import org.junit.jupiter.api.*;

/**
 * The test suite for testing the CircularList implementation
 */
public class CircularListTest {

/*
    @Disabled
    @Test public void testTodo(){
        Assertions.fail();
    }*/
    private NewCircularList circularList;
    @BeforeEach
    void beforeEach(){
        circularList = new NewCircularList();
        circularList.add(4);
        circularList.add(6);
    }

    @Test
    public void testNewEmptyCircularList(){
        NewCircularList circularList = new NewCircularList();
        Assertions.assertEquals(0, circularList.size());
    }
    @Test
    public void testAddElementInCircularList(){
        Assertions.assertEquals(2, circularList.size());


    }
    @Test
    public void testNextElementInCircularList(){
        Assertions.assertEquals(4, circularList.forwardIterator().next());
        Assertions.assertEquals(6, circularList.forwardIterator().next());
        Assertions.assertEquals(4, circularList.forwardIterator().next());
    }

    @Test
    public void testPrevElementInCircularList(){
        circularList.add(8);
        Assertions.assertEquals(4, circularList.backwardIterator().next());
        Assertions.assertEquals(8, circularList.backwardIterator().next());
        Assertions.assertEquals(6, circularList.backwardIterator().next());
    }

}
