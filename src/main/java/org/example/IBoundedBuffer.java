package org.example;

import java.util.Comparator;
import java.util.List;

public interface IBoundedBuffer<Item> {

    void put(Item item) throws InterruptedException;
    
    List<Item> get(int limit) throws InterruptedException;

}
