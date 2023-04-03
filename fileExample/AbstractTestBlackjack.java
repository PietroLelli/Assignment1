package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractTestBlackjack {

    private User ventu00;
    private Blackjack blackjack;

    @BeforeEach
    public final void setup() throws ConflictException, IOException {
        beforeCreatingBlackjack();
        blackjack = createBlackjack();

    }

    protected abstract void beforeCreatingBlackjack() throws IOException;

    protected abstract Blackjack createBlackjack() throws ConflictException;

    @AfterEach
    public final void teardown() throws InterruptedException {
        shutdownBlackjack(blackjack);
        afterShuttingBlackjackDown();
    }

    protected abstract void shutdownBlackjack(Blackjack authenticator);

    protected abstract void afterShuttingBlackjackDown() throws InterruptedException;

    public void testRegisterUsers() throws ConflictException {
        assertDoesNotThrow(() -> blackjack.register("malu00"));
    }
    public void testRegisterUsersAlreadyExist() throws ConflictException {
        assertDoesNotThrow(() -> blackjack.register("filo00"));
        assertThrows(ConflictException.class, () -> blackjack.register("filo00"));
    }
    public void testRegisterUsersNotAlreadyExist() throws ConflictException {
        assertDoesNotThrow(() -> blackjack.register("pietro00"));
        assertDoesNotThrow(() -> blackjack.register("ventu00"));
    }

    public void testBlackjackUserResult() throws ConflictException, MissingException {
        boolean bj = false;
        User malu74 = null;
        String bet = "6";
        while (!bj){
            malu74 = blackjack.register("malu74");
            blackjack.addBet(new Token(malu74, bet));
            blackjack.hitUserCard(malu74);
            blackjack.hitUserCard(malu74);

            if(blackjack.getValueUserCard("malu74") == 21){
                bj = true;
            }
            else{
                blackjack.removeUser(malu74.getNickname());
            }
        }

        blackjack.getCroupierCard(malu74.getNickname());
        blackjack.userReady(malu74);
        blackjack.setFalseUserReadyStatus(malu74.getNickname());
        blackjack.resultUserHand(malu74);

        if(blackjack.hasBlackjackUser(malu74.getNickname())){
            assertEquals(malu74.getBalance() + ((Integer.valueOf(bet)/2)*3), blackjack.getBalance(malu74.getNickname()));
        }
    }

    public void testResetPlay() throws ConflictException, MissingException {
        blackjack.register("mirco05");
        blackjack.hitUserCard(new User("mirco05"));
        blackjack.hitUserCard(new User("mirco05"));
        assertNotEquals(0, blackjack.getValueUserCard("mirco05"));
        blackjack.resetPlay("mirco05");
        assertEquals(0, blackjack.getValueUserCard("mirco05"));

    }


}
