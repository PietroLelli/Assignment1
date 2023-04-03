package it.unibo.ds.lab.ws.client;

import it.unibo.ds.ws.*;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

public class RemoteAuthenticator extends AbstractHttpClientStub implements Authenticator {

    public RemoteAuthenticator(URI host) {
        super(host, "auth", "0.1.0");
    }

    public RemoteAuthenticator(String host, int port) {
        this(URI.create("http://" + host + ":" + port));
    }

    private CompletableFuture<Token> authorizeAsync(Credentials credentials) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/tokens"))
                .header("Accept", "application/json")
                .POST(body(credentials))
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(Token.class));
    }

    @Override
    public Token authorize(Credentials credentials) throws WrongCredentialsException, IllegalArgumentException {
        try {
            return authorizeAsync(credentials).join();
        } catch (CompletionException e) {
            if(e.getCause() instanceof WrongCredentialsException){
                throw getCauseAs(e, WrongCredentialsException.class);
            }else {
                throw getCauseAs(e, IllegalArgumentException.class);
            }
        }
    }

    private CompletableFuture<?> registerAsync(User user) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/users"))
                .header("Accept", "application/json")
                .POST(body(user))
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(String.class));
    }

    @Override
    public void register(User user) throws ConflictException {
        try {
            registerAsync(user).join();
        } catch (CompletionException e) {
            throw getCauseAs(e, ConflictException.class);
        }
    }

    private CompletableFuture<?> removeAsync(String userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/users/"+userId))
                .DELETE()
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse());
    }

    @Override
    public void remove(String userId) throws MissingException {
        try {
            removeAsync(userId).join();
        } catch (CompletionException e) {
            throw getCauseAs(e, MissingException.class);
        }
    }

    private CompletableFuture<User> getAsync(String userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/users/"+userId))
                .header("Accept", "application/json")
                .GET()
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(User.class));
    }

    @Override
    public User get(String userId) throws MissingException {
        try {
            return getAsync(userId).join();
        } catch (CompletionException e) {
            throw getCauseAs(e, MissingException.class);
        }
    }

    private CompletableFuture<?> editAsync(String userId, User changes) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/users/"+userId))
                .header("Accept", "application/json")
                .PUT(body(changes))
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(String.class));
    }

    @Override
    public void edit(String userId, User changes) throws MissingException, ConflictException {
        try {
            editAsync(userId, changes).join();
        } catch (CompletionException e) {
            if(e.getCause() instanceof MissingException){
                throw getCauseAs(e, MissingException.class);
            }else if(e.getCause() instanceof ConflictException){
                throw getCauseAs(e, ConflictException.class);
            }
        }
    }

    protected CompletableFuture<List<String>> getAllNamesAsync() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/users?limit=-1"))
                .header("Accept", "application/json")
                .GET()
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeMany(String.class));
    }

    @Override
    public Set<? extends User> getAll() {
        List<String> userNames = getAllNamesAsync().join();
        Set<User> users = new HashSet<>();
        for(String name : userNames){
            User user = getAsync(name).join();
            users.add(user);
        }
        return users;
    }
}
