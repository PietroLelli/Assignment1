package it.unibo.ds.presentation;

public class EmptyResponse extends Response<Void> {
    public EmptyResponse() {
    }

    public EmptyResponse(Status status, String message) {
        super(status, message);
    }
}
