package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameLobby {
    private Map<User, Integer> nextCardToShow = new HashMap<>();
    private int idLobby;
    private int valueCroupierCard = 0;
    Map<String,User> users = new HashMap<>();
    private List<Card> croupierCards = new ArrayList<>();
    private Map<User, Boolean> userReady = new HashMap<>();
    private Map<User, Integer> bet = new HashMap<>();
    private int countUserReady=0;
    private boolean statusOpenNewUsers = true;
    private boolean isBlackjack = false;

    public void resetLobby(){
        croupierCards = new ArrayList<>();
        bet = new HashMap<>();
        for (User u : users.values()){
            u.resetCards();
            nextCardToShow.replace(u, 0);
            u.setBlackjack(false);
        }
        resetUsersReady();
        croupierCards.add(new Card(Suit.EMPTY, CardValue.EMPTY));
        valueCroupierCard=0;
        countUserReady=0;
        setBlackjack(false);

    }
    public int getCountUserReady(){
        return countUserReady;
    }
    public void incCountUserReady(){
        countUserReady++;
    }
    public void decCountUserReady(User user){
        if(users.get(user.getNickname()).isReady()){
            countUserReady--;
            users.get(user.getNickname()).setReady(false);
        }

    }
    public void addUserBet(User u, int b){
        bet.put(u, b);
    }
    public int getSingleBet(User u ){
        return bet.get(u);
    }
    private User getUserFromId(String userId){
        return users.get(userId);
    }
    public boolean removeUserFromLobby(String u){

        if(!users.containsKey(u)){
            return false;
        }

        userReady.remove(getUserFromId(u));
        bet.remove(getUserFromId(u));
        nextCardToShow.remove(getUserFromId(u));
        nextCardToShow.remove(getUserFromId(u));
        users.remove(u);
        return true;
    }
    public Card getNextCardToShow(String userId){
        for (User u : nextCardToShow.keySet()){
            if(u.getNickname().equals(userId)){
                int indexNextCard = nextCardToShow.get(u);
                if (croupierCards.size() > indexNextCard){
                    nextCardToShow.replace(u, indexNextCard+1);
                    return new Card(croupierCards.get(indexNextCard));
                }

            }
        }
        return new Card(Suit.EMPTY, CardValue.EMPTY);
    }

    public int getValueCroupierCard() {
        return valueCroupierCard;
    }
    public List<Card> getCroupierCards() {
        return croupierCards;
    }
    public void resetCroupierCars(){
        croupierCards = new ArrayList<>();
    }
    public void addCard(Card c){
        if(croupierCards.size()>0) {
            if (croupierCards.get(0).getValue() == CardValue.EMPTY && croupierCards.get(0).getSuit() == Suit.EMPTY) {
                croupierCards.remove(0);
            }
        }
        croupierCards.add(c);

        if (c.getValue() == CardValue.ACE){
            if(valueCroupierCard + 11 <= 21){
                valueCroupierCard +=11;
            }else{
                valueCroupierCard +=1;
            }
        }else{
            valueCroupierCard += c.getNumberValue();
        }
    }
    public void setUserReady(User user) {
        this.userReady.replace(user, true);
        if (!users.get(user.getNickname()).isReady()){
            incCountUserReady();
            users.get(user.getNickname()).setReady(true);
        }

    }
    public void resetUsersReady(){
        for (User u : this.userReady.keySet()){
            this.userReady.replace(u,false);
            u.setReady(false);
        }
        countUserReady=0;

    }
    public boolean allUserReady(){
        if (userReady.containsValue(false)){
            return false;
        }
        return true;
    }

    public List<User> getUsers(){
        return new ArrayList<>(this.users.values());
    }
    public User getUser(String nickname){
        return users.get(nickname);
    }
    public int getIdLobby(){
        return this.idLobby;
    }

    public void addUser(User user){
        users.put(user.getNickname(), user);
        userReady.put(user, false);
        nextCardToShow.put(user,0);
    }
    public GameLobby(int idLobby) {
        this.idLobby = idLobby;
        croupierCards.add(new Card(Suit.EMPTY, CardValue.EMPTY));

    }
    public boolean isStatusOpenNewUsers() {
        return statusOpenNewUsers;
    }

    public void setStatusOpenNewUsers(boolean statusOpenNewUsers) {
        this.statusOpenNewUsers = statusOpenNewUsers;
    }

    public boolean isBlackjack() {
        return isBlackjack;
    }

    public void setBlackjack(boolean blackjack) {
        isBlackjack = blackjack;
    }
}
