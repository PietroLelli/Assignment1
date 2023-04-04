package org.example;

import java.util.List;

public interface IBufferCountLines<Item> {

    void put(Item item) throws InterruptedException;

    List<Item> getTopN(int limit) throws InterruptedException;
    Item getItem();
    /*boolean isListFull();
    Condition getConditionIsFull();*/

}
