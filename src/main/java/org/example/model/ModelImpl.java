package org.example.model;

import org.example.utils.BufferSynchronized;
import org.example.utils.BufferSynchronizedImpl;
import org.example.utils.Pair;
import org.example.utils.ResultsImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

public class ModelImpl implements Model{
    private final List<ModelObserver> observers = new LinkedList<>();
    private ResultsImpl results;

    public void setup(int limit) {
        this.results = new ResultsImpl(limit);
    }

    @Override
    public void addObserver(ModelObserver observer){
        this.observers.add(observer);
    }


    @Override
    public void notifyObservers(ModelObserver.Event event) throws InterruptedException {
        for(ModelObserver observer : this.observers){
            switch (event){
                case RESULT_UPDATED -> observer.resultsUpdated();
                //case COMPUTATION_ENDED -> observer.computationEnded();
            }
        }
    }

    @Override
    public ResultsImpl getResult() {
        return results;
    }

}
