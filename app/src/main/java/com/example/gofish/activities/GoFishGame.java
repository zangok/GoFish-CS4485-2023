package com.example.gofish.activities;

import java.util.List;

public class GoFishGame {
  private List<Player> players;
  private CardDeck cardDeck;
  private Player currentPlayer;
    
  //Constructor to initialize players, card deck, and start the game
  public GoFishGame(List<Player> players) {
    if(players == null || players.isEmpty()) {
        throw new IllegalArgumentException("Players list cannot be null or empty.");
    }
    this.players = players;
    this.cardDeck = new CardDeck();
    this.currentPlayer = players.get(0);
    startGame();
  }
    
  //Start the game by shuffling the deck and dealing initial cards
  private void startGame() {
    cardDeck.shuffle();
    dealCards();
  }
    
  //Deal initial cards to all players
  private void dealCards() {
    if(cardDeck.isEmpty()) {
        throw new IllegalStateException("Deck is empty.");
    }
    int numCardsToDeal;
    if(players.size() >= 4) {
      numCardsToDeal = 5;
    }
    else {
      numCardsToDeal = 7;
    }
    for(int i = 0; i < numCardsToDeal; i++) {
      for(Player player : players) {
        player.addToHand(cardDeck.drawCard());
      }
    }
  }

  //Switch to the next player
  private void switchToNextPlayer() {
    int currentPlayerIndex = players.indexOf(currentPlayer);
    currentPlayer = players.get((currentPlayerIndex + 1) % players.size());
  }
    
  //Check if the game is over based on an empty card deck
  public boolean gameOver() {
    return cardDeck.isEmpty();
  }
  
}
