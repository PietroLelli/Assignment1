package org.example.gameLobby;

import io.javalin.http.Context;
import io.javalin.http.HttpResponseException;
import io.javalin.openapi.*;
import org.example.BlackjackService;
import org.example.Controller;
import org.example.User;
import org.example.gameLobby.impl.GameLobbyControllerImpl;

public interface GameLobbyController extends Controller {


    @OpenApi(
            operationId = "UserApi::isUserOver21",
            path = BlackjackService.BASE_URL + "/lobby/over21",
            methods = {HttpMethod.POST},
            tags = {"lobby"},
            description = "Check if User's card value is over 21",
            requestBody = @OpenApiRequestBody(
                    description = "The user's data",
                    required = true,
                    content = {
                            @OpenApiContent(
                                    from = User.class,
                                    mimeType = ContentType.JSON
                            )
                    }
            ),
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "Response",
                            content = {
                                    @OpenApiContent(
                                            from = User[].class,
                                            mimeType = ContentType.JSON
                                    )
                            }
                    ),
                    @OpenApiResponse(
                            status = "400",
                            description = "Bad request: some field is missing or invalid in the provided user data"
                    ),
                    @OpenApiResponse(
                            status = "409",
                            description = "Conflict: some identifier (username or email address) of the provided user data has already been taken"
                    )
            }
    )
    void isUserOver21(Context context) throws HttpResponseException;

    @OpenApi(
            operationId = "UserApi::allUsersAreReady",
            path = BlackjackService.BASE_URL + "/lobby/{userId}",
            methods = {HttpMethod.GET},
            tags = {"users"},
            description = "Check if all user are Ready",
            pathParams = {
                    @OpenApiParam(
                            name = "userId",
                            type = String.class,
                            description = "Nickname of the user whose data is being requested",
                            required = true
                    )
            },
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "The provided identifier corresponds to a user, whose data is thus returned",
                            content = {
                                    @OpenApiContent(
                                            from = User.class,
                                            mimeType = ContentType.JSON
                                    )
                            }
                    ),
                    @OpenApiResponse(
                            status = "400",
                            description = "Bad request: some field is missing or invalid in the provided user identifier"
                    ),
                    @OpenApiResponse(
                            status = "404",
                            description = "Not found: the provided identifier corresponds to no known user"
                    )
            }
    )
    void allUsersAreReady(Context context) throws HttpResponseException;

    @OpenApi(
            operationId = "UserApi::getOneCroupierCard",
            path = BlackjackService.BASE_URL + "/lobby/{userId}",
            methods = {HttpMethod.GET},
            tags = {"users"},
            description = "Get a Croupier's Card",
            pathParams = {
                    @OpenApiParam(
                            name = "userId",
                            type = String.class,
                            description = "Nickname of the user whose data is being requested",
                            required = true
                    )
            },
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "The provided identifier corresponds to a user, whose data is thus returned",
                            content = {
                                    @OpenApiContent(
                                            from = User.class,
                                            mimeType = ContentType.JSON
                                    )
                            }
                    ),
                    @OpenApiResponse(
                            status = "400",
                            description = "Bad request: some field is missing or invalid in the provided user identifier"
                    ),
                    @OpenApiResponse(
                            status = "404",
                            description = "Not found: the provided identifier corresponds to no known user"
                    )
            }
    )
    void getOneCroupierCard(Context context) throws HttpResponseException;


    @OpenApi(
            operationId = "UserApi::checkResultUserHand",
            path = BlackjackService.BASE_URL + "/lobby/resulthand",
            methods = {HttpMethod.POST},
            tags = {"lobby"},
            description = "Chech User's hand result",
            requestBody = @OpenApiRequestBody(
                    description = "The user's data",
                    required = true,
                    content = {
                            @OpenApiContent(
                                    from = User.class,
                                    mimeType = ContentType.JSON
                            )
                    }
            ),
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "Hand result",
                            content = {
                                    @OpenApiContent(
                                            from = User[].class,
                                            mimeType = ContentType.JSON
                                    )
                            }
                    ),
                    @OpenApiResponse(
                            status = "400",
                            description = "Bad request: some field is missing or invalid in the provided user data"
                    ),
                    @OpenApiResponse(
                            status = "409",
                            description = "Conflict: some identifier (username or email address) of the provided user data has already been taken"
                    )
            }
    )
    void checkResultUserHand(Context context) throws HttpResponseException;


    @OpenApi(
            operationId = "UserApi::deleteUser",
            path = BlackjackService.BASE_URL + "/lobby/{userId}",
            methods = {HttpMethod.DELETE},
            tags = {"lobby"},
            description = "Delete User form GameLobby",
            pathParams = {
                    @OpenApiParam(
                            name = "userId",
                            type = String.class,
                            description = "Nickname of the user whose data is being requested",
                            required = true
                    )
            },
            responses = {
                    @OpenApiResponse(
                            status = "201",
                            description = "The provided identifier corresponds to a user, which is thus removed. Nothing is returned"
                    ),
                    @OpenApiResponse(
                            status = "400",
                            description = "Bad request: some field is missing or invalid in the provided user identifier"
                    ),
                    @OpenApiResponse(
                            status = "404",
                            description = "Not found: the provided identifier corresponds to no known user"
                    )
            }
    )
    void deleteUser(Context context) throws HttpResponseException;

    @OpenApi(
            operationId = "UserApi::getValueCroupierCard",
            path = BlackjackService.BASE_URL + "/lobby/{userId}",
            methods = {HttpMethod.GET},
            tags = {"users"},
            description = "Get Croupier's card value",
            pathParams = {
                    @OpenApiParam(
                            name = "userId",
                            type = String.class,
                            description = "Nickname of the user whose data is being requested",
                            required = true
                    )
            },
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "The provided identifier corresponds to a user, whose data is thus returned",
                            content = {
                                    @OpenApiContent(
                                            from = User.class,
                                            mimeType = ContentType.JSON
                                    )
                            }
                    ),
                    @OpenApiResponse(
                            status = "400",
                            description = "Bad request: some field is missing or invalid in the provided user identifier"
                    ),
                    @OpenApiResponse(
                            status = "404",
                            description = "Not found: the provided identifier corresponds to no known user"
                    )
            }
    )
    void getValueCroupierCard(Context context) throws HttpResponseException;
    @OpenApi(
            operationId = "UserApi::resetActualPlay",
            path = BlackjackService.BASE_URL + "/lobby/resetplay",
            methods = {HttpMethod.POST},
            tags = {"lobby"},
            description = "Reset play",
            requestBody = @OpenApiRequestBody(
                    description = "The user's data",
                    required = true,
                    content = {
                            @OpenApiContent(
                                    from = User.class,
                                    mimeType = ContentType.JSON
                            )
                    }
            ),
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "Reset play confermed",
                            content = {
                                    @OpenApiContent(
                                            from = User[].class,
                                            mimeType = ContentType.JSON
                                    )
                            }
                    ),
                    @OpenApiResponse(
                            status = "400",
                            description = "Bad request: some field is missing or invalid in the provided user data"
                    ),
                    @OpenApiResponse(
                            status = "409",
                            description = "Conflict: some identifier (username or email address) of the provided user data has already been taken"
                    )
            }
    )
    void resetActualPlay(Context context) throws HttpResponseException;

    static GameLobbyController of(String root) {
        return new GameLobbyControllerImpl(root);
    }
}
