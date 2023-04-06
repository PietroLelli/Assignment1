package org.example.view;

import org.example.controller.Controller;

public interface View {
    void setController(Controller controller);

    void resultsUpdated() throws InterruptedException;

    void endComputation();
}
