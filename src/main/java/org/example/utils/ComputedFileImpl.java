package org.example.utils;

import java.io.File;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

public class ComputedFileImpl implements ComputedFile {
    private final int nResults;
    final Comparator<Pair<File, Integer>> comparator = reverseOrder(comparing(Pair::getY));
    private final Set<Pair<File, Integer>> ranking = new TreeSet<>(comparator);
    private final Lock mutex = new ReentrantLock();
    private final Map<Pair<Integer, Integer>, Integer> filesInRange = new HashMap<>();

    public ComputedFileImpl(int nResults, int maxL, int numIntervals ) {
        this.nResults = nResults;
        int range = maxL / (numIntervals - 1);
        int indexRange = 0;
        for (int i = 0; i < numIntervals - 1; i++) {
            filesInRange.put(new Pair<>(indexRange, indexRange + range - 1), 0);
            indexRange += range;
        }
        filesInRange.put(new Pair<>(indexRange, Integer.MAX_VALUE), 0);
    }

    public List<Pair<File, Integer>> getRanking() {
        try{
            this.mutex.lock();
            return this.ranking.stream().limit(this.nResults).collect(Collectors.toList());
        }finally {
            this.mutex.unlock();
        }
    }

    @Override
    public Map<Pair<Integer, Integer>, Integer> getFilesInRange() {
        try{
            this.mutex.lock();
            return this.filesInRange;
        }finally {
            this.mutex.unlock();
        }
    }

    public void add(Pair<File, Integer> elem) {
        try{
            this.mutex.lock();
            this.ranking.add(elem);
            for (Pair<Integer, Integer> p : filesInRange.keySet()) {
                if(elem.getY()>p.getX() && elem.getY()<p.getY()) {
                    int c = filesInRange.get(p);
                    filesInRange.replace(p, c+1);
                }
            }
        }finally {
            this.mutex.unlock();
        }
    }
}
