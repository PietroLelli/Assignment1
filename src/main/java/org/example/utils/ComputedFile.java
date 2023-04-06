package org.example.utils;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ComputedFile {
    void add(Pair<File, Integer> elem);
    List<Pair<File, Integer>> getRanking();
    Map<Pair<Integer, Integer>, Integer> getFilesInRange();
}
