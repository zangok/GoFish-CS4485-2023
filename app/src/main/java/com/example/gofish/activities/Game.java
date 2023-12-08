package com.example.myapplication;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Game {
    private List<Player> players;
    private CardDeck cardDeck;
    private int currentPlayerIndex;
    private Player currentPlayer;

    public Game(List<String> playerNames) {
        initializePlayers(playerNames);
        cardDeck = new CardDeck();
        startGame();
    }

    private void initializePlayers(List<String> playerNames) {
        players = new ArrayList<>();
        for (String name : playerNames) {
            players.add(new Player(name));
        }
    }

    private void startGame() {
        cardDeck.shuffle();
        dealInitialCards();

        currentPlayerIndex = 0; // Start with player 1
        currentPlayer = players.get(currentPlayerIndex);
    }

    private void dealInitialCards() {
        int numPlayers = players.size();
        int numCardsToDeal = (numPlayers >= 4) ? 5 : 7;

        for (int i = 0; i < numCardsToDeal; i++) {
            for (Player player : players) {
                player.addToHand(cardDeck.drawCard());
            }
        }
    }

    public boolean playTurn() {
        Player targetPlayer = getTargetPlayer();

        if (targetPlayer == null || currentPlayer == targetPlayer || targetPlayer.getHand().isEmpty()) {
            System.out.println("Invalid target player or empty hand. Go Fish!");
            return false;
        }

        String rankToAsk = getRandomRank();
        System.out.println(currentPlayer.getName() + " asks " + targetPlayer.getName() + " for a " + rankToAsk + ".");

        // Transfer cards from targetPlayer to currentPlayer
        List<Card> cardsToReceive = targetPlayer.transferCards(rankToAsk);
        if (!cardsToReceive.isEmpty()) {
            System.out.println(targetPlayer.getName() + " is transferring cards to " + currentPlayer.getName() + ": " + cardsToReceive);
            checkForSets(currentPlayer, rankToAsk);

            currentPlayer.addToHand(cardsToReceive);
            System.out.println(currentPlayer.getName() + " received cards from " + targetPlayer.getName() + ".");
            switchToNextPlayer();
            System.out.println("Before Turn - " + currentPlayer.getName() + " Hand: " + currentPlayer.getHand());
            System.out.println("Before Turn - " + targetPlayer.getName() + " Hand: " + targetPlayer.getHand());

            switchToNextPlayer(); // Switch to the next player's turn.

            // Print statements for debugging
            System.out.println("After Turn - " + currentPlayer.getName() + " Hand: " + currentPlayer.getHand());
            System.out.println("After Turn - " + targetPlayer.getName() + " Hand: " + targetPlayer.getHand());

            return true;


        } else {
            System.out.println(targetPlayer.getName() + " says: Go Fish!");

            // Add code to handle the case when Player 1 transfers cards to Player 2
         List<Card> cardsToGive = currentPlayer.transferCards(rankToAsk);
            if (!cardsToGive.isEmpty()) {
                checkForSets(targetPlayer, rankToAsk);
                targetPlayer.addToHand(cardsToGive);
                System.out.println(targetPlayer.getName() + " is transferring cards to " + currentPlayer.getName() + ": " + cardsToReceive);

                System.out.println(currentPlayer.getName() + " received cards from " + targetPlayer.getName() + ".");
                switchToNextPlayer();

                return true;
            }
        }

        // If targetPlayer doesn't have the card, draw a card for currentPlayer
        Card drawnCard = cardDeck.drawCard();
        if (drawnCard != null) {
            currentPlayer.addToHand(drawnCard);
            System.out.println(currentPlayer.getName() + " drew a " + drawnCard + ".");
            if (drawnCard.getRank().equals(rankToAsk)) {
                // Player drew a matching card and gets another turn.
                System.out.println(currentPlayer.getName() + " drew a matching card and gets another turn!");
                return true;
            }
        }

        switchToNextPlayer(); // Switch to the next player's turn.
        return true; // Player successfully completed their turn.
    }

    private String getRandomRank() {
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        Random random = new Random();
        return ranks[random.nextInt(ranks.length)];
    }
    private Player getTargetPlayer() {
        int numPlayers = players.size();
        int targetPlayerIndex = (currentPlayerIndex + numPlayers / 2) % numPlayers;
        return players.get(targetPlayerIndex);
    }
    private void checkForSets(Player player, String rank) {
        int count = player.countRankCards(rank);
        if (count == 4) {
            // Player has collected a set, remove these cards from their hand and increase the score.
            player.removeRankCards(rank);
            player.addToScore(1);
        }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    private void switchToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        currentPlayer = players.get(currentPlayerIndex);

        if (isGameOver()) {
            // Game is over, do not switch to the next player
            currentPlayer = null;
        }
    }

    public boolean isGameOver() {
        return cardDeck.isEmpty();
    }

    public Player getWinner() {
        Player winner = players.get(0);
        for (Player player : players) {
            if (player.getScore() > winner.getScore()) {
                winner = player;
            }
        }
        return winner;
    }

    public List<Player> getPlayers() {
        return players;
    }
}

