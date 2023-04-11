package org.example.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferSynchronizedImpl<Item> implements BufferSynchronized<Item> {

    private final List<Item> list = new LinkedList<>();
    private final Lock mutex = new ReentrantLock();
    private final Condition notEmpty = mutex.newCondition();

    @Override
    public void put(Item item) {
        try{
            mutex.lock();
            this.list.add(item);
            notEmpty.signal();
        }finally {
            mutex.unlock();
        }
    }

    @Override
    public Item get() throws InterruptedException{
        try{
            mutex.lock();
            if(this.list.isEmpty()){
                notEmpty.await();
            }
            return this.list.remove(0);
        } finally {
            mutex.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        try{
            mutex.lock();
            return this.list.isEmpty();
        }finally {
            mutex.unlock();
        }
    }

    @Override
    public int size(){
        try{
            mutex.lock();
            return this.list.size();
        }finally {
            mutex.unlock();
        }
    }
}