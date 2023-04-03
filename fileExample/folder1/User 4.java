package org.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class User {
    private String nickname;
    private int balance = 0;
    private int valueUserCard=0;
    private List<Card> cards = new ArrayList<>();
    private boolean isReady = false;
    private boolean isBlackjack = false;
    private boolean isAlive = false;
    private LocalTime lastSignalIsAlive = null;

    public int getValueUserCard() {
        return valueUserCard;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Card c){
        cards.add(c);
        if (c.getValue() == CardValue.ACE){
            if(valueUserCard + 11 <= 21){
                valueUserCard +=11;
            }else{
                valueUserCard +=1;
            }
        }else{
            valueUserCard += c.getNumberValue();
        }

    }
    public int getBalance() {
        return balance;
    }
    public void resetCards(){
        valueUserCard=0;
        cards = new ArrayList<>();

    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
    public void addBalance(int b){
        this.balance += b;
    }

    //private Balance balance = new Balance(0);

    public String getNickname() {
        return nickname;
    }

    public User(String nickname) {
        this.nickname = nickname;
        balance = 0;
        this.isAlive = true;
    }
    public User() {
        this.nickname = "null";
        balance = 0;
        this.isAlive = true;
    }
    public User(User user) {
        this.nickname = user.nickname;
        this.balance = user.balance;
        this.isAlive = true;
    }
    public User(String nickname, int balance) {
        this.nickname = nickname;
        this.balance = balance;
        this.isAlive = true;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(nickname, user.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname);
    }

    @Override
    public String toString() {
        return "User{" +
                "nickname='" + nickname +'\''+ '}';
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public boolean isBlackjack() {
        return isBlackjack;
    }

    public void setBlackjack(boolean blackjack) {
        isBlackjack = blackjack;
    }
    public void isAlive(){
        this.isAlive = true;
        this.lastSignalIsAlive = LocalTime.now();
    }
    public LocalTime getLastSignalIsAlive(){
        return lastSignalIsAlive;
    }
}
