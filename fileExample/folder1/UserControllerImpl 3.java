package org.example.users.impl;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpResponseException;
import org.example.AbstractController;
import org.example.Token;
import org.example.User;
import org.example.users.UserApi;
import org.example.users.UserController;
import org.example.utils.Filters;

public class UserControllerImpl extends AbstractController implements UserController {
    public UserControllerImpl(String path) {
        super(path);
    }
    private UserApi getApi(Context context) {
        return UserApi.of(getBlackjackInstance(context));
    }

    @Override
    public void postNewUser(Context context) throws HttpResponseException {
        UserApi api = getApi(context);
        var newUser =  context.bodyAsClass(String.class);
        var futureResult = api.registerUser(newUser);
        asyncReplyWithBody(context,"application/json", futureResult);
    }
    @Override
    public void addBetValue(Context context) throws HttpResponseException {
        UserApi api = getApi(context);
        var updateUser =  context.bodyAsClass(Token.class);
        var futureResult = api.addBetValue(updateUser);
        asyncReplyWithBody(context,"application/json", futureResult);
    }

    @Override
    public void hitUserCard(Context context) throws HttpResponseException {
        UserApi api = getApi(context);
        var user =  context.bodyAsClass(User.class);
        var futureResult = api.hitNewUserCard(user);
        asyncReplyWithBody(context,"application/json", futureResult);
    }

    @Override
    public void userReady(Context context) throws HttpResponseException {
        UserApi api = getApi(context);
        var user =  context.bodyAsClass(User.class);
        var futureResult = api.userIsReady(user);
        asyncReplyWithBody(context,"application/json", futureResult);
    }

    @Override
    public void checkCroupierCardIsExtracted(Context context) throws HttpResponseException {
        UserApi api = getApi(context);
        var userId =  context.pathParam("{userId}");
        var futureResult = api.checkCroupierCardIsExtracted(userId);
        asyncReplyWithBody(context,"application/json", futureResult);
    }

    @Override
    public void getUserBalance(Context context) throws HttpResponseException {
        UserApi api = getApi(context);
        var userId =  context.pathParam("{userId}");
        var futureResult = api.getUserBalance(userId);
        asyncReplyWithBody(context,"application/json", futureResult);
    }

    @Override
    public void getValueUserCard(Context context) throws HttpResponseException {
        UserApi api = getApi(context);
        var userId =   context.bodyAsClass(String.class);
        var futureResult = api.getValueUserCard(userId);
        asyncReplyWithBody(context,"application/json", futureResult);
    }

    @Override
    public void setTrueUserReadyStatus(Context context) throws HttpResponseException {
        UserApi api = getApi(context);
        var user =  context.bodyAsClass(String.class);
        var futureResult = api.setTrueUserReadyStatus(user);
        asyncReplyWithBody(context,"application/json", futureResult);
    }
    @Override
    public void setFalseUserReadyStatus(Context context) throws HttpResponseException {
        UserApi api = getApi(context);
        var user =  context.bodyAsClass(String.class);
        var futureResult = api.setFalseUserReadyStatus(user);
        asyncReplyWithBody(context,"application/json", futureResult);
    }

    @Override
    public void hasBlackjackUser(Context context) throws HttpResponseException {
        UserApi api = getApi(context);
        var userId =   context.pathParam("{userId}");
        var futureResult = api.hasBlackjackUser(userId);
        asyncReplyWithBody(context,"application/json", futureResult);
    }

    @Override
    public void isAlive(Context context) throws HttpResponseException {
        UserApi api = getApi(context);
        var st =  context.bodyAsClass(String.class);
        var futureResult = api.isAlive(st);
        asyncReplyWithoutBody(context,"application/json", futureResult);
    }

    @Override
    public void isUserInLobby(Context context) throws HttpResponseException {
        UserApi api = getApi(context);
        var userId =   context.pathParam("{userId}");
        var futureResult = api.isUserInLobby(userId);
        asyncReplyWithBody(context,"application/json", futureResult);
    }

    @Override
    public void registerRoutes(Javalin app) {
        app.before(path("*"), Filters.ensureClientAcceptsMimeType("application", "json"));
        app.post(path("/"), this::postNewUser);
        app.put(path("/balance"), this::addBetValue);
        app.post(path("/newcard"), this::hitUserCard);
        app.post(path("/userready"), this::userReady);
        app.get(path("/{userId}"), this::checkCroupierCardIsExtracted);
        app.post(path("/valuecard"), this::getValueUserCard);
        app.post(path("/settrueuserready"), this::setTrueUserReadyStatus);
        app.post(path("/setfalseuserready"), this::setFalseUserReadyStatus);
        app.get(path("/getbalance/{userId}"), this::getUserBalance);
        app.get(path("/userblackjack/{userId}"), this::hasBlackjackUser);
        app.put(path("/isalive"), this::isAlive);
        app.get(path("/isuserinlobby/{userId}"), this::isUserInLobby);

    }
}
