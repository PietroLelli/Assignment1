package org.example;

import io.javalin.http.Context;
import org.example.utils.Filters;

import java.util.concurrent.CompletableFuture;

public class AbstractController {
    private final String path;

    public AbstractController(String path) {
        this.path = path;
    }
    protected Blackjack getBlackjackInstance(Context context) {
        return Filters.getSingletonFromContext(Blackjack.class, context);
    }
    protected <T> void asyncReplyWithBody(Context ctx, String contentType, CompletableFuture<T> futureResult) {
        ctx.contentType(contentType);
        ctx.future(() -> futureResult.thenAccept(ctx::json));
    }
    public String path() {
        return path;
    }

    public String path(String subPath) {
        return path() + subPath;
    }
    protected void asyncReplyWithoutBody(Context ctx, String contentType, CompletableFuture<?> futureResult) {
        ctx.contentType(contentType);
        ctx.future(() -> futureResult);
    }
}
