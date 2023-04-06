package org.example.controller;

import org.example.model.MasterThread;
import org.example.model.Model;
import org.example.model.ModelObserver;
import org.example.utils.BufferSynchronized;
import org.example.utils.Pair;
import org.example.utils.ResultsImpl;
import org.example.view.View;

import java.io.File;
import java.util.List;

public class ControllerImpl implements Controller{
    private final Model model;
    private final View view;
    private MasterThread masterThread;


    public ControllerImpl(Model model, View view){
        this.model = model;
        this.view = view;
        this.view.setController(this);
    }

    @Override
    public void start(int numberOfWorkers, int topN) {
        this.model.setup(topN);
        masterThread = new MasterThread(numberOfWorkers, this);
        masterThread.start();
    }

    @Override
    public ResultsImpl getRankingList() {
        return this.model.getResult();
    }
    @Override
    public void processEvent(Runnable runnable){
        new Thread(runnable).start();
    }

    @Override
    public void notifyObservers(ModelObserver.Event event) throws InterruptedException {
        this.model.notifyObservers(event);
    }

    @Override
    public ResultsImpl getResult() {
        return this.model.getResult();
    }

    public void addResult(Pair<File, Integer> result) {
        this.model.getResult().add(result);
    }

    @Override
    public void stop() {
        this.masterThread.interrupt();
    }

    @Override
    public void endComputation() {
        this.view.endComputation();
    }


}
