package org.example;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferFileFind<Item> implements IBufferFileFind<Item> {

    private LinkedList<Item> buffer;
    private Lock mutex;
    private Condition notEmpty;
    public BufferFileFind() {
        buffer = new LinkedList<Item>();
        mutex = new ReentrantLock();
        notEmpty = mutex.newCondition();

    }

    public void put(Item item) throws InterruptedException {
        try {
            mutex.lock();
            buffer.addLast(item);
            notEmpty.signal();
        } finally {
            mutex.unlock();
        }
    }

    public Item get() throws InterruptedException {
        try {
            mutex.lock();
            if(isEmpty()){
                notEmpty.await();
            }

            Item item = buffer.removeFirst();

            return item;
        } finally {
            mutex.unlock();
        }
    }
    private boolean isEmpty(){
        return buffer.size() == 0;
    }
}