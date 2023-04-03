package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

public class MasterThread extends Thread{

    private final int nWorkers;

    private final int NI = 5;
    private final int MAXL = 1000;
    private final int N = 10;

    IBoundedBufferFileFind<File> bufferNameFile;
    IBoundedBuffer<Pair<File, Integer>> bufferCounter;
    int nConsumers = 5;
    static Map<String, Long> numFilesLines = new HashMap<>();

    public MasterThread(int nWorkers) {
        this.nWorkers = nWorkers;
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


        bufferNameFile = new BoundedBufferFileFind<>(filesPath.size());

        final Comparator<Pair<String, Integer>> comparator = reverseOrder(comparing(Pair::getY));
        bufferCounter = new BoundedBuffer<>(filesPath.size(), comparator);
        for(Object o : filesPath){
            try {
                bufferNameFile.put(new File(o.toString()));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        for (int i = 0; i < nConsumers; i++){
            new WorkerCountLines(bufferNameFile, bufferCounter).start();
        }


        try {
            Thread.sleep(10000);
            List<Pair<File, Integer>> pair = bufferCounter.get(10);
            for(Pair<File, Integer> p : pair){
                System.out.println(p.getX()+" "+p.getY());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }







    /*
        String path = "./fileExample";
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        try {
            countLines(listOfFiles);
           // orderByNumLines();
        } catch (Exception e) {
            System.out.println(e);
        }

     */




    }

    public static void countLines(File[] listOfFiles) throws IOException {
        if(listOfFiles != null) {
            for(File file : listOfFiles) {
                if(file.isDirectory()) {
                    countLines(file.listFiles());
                } else {
                    numFilesLines.put(file.getName(), Files.lines(Paths.get(file.getPath())).count());
                }
            }
        }
    }



}
