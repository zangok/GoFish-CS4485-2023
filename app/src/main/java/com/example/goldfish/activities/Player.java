package com.example.gofish;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Player {
    private String name;
    private ArrayList<Card> hand;
    private int score;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addToHand(Card card) {
        hand.add(card);
    }

    public void removeFromHand(Card card) {
        hand.remove(card);
    }

    public boolean hasCard(String rank) {
      for(Card card : hand) {
        if(card.getRank().equals(rank)) {
          return true;
        }
      }
      return false;
    } 

    public List<Card> transferCards(String rank) {
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

  private int countRankCards(String rank) {
    int count = 0;
    for(Card card : hand) {
      if(card.getRank().equals(rank)) {
        count++;
      }
    }
    return count;
  }

  private void removeRankCards(String rank) {
    Iterator<Card> iterator = hand.iterator();
    while(iterator.hasNext()) {
      Card card = iterator.next();
      if(card.getRank().equals(rank)) {
        iterator.remove();
      }
    }
  }
  

}

