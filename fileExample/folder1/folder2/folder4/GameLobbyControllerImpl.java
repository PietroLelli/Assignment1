package org.example.gameLobby.impl;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpResponseException;
import org.example.AbstractController;
import org.example.GameLobby;
import org.example.User;
import org.example.gameLobby.GameLobbyApi;
import org.example.gameLobby.GameLobbyController;
import org.example.users.UserApi;
import org.example.utils.Filters;

public class GameLobbyControllerImpl extends AbstractController implements GameLobbyController {

    public GameLobbyControllerImpl(String path) {
        super(path);
    }
    private GameLobbyApi getApi(Context context) {
        return GameLobbyApi.of(getBlackjackInstance(context));
    }

    @Override
    public void isUserOver21(Context context) throws HttpResponseException {
        GameLobbyApi api = getApi(context);
        var user =  context.bodyAsClass(User.class);
        var futureResult = api.isUserOver21(user);
        asyncReplyWithBody(context,"application/json", futureResult);
    }

    @Override
    public void allUsersAreReady(Context context) throws HttpResponseException {
        GameLobbyApi api = getApi(context);
        var userId =  context.pathParam("{userId}");
        var futureResult = api.allUsersAreReady(userId);
        asyncReplyWithBody(context,"application/json", futureResult);

    }

    @Override
    public void getOneCroupierCard(Context context) throws HttpResponseException {
        GameLobbyApi api = getApi(context);
        var userId =  context.pathParam("{userId}");
        var futureResult = api.getOneCroupierCard(userId);
        asyncReplyWithBody(context,"application/json", futureResult);
    }

    @Override
    public void checkResultUserHand(Context context) throws HttpResponseException {
        GameLobbyApi api = getApi(context);
        var user =  context.bodyAsClass(User.class);
        var futureResult = api.checkResultUserHand(user);
        asyncReplyWithBody(context,"application/json", futureResult);
    }


    @Override
    public void deleteUser(Context context) throws HttpResponseException {
        GameLobbyApi api = getApi(context);
        var userId =  context.pathParam("{userId}");
        var futureResult = api.deleteUser(userId);
        asyncReplyWithoutBody(context,"application/json", futureResult);
        //asyncReplyWithBody(context,"application/json", futureResult);
    }

    @Override
    public void getValueCroupierCard(Context context) throws HttpResponseException {
        GameLobbyApi api = getApi(context);
        var userId =  context.pathParam("{userId}");
        var futureResult = api.getValueCroupierCard(userId);
        asyncReplyWithBody(context,"application/json", futureResult);
    }

    @Override
    public void resetActualPlay(Context context) throws HttpResponseException {
        GameLobbyApi api = getApi(context);
        var user =  context.bodyAsClass(String.class);
        var futureResult = api.resetActualPlay(user);
        asyncReplyWithBody(context,"application/json", futureResult);
    }

    @Override
    public void registerRoutes(Javalin app) {
        app.before(path("*"), Filters.ensureClientAcceptsMimeType("application", "json"));
        app.post(path("/over21"), this::isUserOver21);
        app.get(path("/{userId}"), this::allUsersAreReady);
        app.get(path("/getCroupierCard/{userId}"), this::getOneCroupierCard);
        app.post(path("/resulthand"), this::checkResultUserHand);
        app.delete(path("/{userId}"), this::deleteUser);
        app.get(path("/valuecard/{userId}"), this::getValueCroupierCard);
        app.post(path("/resetplay"), this::resetActualPlay);

    }
}
