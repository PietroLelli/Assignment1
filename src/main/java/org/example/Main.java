package org.example;

import org.example.controller.Controller;
import org.example.controller.ControllerImpl;
import org.example.model.MasterThread;
import org.example.model.Model;
import org.example.model.ModelImpl;
import org.example.view.ConsoleView;
import org.example.view.GuiView;
import org.example.view.View;

public class Main {

    public static final int NUMBER_OF_WORKERS = 5;
    public static void main(String[] args) {

        final Model model = new ModelImpl();
        final View view = new ConsoleView();
        final Controller controller = new ControllerImpl(model, view);

        model.addObserver(view);

        controller.start(NUMBER_OF_WORKERS, 10);
    }
}