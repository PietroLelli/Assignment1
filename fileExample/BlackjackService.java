package org.example;

import io.javalin.Javalin;
//import org.example.balance.BalanceController;
import org.example.gameLobby.GameLobbyController;
import org.example.users.UserController;
import org.example.utils.Filters;
import org.example.utils.Plugins;

import java.util.Timer;

public class BlackjackService {
    private static final int DEFAULT_PORT = 10000;
    private static final String API_VERSION = "0.1.0";
    public static final String BASE_URL = "/blackjack/v" + API_VERSION;
    private final Javalin server;
    private final int port;
    public void start() {
        server.start(port);
    }
    public void stop() {
        server.stop();
    }
    private final Blackjack localBlackjack = new LocalBlackjack();
    private static String path(String subPath) {
        return BASE_URL + subPath;
    }
    private static boolean test = false;
    public BlackjackService(int port, boolean test) {
        this.port = port;
        server = Javalin.create(config -> {
            config.plugins.enableDevLogging();
            config.jsonMapper(new JavalinGsonAdapter(GsonUtils.createGson()));
            config.plugins.register(Plugins.openApiPlugin(API_VERSION, "/doc"));
            config.plugins.register(Plugins.swaggerPlugin("/doc", "/ui"));
            config.plugins.register(Plugins.routeOverviewPlugin("/routes"));
        });

        server.before(path("/*"), Filters.putSingletonInContext(Blackjack.class, localBlackjack));

        UserController.of(path("/users")).registerRoutes(server);
        GameLobbyController.of(path("/lobby")).registerRoutes(server);
        this.test = test;

        if (!test) {
            Timer timer = new Timer();
            timer.schedule(new LocalBlackjack.CheckUserIsAlive(), 0, 10000);
        }

    }
    public static void main(String[] args) {
        new BlackjackService(args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT, test).start();

    }
}