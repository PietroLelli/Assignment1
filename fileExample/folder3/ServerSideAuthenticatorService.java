package it.unibo.ds.lab.presentation.server;

import it.unibo.ds.presentation.Authenticator;
import it.unibo.ds.presentation.LocalAuthenticator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

public class ServerSideAuthenticatorService extends Thread {

    private final ServerSocket serverSocket;
    private volatile boolean shouldTerminate = false;
    private final Authenticator localAuthenticator = new LocalAuthenticator();

    public ServerSideAuthenticatorService(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        while (!shouldTerminate) {
            try {
                var socket = serverSocket.accept();
                var handler = new ServerSideAuthenticatorStub(socket, localAuthenticator);
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
