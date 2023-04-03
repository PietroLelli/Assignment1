package org.example.users;

import org.example.Blackjack;
import org.example.Card;
import org.example.Token;
import org.example.User;
import org.example.users.impl.UserApiImpl;

import java.util.concurrent.CompletableFuture;

public interface UserApi {
    CompletableFuture<User> registerUser(String nickname);
    CompletableFuture<String> addBetValue(Token token);
    CompletableFuture<Card> hitNewUserCard(User user);
    CompletableFuture<User> userIsReady(User user);
    CompletableFuture<Card> checkCroupierCardIsExtracted(String userId);
    CompletableFuture<Integer> getUserBalance(String userId);
    CompletableFuture<Integer> getValueUserCard(String userId);
    CompletableFuture<Boolean> setTrueUserReadyStatus(String nickname);
    CompletableFuture<Boolean> setFalseUserReadyStatus(String nickname);
    CompletableFuture<Boolean> hasBlackjackUser(String userId);
    CompletableFuture<Void> isAlive(String userId);
    CompletableFuture<Boolean> isUserInLobby(String userId);


    static UserApi of(Blackjack storage) {
        return new UserApiImpl(storage);
    }
}
