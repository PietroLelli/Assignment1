package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestLocalBlackjack extends AbstractTestBlackjack{
    @Override
    protected void beforeCreatingBlackjack(){
        // do nothing
    }

    @Override
    protected Blackjack createBlackjack(){
        return new LocalBlackjack();
    }

    @Override
    protected void shutdownBlackjack(Blackjack authenticator) {
        // do nothing
    }

    @Override
    protected void afterShuttingBlackjackDown() {
        // do nothing
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
    public void testResetPlay() throws ConflictException, MissingException {
        super.testResetPlay();
    }

}
