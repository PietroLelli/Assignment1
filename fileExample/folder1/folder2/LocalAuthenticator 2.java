package it.unibo.ds.presentation;

import java.util.HashMap;
import java.util.Map;

public class LocalAuthenticator implements Authenticator {

    private final Map<String, User> usersByUsername = new HashMap<>();
    private final Map<String, User> usersByEmail = new HashMap<>();

    @Override
    public synchronized void register(User user) throws BadContentException, ConflictException {
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new BadContentException("Invalid username: " + user.getUsername());
        }
        if (user.getEmailAddresses().isEmpty()) {
            throw new BadContentException("No email provided for user: " + user.getUsername());
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new BadContentException("No password provided for user: " + user.getUsername());
        }
        if (usersByUsername.containsKey(user.getUsername())) {
            throw new ConflictException("Username already exists: " + user.getUsername());
        }
        for (var email : user.getEmailAddresses()) {
            if (usersByEmail.containsKey(email)) {
                throw new ConflictException("Email address already taken: " + email);
            }
        }
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }
        var toBeAdded = new User(user); // defensive copy
        usersByUsername.put(user.getUsername(), toBeAdded);
        for (var email : user.getEmailAddresses()) {
            usersByEmail.put(email, toBeAdded);
        }
    }

    @Override
    public synchronized Token authorize(Credentials credentials) throws BadContentException, WrongCredentialsException {
        if (credentials.getUserId() == null || credentials.getUserId().isBlank()) {
            throw new BadContentException("Missing user ID: " + credentials.getUserId());
        }
        if (credentials.getPassword() == null || credentials.getPassword().isBlank()) {
            throw new BadContentException("Missing password: " + credentials.getPassword());
        }
        String userId = credentials.getUserId();
        User user = usersByUsername.getOrDefault(userId, usersByEmail.get(userId));
        if (user == null) {
            throw new WrongCredentialsException("No such a user: " + userId);
        }
        if (!credentials.getPassword().equals(user.getPassword())) {
            throw new WrongCredentialsException("Wrong credentials for user: " + userId);
        }
        return new Token(user.getUsername(), user.getRole());
    }
}
