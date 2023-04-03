package it.unibo.ds.lab.presentation.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

public class ServerSideCalculatorService extends Thread {

    private final ServerSocket serverSocket;
    private volatile boolean shouldTerminate = false;

    public ServerSideCalculatorService(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        while (!shouldTerminate) {
            try {
                var socket = serverSocket.accept();
                var handler = new ServerSideCalculatorStub(socket);
                handler.start();
            } catch (SocketException e) {
                if (e.getMessage().contains("closed")) {
                    // silently ignores
                } else {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void terminate() {
        shouldTerminate = true;
        try {
            serverSocket.close();
        } catch (IOException e) {
            // silently ignores
        }
    }
}
