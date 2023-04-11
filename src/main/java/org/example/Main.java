package org.example;

import org.example.controller.Controller;
import org.example.controller.ControllerImpl;
import org.example.model.MasterThread;
import org.example.model.Model;
import org.example.model.ModelImpl;
import org.example.view.ConsoleView;
import org.example.view.GuiView;
import org.example.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

    public static final int NUMBER_OF_WORKERS = Runtime.getRuntime().availableProcessors();
    static Controller controller;
    public static void main(String[] args) throws IOException {
        final Model model = new ModelImpl();
        final View view = new ConsoleView();
        controller = new ControllerImpl(model, view);

        setupConsole();
    }

    private static void setupConsole() throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Inserisci il path: ");
        String path = bufferedReader.readLine();
        System.out.println("Inserisci il numero di file da visualizzare nella classifica: ");
        int limit = Integer.parseInt(bufferedReader.readLine());
        System.out.println("Inserisci il numero max di linee: ");
        int maxL = Integer.parseInt(bufferedReader.readLine());
        System.out.println("Inserisci il numero di intervalli: ");
        int numIntervals = Integer.parseInt(bufferedReader.readLine());
        
        controller.start(NUMBER_OF_WORKERS, path, limit, maxL, numIntervals);
    }
}