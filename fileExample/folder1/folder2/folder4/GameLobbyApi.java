package org.example.gameLobby;

import org.example.*;
import org.example.gameLobby.impl.GameLobbyApiImpl;
import org.example.users.impl.UserApiImpl;

import java.util.concurrent.CompletableFuture;

public interface GameLobbyApi {
    static GameLobbyApi of(Blackjack storage) {
        return new GameLobbyApiImpl(storage);
    }
    CompletableFuture<Boolean> isUserOver21(User gameLobby);
    CompletableFuture<Boolean> allUsersAreReady(String userId);
    CompletableFuture<Card> getOneCroupierCard(String userId);
    CompletableFuture<HandResult> checkResultUserHand(User user);
    CompletableFuture<Void> deleteUser(String userId);
    CompletableFuture<Integer> getValueCroupierCard(String userId);
    CompletableFuture<Boolean> resetActualPlay(String nickname);

}
