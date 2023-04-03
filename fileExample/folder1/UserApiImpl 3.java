package org.example.users.impl;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.ConflictResponse;
import io.javalin.http.NotFoundResponse;
import org.example.*;
import org.example.users.UserApi;

import java.util.concurrent.CompletableFuture;

public class UserApiImpl extends AbstractApi implements UserApi {
    public UserApiImpl(Blackjack storage) {
        super(storage);
    }

    @Override
    public CompletableFuture<User> registerUser(String nickname) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return storage().register(nickname);
                    } catch (ConflictException e) {
                        throw new ConflictResponse();
                    }catch (IllegalArgumentException e){
                        throw new BadRequestResponse();
                    }
                }
        );
    }

    @Override
    public CompletableFuture<String> addBetValue(Token token) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        storage().addBet(token);
                        return token.getUser().getNickname();
                    } catch (ConflictException e) {
                        throw new ConflictResponse();
                    }catch (IllegalArgumentException e){
                        throw new BadRequestResponse();
                    }
                }
        );
    }

    @Override
    public CompletableFuture<Card> hitNewUserCard(User user) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        Card newCard = storage().hitUserCard(user);
                        return newCard;
                    } catch (ConflictException e) {
                        throw new ConflictResponse();
                    }catch (IllegalArgumentException e){
                        throw new BadRequestResponse();
                    }
                }
        );
    }

    @Override
    public CompletableFuture<User> userIsReady(User user) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return storage().userReady(user);
                    } catch (ConflictException e) {
                        throw new ConflictResponse();
                    }catch (IllegalArgumentException e){
                        throw new BadRequestResponse();
                    }

                }
        );
    }

    @Override
    public CompletableFuture<Card> checkCroupierCardIsExtracted(String userId) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try{
                        return storage().checkCroupierCard(userId);
                    } catch (MissingException e) {
                        throw new NotFoundResponse();
                    } catch (ConflictException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    @Override
    public CompletableFuture<Integer> getUserBalance(String userId) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try{
                        return storage().getBalance(userId);
                    } catch (MissingException e) {
                        throw new NotFoundResponse();
                    } catch (ConflictException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    @Override
    public CompletableFuture<Integer> getValueUserCard(String userId) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try{
                        return storage().getValueUserCard(userId);
                    } catch (MissingException e) {
                        throw new NotFoundResponse();
                    } catch (ConflictException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    @Override
    public CompletableFuture<Boolean> setTrueUserReadyStatus(String nickname) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return storage().setTrueUserReadyStatus(nickname);
                    } catch (ConflictException e) {
                        throw new ConflictResponse();
                    }catch (IllegalArgumentException e){
                        throw new BadRequestResponse();
                    }

                }
        );
    }
    @Override
    public CompletableFuture<Boolean> setFalseUserReadyStatus(String nickname) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return storage().setFalseUserReadyStatus(nickname);
                    } catch (ConflictException e) {
                        throw new ConflictResponse();
                    }catch (IllegalArgumentException e){
                        throw new BadRequestResponse();
                    }
                }
        );
    }

    @Override
    public CompletableFuture<Boolean> hasBlackjackUser(String userId) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try{
                        return storage().hasBlackjackUser(userId);
                    } catch (MissingException e) {
                        throw new NotFoundResponse();
                    } catch (ConflictException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    @Override
    public CompletableFuture<Void> isAlive(String userId) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        storage().isAlive(userId);
                        return null;
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

    @Override
    public CompletableFuture<Boolean> isUserInLobby(String userId) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try{
                        return storage().isUserInLobby(userId);
                    } catch (MissingException e) {
                        throw new NotFoundResponse();
                    } catch (ConflictException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }
}
