package it.unibo.ds.lab.presentation;

import it.unibo.ds.lab.presentation.client.ClientSideAuthenticator;
import it.unibo.ds.lab.presentation.server.ServerSideAuthenticatorService;
import it.unibo.ds.presentation.Authenticator;
import it.unibo.ds.presentation.BadContentException;
import it.unibo.ds.presentation.ConflictException;
import it.unibo.ds.presentation.LocalAuthenticator;

import java.io.IOException;

public class TestRemoteAuthenticator extends AbstractTestAuthenticator {

    private static final int port = 10000;

    private ServerSideAuthenticatorService service;

    @Override
    protected void beforeCreatingAuthenticator() throws IOException {
        service = new ServerSideAuthenticatorService(port);
        service.start();
    }

    @Override
    protected Authenticator createAuthenticator() throws BadContentException, ConflictException {
        return new ClientSideAuthenticator("localhost", port);
    }

    @Override
    protected void shutdownAuthenticator(Authenticator authenticator) {
        // do nothing
    }

    @Override
    protected void afterShuttingAuthenticatorDown() throws InterruptedException {
        service.terminate();
        service.join(1000);
    }
}
