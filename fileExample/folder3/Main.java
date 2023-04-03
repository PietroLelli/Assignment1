package org.example;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.*;

public class Main {


    public static void main(String[] args) throws IOException {
        String D = "";
        int NI = 5;
        int MAXL = 100;
        List<List<File>> fileInIntervallo = new ArrayList<>();

        for (int i =0; i < NI; i++){
            fileInIntervallo.add(new ArrayList<>());
        }



        List filesPath;
        try (Stream<Path> walk = Files.walk(Paths.get("./fileExample"))) {
            filesPath = walk
                    .filter(p -> !Files.isDirectory(p))   // not a directory
                    .map(p -> p.toString().toLowerCase()) // convert path to string
                    .filter(f -> f.endsWith("txt"))       // check end with
                    .collect(Collectors.toList());        // collect all matched to a List
        }
        List<File> files = new ArrayList<>();
        for(Object o : filesPath){
            files.add(new File(o.toString()));
        }

        int nThread = 5;//TODO da modificare
        int countIndexFile = 0;

        List<Worker> workers = new ArrayList<>();

        for (int i = 0; i < nThread; i++){
            List<File> fileForThread = new ArrayList<>();
            for(int f = 0; f < files.size() / nThread; f++){
                fileForThread.add(files.get(countIndexFile));
                countIndexFile++;
            }
            workers.add(new Worker(fileForThread));
            workers.get(i).start();
        }

        for (Worker w : workers){
            System.out.println(w.printFileAndLine());
        }

    }

}