package it.unibo.ds.ws.users.impl;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.ConflictResponse;
import io.javalin.http.NotFoundResponse;
import it.unibo.ds.ws.*;
import it.unibo.ds.ws.AbstractApi;
import it.unibo.ds.ws.users.UserApi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class UserApiImpl extends AbstractApi implements UserApi {

    public UserApiImpl(Authenticator storage) {
        super(storage);
    }

    @Override
    public CompletableFuture<Collection<? extends String>> getAllNames(int skip, int limit, String filter) {
        return CompletableFuture.supplyAsync(
                () -> {
                    Set<? extends User> users = storage().getAll();
                    List<String> usersNames = new ArrayList<>();
                    int i = 0;
                    for(User user : users){
                        i++;
                        if(i < skip){
                            continue;
                        }
                        usersNames.add(user.getUsername());
                        if(limit != -1 && i >= limit){
                            break;
                        }
                    }
                    return usersNames;
                }
        );
    }

    @Override
    public CompletableFuture<String> registerUser(User user) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        storage().register(user);
                        return user.getUsername();
                    } catch (ConflictException e) {
                        throw new ConflictResponse();
                    }catch (IllegalArgumentException e){
                        throw new BadRequestResponse();
                    }
                }
        );
    }

    @Override
    public CompletableFuture<User> getUser(String userId){
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        User result = storage().get(userId);
                        return result;
                    } catch (MissingException e) {
                        throw new NotFoundResponse();
                    }
                }
        );
    }

    @Override
    public CompletableFuture<Void> removeUser(String userId) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        storage().remove(userId);
                        return null;
                    } catch (MissingException e) {
                        throw new NotFoundResponse();
                    }
                }
        );
    }

    @Override
    public CompletableFuture<String> editUser(String userId, User changes) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        storage().edit(userId, changes);
                        return changes.getUsername();
                    } catch (MissingException e) {
                        throw new NotFoundResponse();
                    } catch (ConflictException e) {
                        throw new ConflictResponse();
                    }
                }
        );
    }
}
