package org.example.view;

import org.example.controller.Controller;
import org.example.utils.Pair;

import javax.swing.*;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class ConsoleView implements View{
    private Controller controller;

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void endComputation() {
        List<Pair<File, Integer>> rankingList = new LinkedList<>();
        rankingList.addAll(this.controller.getResult().getRanking());
        System.out.println(rankingList);
    }

    @Override
    public void resultsUpdated() throws InterruptedException {
    }


}
