package it.unibo.ds.presentation;

public class AuthorizeRequest extends Request<Credentials> {
    public AuthorizeRequest() {
        this(null);
    }

    public AuthorizeRequest(Credentials argument) {
        super("authorize", argument);
    }
}
