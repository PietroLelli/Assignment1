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
    private ResultsImpl results;

    public void setup(int limit, int maxL, int numIntervals) {
        this.results = new ResultsImpl(limit, maxL, numIntervals);
    }

    @Override
    public ResultsImpl getResult() {
        return results;
    }

}
