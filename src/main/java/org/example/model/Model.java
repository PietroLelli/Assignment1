package org.example.model;

import org.example.utils.BufferSynchronized;
import org.example.utils.Pair;
import org.example.utils.ResultsImpl;

import java.io.File;
import java.util.List;

public interface Model {
    ResultsImpl getResult();
    void setup(int limit, int maxL, int numIntervals);

}
