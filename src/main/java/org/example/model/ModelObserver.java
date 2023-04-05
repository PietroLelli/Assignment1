package org.example.model;

public interface ModelObserver {
    enum Event{
        RESULT_UPDATED,
        COMPUTATION_ENDED
    }
    void resultsUpdated() throws InterruptedException;
    //void computationEnded();
}
