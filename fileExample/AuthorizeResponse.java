package it.unibo.ds.presentation;

public class AuthorizeResponse extends Response<Token> {
    public AuthorizeResponse() {
    }

    public AuthorizeResponse(Status status, String message, Token result) {
        super(status, message, result);
    }
}
