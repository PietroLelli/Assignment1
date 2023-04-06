package org.example.controller;

import org.example.utils.Pair;
import org.example.utils.ComputedFile;

import java.io.File;

public interface Controller {
    void start(int numberOfWorkers, String path, int topN, int maxL, int numIntervals);
    void processEvent(Runnable runnable);
    ComputedFile getResult();
    void addResult(Pair<File, Integer> result);

    void stop();

    void updateResult() throws InterruptedException;
    void endComputation();
}
