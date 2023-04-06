package org.example.view;

import org.example.controller.Controller;
import org.example.utils.Pair;

import javax.swing.*;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConsoleView implements View{
    private Controller controller;

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void endComputation() {
        System.out.println("Ranking: ");

        List<Pair<File, Integer>> rankingList = new LinkedList<>(this.controller.getResult().getRanking());
        for(Pair<File, Integer> p : rankingList) {
            System.out.println(p.getX().getName()+": "+p.getY());
        }

        System.out.println("Intervals: ");
        Map<Pair<Integer, Integer>, Integer> filesInRange = this.controller.getResult().getFilesInRange();
        for(Map.Entry<Pair<Integer, Integer>, Integer> p : filesInRange.entrySet()) {
            System.out.println(p.getKey().getX()+"-"+p.getKey().getY()+": "+p.getValue());
        }
    }

    @Override
    public void resultsUpdated() throws InterruptedException {
    }
}
