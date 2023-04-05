package org.example.utils;

public interface BufferSynchronized<Item> {
    void put(Item item) throws InterruptedException;

    Item get() throws InterruptedException;
    boolean isEmpty();

    int size();
}
