package org.example.view;

import org.example.controller.Controller;
import org.example.model.ModelObserver;

public interface View extends ModelObserver {
    void setController(Controller controller);

    void endComputation();
}
