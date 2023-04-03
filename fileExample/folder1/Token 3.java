package org.example;

import java.util.Objects;

public class Token {
    private User user;

    public String getBet() {
        return bet;
    }

    public void setBet(String bet) {
        this.bet = bet;
    }

    private String bet;

    public Token(User user, String bet) {
        this.user = user;
        this.bet = bet;
    }
    public Token(User user) {//TODO da controllare se è davvero necessario questo costruttore
        this.user = user;
        this.bet = String.valueOf(user.getBalance());
    }
    public Token(Token token) {
        this.user = token.getUser();
        this.user.setBalance(token.getUser().getBalance());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(user, token.user) && bet == token.bet;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, bet);
    }

    @Override
    public String toString() {
        return "Token{" +
                "user='" + user+ '\'' +
                ", balance=" + bet +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
