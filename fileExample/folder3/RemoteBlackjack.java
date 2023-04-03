package org.example;


import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class RemoteBlackjack extends AbstractHttpClientStub implements Blackjack {

    public RemoteBlackjack(URI host) {
        super(host, "blackjack", "0.1.0");
    }

    public RemoteBlackjack(String host, int port) {
        this(URI.create("http://" + host + ":" + port));
    }

    private CompletableFuture<?> registerAsync(String nickname) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/users"))
                .header("Accept", "application/json")
                .POST(body(nickname))
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(User.class));
    }
    @Override
    public User register(String nickname) throws ConflictException {
        try {
            User result = (User) registerAsync(nickname).join();
            return  result;
        } catch (CompletionException e) {
            throw getCauseAs(e, ConflictException.class);
        }
    }

    private CompletableFuture<?> addBetAsync(Token token) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/users/balance"))
                .header("Accept", "application/json")
                .PUT(body(token))
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(String.class));
    }
    @Override
    public void addBet(Token token) throws ConflictException {
        try {
            addBetAsync(token).join();
        } catch (CompletionException e) {
            throw getCauseAs(e, ConflictException.class);
        }
    }

    private CompletableFuture<?> hitUserCardAsync(User user) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/users/newcard"))
                .header("Accept", "application/json")
                .POST(body(user))
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(Card.class));
    }
    @Override
    public Card hitUserCard(User user) throws ConflictException {
        try {
            Card result = (Card) hitUserCardAsync(user).join();
            return result;
        } catch (CompletionException e) {
            throw getCauseAs(e, ConflictException.class);
        }
    }

    private CompletableFuture<?> userReadyAsync(User user) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/users/userready"))
                .header("Accept", "application/json")
                .POST(body(user))
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(User.class));
    }

    @Override
    public User userReady(User user) throws ConflictException {
        try {
            return (User) userReadyAsync(user).join();
        } catch (CompletionException e) {
            throw getCauseAs(e, ConflictException.class);
        }
    }
    private CompletableFuture<Card> checkCroupierCardAsync(String userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/users/"+userId))
                .header("Accept", "application/json")
                .GET()
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(Card.class));
    }

    @Override
    public Card checkCroupierCard(String userId) throws MissingException {
        try {
            return checkCroupierCardAsync(userId).join();
        } catch (CompletionException e) {
            throw getCauseAs(e, MissingException.class);
        }
    }
    private CompletableFuture<?> isOver21Async(User user) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/lobby/over21"))
                .header("Accept", "application/json")
                .POST(body(user))
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(Boolean.class));
    }
    @Override
    public Boolean isOver21(User user) throws ConflictException {
        try {
            return (Boolean) isOver21Async(user).join();
        } catch (CompletionException e) {
            throw getCauseAs(e, ConflictException.class);
        }
    }

    private CompletableFuture<Boolean> allUsersReadyAsync(String userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/lobby/"+userId))
                .header("Accept", "application/json")
                .GET()
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(Boolean.class));
    }
    @Override
    public Boolean allUsersReady(String userId) throws MissingException {
        try {
            return allUsersReadyAsync(userId).join();
        } catch (CompletionException e) {
            throw getCauseAs(e, MissingException.class);
        }
    }
    private CompletableFuture<?> getCroupierCardAsync(String userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/lobby/getCroupierCard/"+userId))
                .header("Accept", "application/json")
                .GET()
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(Card.class));
    }
    @Override
    public Card getCroupierCard(String userId) throws MissingException{
        try {
            return (Card) getCroupierCardAsync(userId).join();
        } catch (CompletionException e) {
            throw getCauseAs(e, MissingException.class);
        }
    }

    private CompletableFuture<?> resultUserHandAsync(User user) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/lobby/resulthand"))
                .header("Accept", "application/json")
                .POST(body(user))
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(HandResult.class));
    }

    @Override
    public HandResult resultUserHand(User user) throws ConflictException {
        try {
            return (HandResult) resultUserHandAsync(user).join();
        } catch (CompletionException e) {
            throw getCauseAs(e, ConflictException.class);
        }
    }

    private CompletableFuture<?> removeUserAsync(String userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/lobby/"+userId))
                .DELETE()
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse());

    }

    @Override
    public void removeUser(String userId) throws ConflictException {
        try {
            removeUserAsync(userId).join();
        } catch (CompletionException e) {
            throw getCauseAs(e, ConflictException.class);
        }
    }

    private CompletableFuture<?> getBalanceAsync(String userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/users/getbalance/"+userId))
                .header("Accept", "application/json")
                .GET()
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(int.class));
    }
    @Override
    public int getBalance(String userId) throws MissingException{
        try {
            return (int) getBalanceAsync(userId).join();
        } catch (CompletionException e) {
            throw getCauseAs(e, MissingException.class);
        }
    }

    private CompletableFuture<Integer> getValueCroupierCardAsync(String userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/lobby/valuecard/"+userId))
                .header("Accept", "application/json")
                .GET()
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(Integer.class));
    }
    @Override
    public int getValueCroupierCard(String userId) throws MissingException{
        try {
            return getValueCroupierCardAsync(userId).join();
        } catch (CompletionException e) {
            throw getCauseAs(e, MissingException.class);
        }
    }
    private CompletableFuture<?> getValueUserCardAsync(String userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/users/valuecard"))
                .header("Accept", "application/json")
                .POST(body(userId))
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(Integer.class));
    }

    @Override
    public int getValueUserCard(String userId) throws MissingException{
        try {
            return (int) getValueUserCardAsync(userId).join();
        } catch (CompletionException e) {
            throw getCauseAs(e, MissingException.class);
        }
    }
    private CompletableFuture<?> resetPlayAsync(String nickname) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/lobby/resetplay"))
                .header("Accept", "application/json")
                .POST(body(nickname))
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(Boolean.class));
    }
    @Override
    public Boolean resetPlay(String nickname) throws MissingException{
        try {
            return (Boolean) resetPlayAsync(nickname).join();
        } catch (CompletionException e) {
            throw getCauseAs(e, MissingException.class);
        }
    }

    private CompletableFuture<?> setTrueUserReadyStatusAsync(String nickname) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/users/settrueuserready"))
                .header("Accept", "application/json")
                .POST(body(nickname))
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(Boolean.class));
    }

    @Override
    public Boolean setTrueUserReadyStatus(String nickname) throws ConflictException {
        try {
            return (Boolean) setTrueUserReadyStatusAsync(nickname).join();
        } catch (CompletionException e) {
            throw getCauseAs(e, ConflictException.class);
        }
    }
    private CompletableFuture<?> setFalseUserReadyStatusAsync(String nickname) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/users/setfalseuserready"))
                .header("Accept", "application/json")
                .POST(body(nickname))
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(Boolean.class));
    }

    @Override
    public Boolean setFalseUserReadyStatus(String nickname) throws ConflictException {
        try {
            return (Boolean) setFalseUserReadyStatusAsync(nickname).join();

        } catch (CompletionException e) {
            throw getCauseAs(e, ConflictException.class);
        }
    }

    private CompletableFuture<?> hasBlackjackUserAsync(String userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/users/userblackjack/"+userId))
                .header("Accept", "application/json")
                .GET()
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(Boolean.class));
    }
    @Override
    public Boolean hasBlackjackUser(String userId) throws MissingException{
        try {
            return (Boolean) hasBlackjackUserAsync(userId).join();
        } catch (CompletionException e) {
            throw getCauseAs(e, MissingException.class);
        }
    }

    private CompletableFuture<?> isAliveAsync(String userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/users/isalive"))
                .header("Accept", "application/json")
                .PUT(body(userId))
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse());

    }
    @Override
    public void isAlive(String userId) throws MissingException, ConflictException {
        try {
            isAliveAsync(userId).join();
        } catch (CompletionException e) {
            throw getCauseAs(e, MissingException.class);
        }
    }
    private CompletableFuture<Boolean> isUserInLobbyAsync(String userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/users/isuserinlobby/"+userId))
                .header("Accept", "application/json")
                .GET()
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(Boolean.class));
    }
    @Override
    public Boolean isUserInLobby(String userId) throws MissingException {
        try {
            return isUserInLobbyAsync(userId).join();
        } catch (CompletionException e) {
            throw getCauseAs(e, MissingException.class);
        }
    }


}
