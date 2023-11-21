package com.example.gofish.activities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Player {
    private String name;
    private ArrayList<Card> hand;
    private int score;
    
    //Constructor to initialize player attributes
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.score = 0;
    }
    
    //Gets the name of the player
    public String getName() {
        return name;
    }
    
    //Sets the name of the player
    public void setName(String name) {
        if(name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be empty");
        }
        this.name = name;
    }
    
    //Gets the list of cards in the player's hand
    public ArrayList<Card> getHand() {
        return hand;
    }
    
    //Sets the player's hand with new list of cards
    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }
    
    //Gets the score of the player
    public int getScore() {
        return score;
    }
    
    //Sets the score of the player
    public void setScore(int score) {
        this.score = score;
    }
    
    //Adds a card to the player's hand
    public void addToHand(Card card) {
        if(card == null) {
            throw new IllegalArgumentException("Card is null");
        }
        hand.add(card);
    }
    
    //Removes a card from the player's hand
    public void removeFromHand(Card card) {
        if(card == null) {
            throw new IllegalArgumentException("Card is null");
        }
        hand.remove(card);
    }

    //Checks if the player has a card with the given rank in their hand
    public boolean hasCard(String rank) {
        for(Card card : hand) {
            if(card.getRank().equals(rank)) {
                return true;
            }
        }
        return false;
    }
    
    //Transfers cards with the given rank from the player's hand
    public List<Card> transferCards(String rank) {
        if(rank == null) {
            throw new IllegalArgumentException("Rank cannot be null");
        }
        List<Card> cardsToTransfer = new ArrayList<>();
        Iterator<Card> iterator = hand.iterator();
        while(iterator.hasNext()) {
            Card card = iterator.next();
            if(card.getRank().equals(rank)) {
                cardsToTransfer.add(card);
                iterator.remove();
            }
        }
        return cardsToTransfer;
    }
    
    
    //Add cards to the player's hand
    public void addToHand(List<Card> cards) {
        hand.addAll(cards);
    }

    //Counts the number of cards with the given rank in the player's hand
    protected int countRankCards(String rank) {
        int count = 0;
        for (Card card : hand) {
            if (card.getRank().equals(rank)) {
                count++;
            }
        }
        return count;
    }
    
    
    //Removes all cards with the given rank from the player's hand
    protected void removeRankCards(String rank) {
        Iterator<Card> iterator = hand.iterator();
        while (iterator.hasNext()) {
            Card card = iterator.next();
            if (card.getRank().equals(rank)) {
                iterator.remove();
            }
        }
    }

    
    //Adds points to the player's score
    public void addToScore(int points) {
        score += points;
    }


  

}
