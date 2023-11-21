package com.example.gofish.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardDeck {
  private List<Card> deck;
    
  //Constructor for CardDeck
  public CardDeck() {
    initializeDeck();
  }
    
  //Initialize the deck with all the possible cards
  private void initializeDeck() {
    deck = new ArrayList<>();

    String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
    String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    
    //Create cards for each combination of suit and rank and add them to deck
    for(String suit : suits) {
      for(String rank : ranks) {
        deck.add(new Card(rank, suit));
      }
    }
  }

  //Shuffle the deck
  public void shuffle() {
    //Check if the deck is empty before shuffling
    if(deck.isEmpty()) {
        throw new IllegalStateException("Deck is empty.");
    }
    int deckSize = deck.size();
    Random random = new Random();

    for(int i = deckSize - 1; i > 0; i--) {
      int j = random.nextInt(i + 1);

      Card temp = deck.get(i);
      deck.set(i, deck.get(j));
      deck.set(j, temp);
    }
  }
  
  //Draw a card from the top of the deck
  public Card drawCard() {
    if(!deck.isEmpty()) {
      return deck.remove(0);
    }
    else {
      return null;
    }
  }

  //Check if the deck is empty
  public boolean isEmpty() {
    return deck.isEmpty();
  }
}
