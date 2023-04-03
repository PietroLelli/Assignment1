package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static final int NUMBER_OF_WORKERS = 5;
    public static void main(String[] args) {
        MasterThread masterThread = new MasterThread(NUMBER_OF_WORKERS);
        masterThread.start();
    }

}