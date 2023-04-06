package org.example.controller;

import org.example.utils.Pair;
import org.example.utils.Results;
import org.example.utils.ResultsImpl;

import java.io.File;

public interface Controller {
    void start(int numberOfWorkers, String path, int topN, int maxL, int numIntervals);
    Results getRankingList();
    void processEvent(Runnable runnable);
    Results getResult();
    void addResult(Pair<File, Integer> result);

    void stop();

    void updateResult() throws InterruptedException;
    void endComputation();
}
