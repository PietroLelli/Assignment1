package it.unibo.ds.presentation;

public interface Authenticator {
    void register(User user) throws BadContentException, ConflictException;

    Token authorize(Credentials credentials) throws BadContentException, WrongCredentialsException;
}
