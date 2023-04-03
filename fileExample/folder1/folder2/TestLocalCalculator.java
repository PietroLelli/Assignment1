package it.unibo.ds.lab.presentation;

import it.unibo.ds.presentation.Calculator;
import it.unibo.ds.presentation.LocalCalculator;

import java.io.IOException;

public class TestLocalCalculator extends AbstractTestCalculator {

    @Override
    protected void beforeCreatingCalculator() throws IOException {
        // do nothing
    }

    @Override
    protected Calculator createCalculator() {
        return new LocalCalculator();
    }

    @Override
    protected void shutdownCalculator(Calculator calculator) {
        // do nothing
    }

    @Override
    protected void afterShuttingCalculatorDown() throws InterruptedException {
        // do nothing
    }
}
