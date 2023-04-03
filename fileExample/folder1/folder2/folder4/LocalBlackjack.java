package org.example;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import static java.time.temporal.ChronoUnit.SECONDS;

public class LocalBlackjack implements Blackjack {

    private static final List<GameLobby> lobbies = new ArrayList<>();
    static final int INITIALBALANCE = 30;


    @Override
    public synchronized User register(String nickname) throws ConflictException {
        if (usernameAlreadyExist(nickname)){
            throw new ConflictException();
        }
        User newUser = new User(nickname);
        newUser.setBalance(INITIALBALANCE);
        if(lobbies.isEmpty()){//Nessuna lobby è presente, lo user viene aggiunto in una nuova
            lobbies.add(new GameLobby(1));
            lobbies.get(0).addUser(newUser);
            return newUser;
        }
        for (GameLobby lobby : lobbies) {//lobby gia presente e non piena(<3)
            if(lobby.getUsers().size()<3 && lobby.isStatusOpenNewUsers()){
                lobby.addUser(newUser);
                return newUser;
            }
        }
        lobbies.get(addGameLobby()-1).addUser(newUser);//user aggiunto in una nuova lobby essendo piene quelle presenti
        return newUser;
    }


    private int addGameLobby(){
        GameLobby newGameLobby = new GameLobby(lobbies.size()+1);
        lobbies.add(newGameLobby);
        return newGameLobby.getIdLobby();
    }

    private User getUserFromLobby(User user) throws ConflictException{
        for (GameLobby lobby : lobbies) {
            for (User u : lobby.getUsers()) {
                if (u.getNickname().equals(user.getNickname())){
                    return u;
                }
            }
        }
        throw new ConflictException();
    }
    private GameLobby getLobbyFromUser(User user){
        for (GameLobby lobby : lobbies) {
            for (User u : lobby.getUsers()) {
                if (u.getNickname().equals(user.getNickname())){
                    return lobby;
                }
            }
        }
        return null;

    }

