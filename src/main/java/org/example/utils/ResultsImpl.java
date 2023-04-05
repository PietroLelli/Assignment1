package org.example.utils;

import java.io.File;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

public class ResultsImpl {
    private final int nResults;
    final Comparator<Pair<File, Integer>> comparator = reverseOrder(comparing(Pair::getY));

    private final Set<Pair<File, Integer>> ranking = new TreeSet<>(comparator);
    private final Lock mutex = new ReentrantLock();
    //private final Map<Interval, Integer> distribution = new TreeMap<>();

    public ResultsImpl(int nResults){
        this.nResults = nResults;
    }

    public List<Pair<File, Integer>> getRanking() {
        try{
            this.mutex.lock();
            return this.ranking.stream().limit(this.nResults).collect(Collectors.toList());
        }finally {
            this.mutex.unlock();
        }
    }

    public void add(Pair<File, Integer> elem) {
        try{
            this.mutex.lock();
            this.ranking.add(elem);
        }finally {
            this.mutex.unlock();
        }
    }
}
