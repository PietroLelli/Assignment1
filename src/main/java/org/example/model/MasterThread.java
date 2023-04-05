package org.example.model;

import org.example.controller.Controller;
import org.example.utils.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

public class MasterThread extends Thread{

    private final int nWorkers;
    private final int NI = 5;
    private final int MAXL = 1000;
    private final int N = 10;

    BufferSynchronized<File> bufferNameFile;
    BufferSynchronized<Pair<File, Integer>> bufferCounter;
    int nConsumers = 5;
    private Controller controller;

    public MasterThread(int nWorkers, Controller controller) {
        this.nWorkers = nWorkers;
        this.controller = controller;
    }

    @Override
    public void run() {

        List<Object> filesPath;
        try (Stream<Path> walk = Files.walk(Paths.get("./fileExample"))) {
            filesPath = walk
                    .filter(p -> !Files.isDirectory(p))   // not a directory
                    .map(p -> p.toString().toLowerCase()) // convert path to string
                    .filter(f -> f.endsWith("java"))       // check end with
                    .collect(Collectors.toList());        // collect all matched to a List
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        bufferNameFile = new BufferSynchronizedImpl<>();
        for(Object o : filesPath){
            try {
                bufferNameFile.put(new File(o.toString()));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        bufferCounter = new BufferSynchronizedImpl<>();

        for (int i = 0; i < nWorkers; i++){
            new WorkerCountLines(bufferNameFile, bufferCounter).start();
        }

        for(int i = 0; i < filesPath.size(); i++){
            try {
                Pair<File, Integer> result = bufferCounter.get();
                this.controller.addResult(result);
                this.controller.notifyObservers(ModelObserver.Event.RESULT_UPDATED);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
