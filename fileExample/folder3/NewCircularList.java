package lab01.tdd.newImplement;

import lab01.tdd.CircularList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NewCircularList implements CircularList {
    private Map<Integer, Integer> values = new HashMap<>();
    private int newPositionFree = 0;
    private int currentPosition = 0;
    @Override
    public void add(int element) {
        values.put(newPositionFree, element);
        newPositionFree++;
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    public Iterator forwardIterator(){

        return new Iterator() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Object next() {
                if(currentPosition == values.size()){
                    currentPosition = 0;
                }
                int tmp = currentPosition;
                currentPosition++;
                return values.get(tmp);
            }
        };

    }
    public Iterator backwardIterator(){
        return new Iterator() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Object next() {
                if(currentPosition == -1){
                    currentPosition = values.size()-1;
                }
                int tmp = currentPosition;
                currentPosition--;
                return values.get(tmp);
            }
        };
    }
    public void reset() {
        values = new HashMap<>();
        newPositionFree = 0;
        currentPosition = 0;
    }

    public Object filteredNext(){
        return null;
    }
}
