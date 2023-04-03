package org.example;

public interface IBoundedBufferFileFind<Item> {
    void put(Item item) throws InterruptedException;

    Item get() throws InterruptedException;

}
