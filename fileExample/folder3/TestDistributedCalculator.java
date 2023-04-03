package it.unibo.ds.lab.presentation;

import it.unibo.ds.lab.presentation.client.ClientSideCalculator;
import it.unibo.ds.lab.presentation.server.ServerSideCalculatorService;
import it.unibo.ds.presentation.Calculator;
import it.unibo.ds.presentation.LocalCalculator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestDistributedCalculator extends AbstractTestCalculator {

    private ServerSideCalculatorService service;
    private static final int port = 10000;


    @Override
    protected void beforeCreatingCalculator() throws IOException {
        service = new ServerSideCalculatorService(port);
        service.start();
    }

    @Override
    protected Calculator createCalculator() {
        return new ClientSideCalculator("localhost", port);
    }

    @Override
    protected void shutdownCalculator(Calculator calculator) {
        // does nothing
    }

    @Override
    protected void afterShuttingCalculatorDown() throws InterruptedException {
        service.terminate();
        service.join();
    }
}
