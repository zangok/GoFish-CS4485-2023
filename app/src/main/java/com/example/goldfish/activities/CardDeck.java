package com.example.goldfish;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CardDeck {
  private List<Card> deck;

  public CardDeck() {
    initializeDeck();
  }

  private void initializeDeck() {
    deck = new ArrayList<>();

    String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
    String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

    for(String suit : suits) {
      for(String rank : ranks) {
        deck.add(new Card(rank, suit));
      }
    }
  }

  public void shuffle() {
    int deckSize = deck.size();
    Random random = new Random();

    for(int i = deckSize - 1; i > 0; i--) {
      int j = random.nextInt(i + 1);

      Card temp = deck.get(i);
      deck.set(i, deck.get(j));
      deck.set(j, temp);
    }
  }

  public Card drawCard() {
    if(!deck.isEmpty()) {
      return deck.remove(0);
    }
    else {
      return null;
    }
  }

  public boolean isEmpty() {
    return deck.isEmpty();
  }
}