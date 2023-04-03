package it.unibo.oop.lab.workers02;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MultiThreadedSumMatrix implements  SumMatrix {
    private int nWorkers;
    private List<List<double[]>> list;

    public MultiThreadedSumMatrix(final int nWorkers) {
        super();
        this.nWorkers = nWorkers;


    }
    public double sum(final double[][] matrix) {
        this.list = Arrays.stream(matrix).map(a -> Arrays.asList(a)).collect(Collectors.toList());
        final int size = list.size() % n + list.size() / n;
        return 0;
    }

    private static class Worker implements Runnable {
        private final List<Integer> list;
        private final int startpos;
        private final int nelem;
        private long res;

        public Worker(final List<Integer> list, final int startpos, final int nelem) {
            super();
            this.list = list;
            this.startpos = startpos;
            this.nelem = nelem;
        }

        public void run() {
            System.out.println("Working from position " + startpos + " to position " + (startpos + nelem - 1));
            for (int i = startpos; i < list.size() && i < startpos + nelem; i++) {
                this.res += this.list.get(i);
            }
        }

    }

}
