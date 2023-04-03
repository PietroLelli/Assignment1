package it.unibo.ds.lab.presentation;

import it.unibo.ds.presentation.Authenticator;
import it.unibo.ds.presentation.BadContentException;
import it.unibo.ds.presentation.ConflictException;
import it.unibo.ds.presentation.LocalAuthenticator;

public class TestLocalAuthenticator extends AbstractTestAuthenticator {
    @Override
    protected void beforeCreatingAuthenticator() {
        // do nothing
    }

    @Override
    protected Authenticator createAuthenticator() throws BadContentException, ConflictException {
        return new LocalAuthenticator();
    }

    @Override
    protected void shutdownAuthenticator(Authenticator authenticator) {
        // do nothing
    }

    @Override
    protected void afterShuttingAuthenticatorDown() {
        // do nothing
    }
}
