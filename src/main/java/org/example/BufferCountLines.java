package org.example;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

public class BufferCountLines<Item> implements IBufferCountLines<Item> {
	private TreeSet<Item> bufferTopN;
	private LinkedList<Item> bufferRange;
	private Lock mutex;
	private Comparator comparator;
	//private Condition isFull;
	private Condition notEmpty, isFull;
	private int sizeMax;


	public BufferCountLines(Comparator comparator, int size) {
		bufferTopN = new TreeSet<>(comparator);
		mutex = new ReentrantLock();
		this.comparator = comparator;
		notEmpty = mutex.newCondition();
		isFull = mutex.newCondition();
		bufferRange = new LinkedList<>();
		this.sizeMax = size;

	}

	public void put(Item item) throws InterruptedException {
		try {
			mutex.lock();

			bufferTopN.add(item);
			bufferRange.add(item);

		} finally {
			mutex.unlock();
		}
	}


	public List<Item> getTopN(int limit) throws InterruptedException {
		try {
			mutex.lock();

			return bufferTopN.stream().limit(limit).collect(Collectors.toList());

		} finally {
			mutex.unlock();
		}
	}

	public Item getItem() {
		try {
			mutex.lock();
			if (isEmpty()) {
				notEmpty.await();
			}
			Item item = bufferRange.removeFirst();

			return item;

		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			mutex.unlock();
		}
	}
	private boolean isEmpty() {
		return bufferRange.size() == 0;
	}


}