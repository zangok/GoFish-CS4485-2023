package com.example.gofish;

public class Card {
    private String rank;
    private String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public boolean equals(Card otherCard) {
        return this.rank.equals(otherCard.getRank()) && this.suit.equals(otherCard.getSuit());
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}

