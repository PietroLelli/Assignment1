package org.example;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestRemoteBlackjack extends AbstractTestBlackjack {
    private static final int port = 10000;
    private BlackjackService service;

    @Override
    protected void beforeCreatingBlackjack(){
        service = new BlackjackService(port, true);
        service.start();
    }

    @Override
    protected Blackjack createBlackjack(){
        return new RemoteBlackjack("localhost", port);
    }

    @Override
    protected void shutdownBlackjack(Blackjack blackjack) {
    }

    @Override
    protected void afterShuttingBlackjackDown() {
        service.stop();
    }

    @Override
    @Test
    public void testRegisterUsers() throws ConflictException {
    super.testRegisterUsers();
    }
    @Override
    @Test
    public void testRegisterUsersAlreadyExist() throws ConflictException {
        super.testRegisterUsersAlreadyExist();
    }
    @Override
    @Test
    public void testRegisterUsersNotAlreadyExist() throws ConflictException {
        super.testRegisterUsersNotAlreadyExist();
    }
    @Override
    @Test
    public void testBlackjackUserResult() throws ConflictException, MissingException {
        super.testBlackjackUserResult();
    }

    @Override
    @Test
    public void testResetPlay() throws ConflictException, MissingException {
        super.testResetPlay();
    }


}
