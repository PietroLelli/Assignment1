package org.example.gameLobby.impl;

import com.sun.jdi.VoidType;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.ConflictResponse;
import io.javalin.http.NotFoundResponse;
import org.example.*;
import org.example.gameLobby.GameLobbyApi;
import org.example.users.UserApi;

import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class GameLobbyApiImpl extends AbstractApi implements GameLobbyApi {
    public GameLobbyApiImpl(Blackjack storage) {
        super(storage);
    }



    @Override
    public CompletableFuture<Boolean> isUserOver21(User user) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return storage().isOver21(user);
                    } catch (ConflictException e) {
                        throw new ConflictResponse();
                    }catch (IllegalArgumentException e){
                        throw new BadRequestResponse();
                    }
                }
        );
    }

    @Override
    public CompletableFuture<Boolean> allUsersAreReady(String userId) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try{
                        return storage().allUsersReady(userId);
                    } catch (MissingException e) {
                        throw new NotFoundResponse();
                    } catch (ConflictException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    @Override
    public CompletableFuture<Card> getOneCroupierCard(String userId) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try{
                        return storage().getCroupierCard(userId);
                    } catch (MissingException e) {
                        throw new NotFoundResponse();
                    } catch (ConflictException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    @Override
    public CompletableFuture<HandResult> checkResultUserHand(User user) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return storage().resultUserHand(user);
                    } catch (ConflictException e) {
                        throw new ConflictResponse();
                    }catch (IllegalArgumentException e){
                        throw new BadRequestResponse();
                    }
                }
        );
    }


    @Override
    public CompletableFuture<Void> deleteUser(String userId) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        storage().removeUser(userId);
                        return null;
                    } catch (ConflictException e) {
                        throw new ConflictResponse();
                    }catch (IllegalArgumentException e){
                        throw new BadRequestResponse();
                    }
                }
        );
    }

    @Override
    public CompletableFuture<Integer> getValueCroupierCard(String userId) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try{
                        return storage().getValueCroupierCard(userId);
                    } catch (MissingException e) {
                        throw new NotFoundResponse();
                    } catch (ConflictException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    @Override
    public CompletableFuture<Boolean> resetActualPlay(String nickname) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return storage().resetPlay(nickname);
                    } catch (ConflictException e) {
                        throw new ConflictResponse();
                    }catch (IllegalArgumentException e){
                        throw new BadRequestResponse();
                    } catch (MissingException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }


}
