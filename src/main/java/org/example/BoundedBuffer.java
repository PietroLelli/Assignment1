package org.example;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

public class BoundedBuffer<Item> implements IBoundedBuffer<Item> {
	private TreeSet<Item> buffer;
	private int maxSize;
	private Lock mutex;
	private Condition notEmpty, notFull;
	private Comparator comparator;

	public BoundedBuffer(int size) {
		buffer = new TreeSet<>();
		maxSize = size;
		mutex = new ReentrantLock();
		notEmpty = mutex.newCondition();
		notFull = mutex.newCondition();
	}
	public BoundedBuffer(int size, Comparator comparator) {
		buffer = new TreeSet<>(comparator);
		maxSize = size;
		mutex = new ReentrantLock();
		notEmpty = mutex.newCondition();
		notFull = mutex.newCondition();
		this.comparator = comparator;
	}

	public void put(Item item) throws InterruptedException {
		try {
			mutex.lock();
			if (isFull()) {
				notFull.await();
			}
			buffer.add(item);
			notEmpty.signal();
		} finally {
			mutex.unlock();
		}
	}


	public List<Item> get(int limit) throws InterruptedException {
		try {
			mutex.lock();
			if (isEmpty()) {
				notEmpty.await();
			}
			List<Item> items = new LinkedList<>();
			items = buffer.stream().limit(limit).collect(Collectors.toList());
			notFull.signal();
			return items;
		} finally {
			mutex.unlock();
		}
	}
	private boolean isFull() {
		return buffer.size() == maxSize;
	}
	private boolean isEmpty() {
		return buffer.size() == 0;
	}

}
