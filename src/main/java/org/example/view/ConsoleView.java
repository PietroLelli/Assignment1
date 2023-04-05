package org.example.view;

import org.example.controller.Controller;

public class ConsoleView implements View{
    private Controller controller;

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void resultsUpdated() throws InterruptedException {

    }
}
