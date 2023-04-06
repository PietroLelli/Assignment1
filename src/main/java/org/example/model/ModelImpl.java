package org.example.model;

import org.example.utils.ComputedFile;
import org.example.utils.ComputedFileImpl;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

public class ModelImpl implements Model{
    private ComputedFile results;

    public void setup(int limit, int maxL, int numIntervals) {
        this.results = new ComputedFileImpl(limit, maxL, numIntervals);
    }

    @Override
    public ComputedFile getResult() {
        return results;
    }

}
