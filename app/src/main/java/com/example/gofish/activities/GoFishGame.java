package com.example.gofish.activities;

import java.util.List;

public class GoFishGame {
  private List<Player> players;
  private CardDeck cardDeck;
  private Player currentPlayer;

  public GoFishGame(List<Player> players) {
    this.players = players;
    this.cardDeck = new CardDeck();
    this.currentPlayer = players.get(0);
    startGame();
  }

  private void startGame() {
    cardDeck.shuffle();
    dealCards();
  }

  private void dealCards() {
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

  private void switchToNextPlayer() {
    int currentPlayerIndex = players.indexOf(currentPlayer);
    currentPlayer = players.get((currentPlayerIndex + 1) % players.size());
  }

  public boolean gameOver() {
    return cardDeck.isEmpty();
  }
  
}
