package org.example.model;

import org.example.utils.ComputedFile;

public interface Model {
    ComputedFile getResult();
    void setup(int limit, int maxL, int numIntervals);

}
