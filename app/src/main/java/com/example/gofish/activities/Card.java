package com.example.gofish.activities;

public class Card {
    //Instance variables to store the rank and suit of the card
    private String rank;
    private String suit;
    
    //Constructor for creating a Card with a specific rank and suit
    public Card(String rank, String suit) {
        //Error handling for invalid rank or suit
        if(rank == null) {
            throw new IllegalArgumentException("Card rank cannot be null.");
        }
        if(suit == null) {
            throw new IllegalArgumentException("Card suit cannot be null.");
        }
        
        //Initialize the Card
        this.rank = rank;
        this.suit = suit;
    }
    
    //Get the rank of the card
    public String getRank() {
        return rank;
    }
    
    //Update the rank of the card
    public void setRank(String rank) {
        this.rank = rank;
    }
    
    //Get the suit of the card
    public String getSuit() {
        return suit;
    }
    
    //Update the suit of the card
    public void setSuit(String suit) {
        this.suit = suit;
    }
    
    //Check if two cards are equal
    public boolean equals(Card otherCard) {
        return this.rank.equals(otherCard.getRank()) && this.suit.equals(otherCard.getSuit());
    }
    
    //Get the string representation of the card
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
