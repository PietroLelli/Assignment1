package it.unibo.oop.lab.mvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimpleController implements Controller {

    private final Set<String> setString;
    private final List<String> listHistoryString;
    private String current;

    public SimpleController() {
        setString = new HashSet<>();
        listHistoryString = new ArrayList<>();
    }
    public final void setNextString(final String string) {
        current = string;
        setString.add(string);
        listHistoryString.add(string);
    }
    public final String getNextString() {
        return current;
    }
    public final List<String> getHistory() {
       return listHistoryString;
    }
    public final void printCurrentString() {
        getNextString();
        if (this.current == null) {
            throw new IllegalStateException("String unset!");
        }
        System.out.println(current);
    }

}
