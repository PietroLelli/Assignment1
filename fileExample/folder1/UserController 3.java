package org.example.users;

import io.javalin.http.Context;
import io.javalin.http.HttpResponseException;

import io.javalin.openapi.*;
import org.example.Controller;
import org.example.BlackjackService;
import org.example.User;
import org.example.users.impl.UserControllerImpl;

public interface UserController extends Controller {

    @OpenApi(
            operationId = "UserApi::registerUser",
            path = BlackjackService.BASE_URL + "/users",
            methods = {HttpMethod.POST},
            tags = {"users"},
            description = "Registers a novel user out of the provided user data",
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
                            description = "The username of the newly created user",
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
    void postNewUser(Context context) throws HttpResponseException;

    @OpenApi(
            operationId = "UserApi::addBetValue",
            path = BlackjackService.BASE_URL + "/users/balance",
            methods = {HttpMethod.PUT},
            tags = {"users"},
            description = "Update balance value",
            pathParams = {
                    @OpenApiParam(
                            name = "userId",
                            type = String.class,
                            description = "UserId of the user whose data is being requested",
                            required = true
                    )
            },
            requestBody = @OpenApiRequestBody(
                    description = "The updated balance",
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
                            description = "The provided identifier corresponds to a user, which is thus updated, and the new username is returned",
                            content = {
                                    @OpenApiContent(
                                            from = String.class,
                                            mimeType = ContentType.JSON
                                    )
                            }
                    ),
                    @OpenApiResponse(
                            status = "400",
                            description = "Bad request: some field is missing or invalid in the provided user identifier or data"
                    ),
                    @OpenApiResponse(
                            status = "404",
                            description = "Not found: the provided identifier corresponds to no known user"
                    ),
                    @OpenApiResponse(
                            status = "404",
                            description = "Conflict: some identifier in the new user data has already been taken"
                    )
            }
    )
    void addBetValue(Context context) throws HttpResponseException;

    @OpenApi(
            operationId = "UserApi::hitNewCard",
            path = BlackjackService.BASE_URL + "/users/newcard",
            methods = {HttpMethod.POST},
            tags = {"users"},
            description = "hit a new card",
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
                            description = "New card extracted",
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
                            description = "Conflict: nickname doesn't exist"
                    )
            }
    )
    void hitUserCard(Context context) throws HttpResponseException;

    @OpenApi(
            operationId = "UserApi::userIsReady",
            path = BlackjackService.BASE_URL + "/users/userready",
            methods = {HttpMethod.POST},
            tags = {"users"},
            description = "User is ready",
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
                            description = "User is ready",
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
                            description = "Conflict: nickname doesn't exist"
                    )
            }
    )
    void userReady(Context context) throws HttpResponseException;

    @OpenApi(
            operationId = "UserApi::checkCroupierCardIsExtracted",
            path = BlackjackService.BASE_URL + "/users/{userId}",
            methods = {HttpMethod.GET},
            tags = {"users"},
            description = "Check if Croupier card is extracted",
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
    void checkCroupierCardIsExtracted(Context context) throws HttpResponseException;
    @OpenApi(
            operationId = "UserApi::getUserBalance",
            path = BlackjackService.BASE_URL + "/users/getbalance/{userId}",
            methods = {HttpMethod.GET},
            tags = {"users"},
            description = "Get User's Balance",
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
    void getUserBalance(Context context) throws HttpResponseException;
    @OpenApi(
            operationId = "UserApi::getValueUserCard",
            path = BlackjackService.BASE_URL + "/users/valuecard",
            methods = {HttpMethod.POST},
            tags = {"users"},
            description = "Gets the user's cards value",
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
                            description = "Get user's cards value",
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
                            description = "Conflict: nickname doesn't exist"
                    )
            }
    )
    void getValueUserCard(Context context) throws HttpResponseException;
    @OpenApi(
            operationId = "UserApi::setTrueUserReadyStatus",
            path = BlackjackService.BASE_URL + "/users/setuserready",
            methods = {HttpMethod.POST},
            tags = {"users"},
            description = "Set User's ready param",
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
                            description = "User is Ready",
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
                            description = "Conflict: nickname doesn't exist"
                    )
            }
    )
    void setTrueUserReadyStatus(Context context) throws HttpResponseException;
    @OpenApi(
            operationId = "UserApi::setFalseUserReadyStatus",
            path = BlackjackService.BASE_URL + "/users/setuserready",
            methods = {HttpMethod.POST},
            tags = {"users"},
            description = "Set False User's Ready status",
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
                            description = "Users isn't ready",
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
                            description = "Conflict: nickname doesn't exist"
                    )
            }
    )
    void setFalseUserReadyStatus(Context context) throws HttpResponseException;

    @OpenApi(
            operationId = "UserApi::hasBlackjackUser",
            path = BlackjackService.BASE_URL + "/users/userblackjack/{userId}",
            methods = {HttpMethod.GET},
            tags = {"users"},
            description = "Check if User has Blackjack",
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
    void hasBlackjackUser(Context context) throws HttpResponseException;
    @OpenApi(
            operationId = "UserApi::isAlive",
            path = BlackjackService.BASE_URL + "/users/isalive/{userId}",
            methods = {HttpMethod.PUT},
            tags = {"users"},
            description = "Check if User is alive",
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
    void isAlive(Context context) throws HttpResponseException;
    @OpenApi(
            operationId = "UserApi::isUserInLobby",
            path = BlackjackService.BASE_URL + "/users/isuserinlobby/{userId}",
            methods = {HttpMethod.GET},
            tags = {"users"},
            description = "Check if User is in lobby",
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
    void isUserInLobby(Context context) throws HttpResponseException;

    static UserController of(String root) {
        return new UserControllerImpl(root);
    }
}