    private GameLobby getLobbyFromUserId(String userId){
        for (GameLobby lobby : lobbies) {
            for (User u : lobby.getUsers()) {
                if (u.getNickname().equals(userId)){
                    return lobby;
                }
            }
        }
        return null;
    }
    private User getUserFromUser(User user){
        for (GameLobby lobby : lobbies) {
            for (User u : lobby.getUsers()) {
                if (u.getNickname().equals(user.getNickname())){
                    return u;
                }
            }
        }
        return null;
    }

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            if(Integer.parseInt(str) > 0){
                return true;
            }
            return false;
        } catch(NumberFormatException e){
            return false;
        }
    }
    @Override
    public synchronized void addBet(Token token) throws ConflictException {
        if (isNumeric(token.getBet())){
            if(getUserFromUser(token.getUser()).getBalance() < Integer.parseInt(token.getBet())){
                throw new ConflictException();
            }
            getLobbyFromUser(token.getUser()).addUserBet(token.getUser(), Integer.parseInt(token.getBet()));
            getLobbyFromUser(token.getUser()).setStatusOpenNewUsers(false);

        }else {
            throw new ConflictException();
        }


    }
    private Card getRandomCard(){
        Suit suit = Suit.values()[new Random().nextInt(Suit.values().length)];
        while(suit == Suit.EMPTY){
            suit = Suit.values()[new Random().nextInt(Suit.values().length)];
        }

        CardValue value = CardValue.values()[new Random().nextInt(CardValue.values().length)];
        while (value == CardValue.EMPTY){
            value = CardValue.values()[new Random().nextInt(CardValue.values().length)];
        }
        return new Card(suit, value);
    }
    private boolean isUserBlackjack(User u){
        if(getLobbyFromUser(u).getUser(u.getNickname()).getCards().size() == 2){
            if ((getLobbyFromUser(u).getUser(u.getNickname()).getCards().get(0).getValue() == CardValue.ACE && getLobbyFromUser(u).getUser(u.getNickname()).getCards().get(1).getNumberValue() == 10) ||
                    (getLobbyFromUser(u).getUser(u.getNickname()).getCards().get(1).getValue() == CardValue.ACE && getLobbyFromUser(u).getUser(u.getNickname()).getCards().get(0).getNumberValue() == 10)){
                return true;
            }
        }
        return false;
    }
    private boolean isCroupierBlackjack(GameLobby lobby){
        if(lobby.getCroupierCards().size() == 2){
            if ((lobby.getCroupierCards().get(0).getValue() == CardValue.ACE && lobby.getCroupierCards().get(1).getNumberValue() == 10) ||
                    (lobby.getCroupierCards().get(1).getValue() == CardValue.ACE && lobby.getCroupierCards().get(0).getNumberValue() == 10)){
                return true;
            }
        }
        return false;
    }
    @Override
    public synchronized Card hitUserCard(User u) throws ConflictException {
        Card cardDrawn = getRandomCard();
        getUserFromLobby(u).addCard(cardDrawn);
        if(isUserBlackjack(u)){
            getUserFromLobby(u).setBlackjack(true);
        }
        return cardDrawn;

    }

    @Override
    public synchronized User userReady(User user) {
        getLobbyFromUser(user).setUserReady(user);
        if (getLobbyFromUser(user).allUserReady()){
            if (getLobbyFromUser(user).getCroupierCards().get(0).getValue() == CardValue.EMPTY){
                getLobbyFromUser(user).resetCroupierCars();
                getLobbyFromUser(user).resetUsersReady();
                hitCroupierCard((getLobbyFromUser(user)));
                getLobbyFromUser(user).resetUsersReady();
            }
        }
        return user;

    }
    private void startCroupierTurn(User user) {
        hitCroupierCard(getLobbyFromUser(user));
        while(getLobbyFromUser(user).getValueCroupierCard()<17){
            hitCroupierCard((getLobbyFromUser(user)));
        }
    }

    @Override
    public Card checkCroupierCard(String userId) {
        return getLobbyFromUserId(userId).getCroupierCards().get(0);
    }


    @Override
    public Boolean isOver21(User user) throws ConflictException {
        return getUserFromLobby(user).getValueUserCard() > 21 ? true : false;
    }

    @Override
    public Boolean allUsersReady(String userId){
        return getLobbyFromUserId(userId).allUserReady();
    }

    @Override
    public Card getCroupierCard(String userId) {
        return getLobbyFromUserId(userId).getNextCardToShow(userId);
    }

    @Override
    public HandResult resultUserHand(User user)  {
        int valueCrupierCard = getLobbyFromUser(user).getValueCroupierCard();
        int valueUserCard = getLobbyFromUser(user).getUser(user.getNickname()).getValueUserCard();
        HandResult result = null;
        User actualUser = getUserFromUser(user);
        if(actualUser.isBlackjack()){
            result = HandResult.WIN;
            getLobbyFromUser(user).getUser(user.getNickname()).addBalance((getLobbyFromUser(user).getSingleBet(user)/2)*3);
        } else if (getLobbyFromUser(user).isBlackjack()) {
            result = HandResult.LOST;
            getLobbyFromUser(user).getUser(user.getNickname()).addBalance(-getLobbyFromUser(user).getSingleBet(user));
        }
        else if (valueUserCard > 21 || (valueCrupierCard > valueUserCard) && (valueCrupierCard <= 21)  ) {
            result = HandResult.LOST;
            getLobbyFromUser(user).getUser(user.getNickname()).addBalance(-getLobbyFromUser(user).getSingleBet(user));
        }else if (valueCrupierCard > 21 || valueCrupierCard < valueUserCard){
            result = HandResult.WIN;
            getLobbyFromUser(user).getUser(user.getNickname()).addBalance(getLobbyFromUser(user).getSingleBet(user));
        }else{
            result = HandResult.PUSH;
        }

        return result;
    }

    private boolean usernameAlreadyExist(String nickname){
        if(getLobbyFromUserId(nickname) == null){
            return false;
        }
        return true;
    }


    @Override
    public synchronized void removeUser(String userId)  {
        if(!getLobbyFromUserId(userId).removeUserFromLobby(userId)){
            throw new IllegalArgumentException("Invalid user, doesn't exist: " + userId);
        }
    }

    @Override
    public int getBalance(String userId) {
        return getLobbyFromUserId(userId).getUser(userId).getBalance();
    }

    @Override
    public int getValueCroupierCard(String userId) {
        return getLobbyFromUserId(userId).getValueCroupierCard();
    }

    @Override
    public int getValueUserCard(String userId) throws MissingException, ConflictException {
        return getLobbyFromUserId(userId).getUser(userId).getValueUserCard();
    }

    @Override
    public Boolean resetPlay(String nickname){
        //resetPlay(getLobbyFromUserId(nickname).getUser(nickname));
        getLobbyFromUserId(nickname).resetLobby();
        getLobbyFromUserId(nickname).setStatusOpenNewUsers(true);
        return true;
    }

    @Override
    public Boolean setTrueUserReadyStatus(String nickname) {
        getLobbyFromUserId(nickname).setUserReady(getLobbyFromUserId(nickname).getUser(nickname));
        if (getLobbyFromUserId(nickname).allUserReady()) {
            if (getLobbyFromUserId(nickname).getCroupierCards().size() == 1 && getLobbyFromUserId(nickname).getCroupierCards().get(0).getValue() != CardValue.EMPTY) {
                startCroupierTurn(getLobbyFromUserId(nickname).getUser(nickname));
            }
        }
        return true;

    }

    @Override
    public Boolean setFalseUserReadyStatus(String nickname){
        getLobbyFromUserId(nickname).decCountUserReady(getLobbyFromUserId(nickname).getUser(nickname));
        if(getLobbyFromUserId(nickname).getCountUserReady() ==0){
            getLobbyFromUserId(nickname).resetUsersReady();
        }
        return true;
    }

    @Override
    public Boolean hasBlackjackUser(String userId){
        return getLobbyFromUserId(userId).getUser(userId).isBlackjack();
    }

    @Override
    public void isAlive(String userId){
        getLobbyFromUserId(userId).getUser(userId).isAlive();
    }

    @Override
    public Boolean isUserInLobby(String userId){

        for (GameLobby lobby : lobbies){
            for(User user : lobby.getUsers()){
                if( user.getNickname().equals(userId)){
                    return true;
                }
            }
        }
        return false;
    }



    private Card hitCroupierCard(GameLobby lobby){
        Card card = getRandomCard();
        lobby.addCard(card);
        if(isCroupierBlackjack(lobby)){
            lobby.setBlackjack(true);
        }
        return card;
    }

    static class CheckUserIsAlive extends TimerTask {

        @Override
        public void run() {
            LocalTime timeNow = LocalTime.now();
            for (GameLobby lobby : lobbies){
                for(User user : lobby.getUsers()){
                    if( Math.abs(Duration.between(timeNow, user.getLastSignalIsAlive()).toSeconds()) > 30){
                        lobby.removeUserFromLobby(user.getNickname());
                    }
                }
            }
        }
    }

}
