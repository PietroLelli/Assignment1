package org.example;

public interface Blackjack {
    User register(String nickname) throws ConflictException;
    void addBet(Token token) throws ConflictException;
    Card hitUserCard(User user) throws ConflictException;
    User userReady(User user) throws ConflictException;
    Card checkCroupierCard(String userId) throws MissingException, ConflictException;
    Boolean isOver21(User user) throws ConflictException;
    Boolean allUsersReady(String userId) throws MissingException, ConflictException;
    Card getCroupierCard(String userId) throws MissingException, ConflictException;
    HandResult resultUserHand(User user) throws ConflictException;
    void removeUser(String userId) throws  ConflictException;
    int getBalance(String userId) throws MissingException, ConflictException;
    int getValueCroupierCard(String userId) throws MissingException, ConflictException;
    int getValueUserCard(String userId) throws MissingException, ConflictException;
    Boolean resetPlay(String nickname) throws MissingException, ConflictException;
    Boolean setTrueUserReadyStatus(String nickname) throws ConflictException;
    Boolean setFalseUserReadyStatus(String nickname) throws ConflictException;
    Boolean hasBlackjackUser(String userId) throws MissingException, ConflictException;
    void isAlive(String userId) throws MissingException, ConflictException;
    Boolean isUserInLobby(String userId) throws MissingException, ConflictException;

}
