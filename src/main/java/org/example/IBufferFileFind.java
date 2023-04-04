package org.example;

public interface IBufferFileFind<Item> {
    void put(Item item) throws InterruptedException;

    Item get() throws InterruptedException;

}