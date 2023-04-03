package e1;

import e1.Logics;
import e1.LogicsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestE1 {
    private Logics logics;
    private static final int SIZE = 5 ;


    @BeforeEach
    public void startGame(){
        this.logics = new LogicsImpl(SIZE);
    }

    @Test
    public void testKnightNotNullPosition(){
        boolean positionExist = false;
        for (int col = 0; col< SIZE; col++){
            for (int row = 0; row< SIZE; row++){
                if (logics.hasKnight(row,col)){
                    positionExist = true;
                }
            }
        }
        assertTrue(positionExist);
    }

    @Test
    public void testPawnNotNullPosition(){
        boolean positionExist = false;
        for (int col = 0; col< SIZE; col++){
            for (int row = 0; row< SIZE; row++){
                if (logics.hasPawn(row,col)){
                    positionExist = true;
                }
            }
        }
        assertTrue(positionExist);
    }


    @Test
    public void testMoveKnightOutOfSize(){
        assertThrows(IndexOutOfBoundsException.class, ()-> logics.hit(SIZE+2,SIZE+2));
    }

    @Test
    public void testWrongMove(){
        logics = new LogicsImpl(SIZE,0,0);
        assertFalse(logics.hit(1,1));
    }

}
