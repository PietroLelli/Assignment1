package org.example.controller;

import org.example.model.ModelObserver;
import org.example.utils.BufferSynchronized;
import org.example.utils.Pair;
import org.example.utils.ResultsImpl;

import java.io.File;
import java.util.List;

public interface Controller {
    void start(int numberOfWorkers, int topN);
    ResultsImpl getRankingList();
    void processEvent(Runnable runnable);
    void notifyObservers(ModelObserver.Event event) throws InterruptedException;
    ResultsImpl getResult();
    void addResult(Pair<File, Integer> result);

    void stop();

    void endComputation();
}
