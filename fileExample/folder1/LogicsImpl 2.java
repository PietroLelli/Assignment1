package e2;

import java.util.HashMap;
import java.util.Random;

public class LogicsImpl implements Logics {
    private HashMap<Pair<Integer, Integer>, Boolean> minesweeper;
    public LogicsImpl(int size) {
        for (int row = 0; row < size; row++){
            for (int col = 0; col < size; col++) {
                minesweeper.put(new Pair<>(row, col), false);
            }
        }
        setMines(size);
    }
    private void setMines(int size){
        int numberMines = 0;
        Random rand = new Random();
        while(numberMines != size){
            int x = rand.nextInt(size);
            int y = rand.nextInt(size);
            if (minesweeper.get(new Pair<>(x,y)) == false){
                minesweeper.replace(new Pair<>(x,y), true);
                numberMines++;
            }
        }

    }

}
