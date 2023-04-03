package org.example;

public class Card {
    private Suit suit;
    private CardValue value;
    public int getNumberValue(){
        switch(value.ordinal()) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            case 4:
                return 5;
            case 5:
                return 6;
            case 6:
                return 7;
            case 7:
                return 8;
            case 8:
                return 9;
            case 9:
                return 10;
            case 10:
                return 10;
            case 11:
                return 10;
            case 12:
                return 10;
        }
        return -1;
    }
    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public CardValue getValue() {
        return value;
    }

    public void setValue(CardValue value) {
        this.value = value;
    }


    public Card(Suit s, CardValue v){
        this.suit = s;
        this.value = v;
    }
    public Card(Card card){
        this.value = card.getValue();
        this.suit = card.getSuit();
    }

    public Card(int s, int v){
        switch(s) {
            case 0:
                this.suit = Suit.HEARTS;
                break;
            case 1:
                this.suit = Suit.CLUBS;
                break;
            case 2:
                this.suit = Suit.SPADES;
                break;
            case 3:
                this.suit = Suit.DIAMONDS;
                break;
            case 4:
                this.suit = Suit.EMPTY;
                break;
        }
        switch(v) {
            case 0:
                this.value = CardValue.ACE;
                break;
            case 1:
                this.value = CardValue.TWO;
                break;
            case 2:
                this.value = CardValue.THREE;
                break;
            case 3:
                this.value = CardValue.FOUR;
                break;
            case 4:
                this.value = CardValue.FIVE;
                break;
            case 5:
                this.value = CardValue.SIX;
                break;
            case 6:
                this.value = CardValue.SEVEN;
                break;
            case 7:
                this.value = CardValue.EIGHT;
                break;
            case 8:
                this.value = CardValue.NINE;
                break;
            case 9:
                this.value = CardValue.TEN;
                break;
            case 10:
                this.value = CardValue.JACK;
                break;
            case 11:
                this.value = CardValue.QUEEN;
                break;
            case 12:
                this.value = CardValue.KING;
                break;
            case 13:
                this.value = CardValue.EMPTY;
                break;
        }


    }


}
