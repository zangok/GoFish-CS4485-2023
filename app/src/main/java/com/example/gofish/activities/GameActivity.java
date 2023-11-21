package com.example.gofish.activities;

public class GameActivity {
    private List<Player> players;
    private CardDeck cardDeck;
    private Player currentPlayer;
    
    //Constructor to initialize players, card deck, and start the game
    public GameActivity(List<String> playerNames) {
        initializePlayers(playerNames);
        cardDeck = new CardDeck();
        currentPlayer = players.get(0);
        startGame();
    }
    
    //Initialize players based on given names
    private void initializePlayers(List<String> playerNames) {
        players = new ArrayList<>();
        for (String name : playerNames) {
            players.add(new Player(name));
        }
    }
    
    //Start the game by shuffling the deck and dealing initial cards
    private void startGame() {
        cardDeck.shuffle();
        if(cardDeck.isEmpty()) {
            throw new IllegalStateException("Deck is empty.");
        }
        dealInitialCards();
    }
    
    //Deal initial cards to all players
    private void dealInitialCards() {
        if(cardDeck.isEmpty()) {
            throw new IllegalStateException("Deck is empty.");
        }
        int numPlayers = players.size();
        int numCardsToDeal = (numPlayers >= 4) ? 5 : 7;

        for (int i = 0; i < numCardsToDeal; i++) {
            for (Player player : players) {
                player.addToHand(cardDeck.drawCard());
            }
        }
    }
    
    //Play a turn in the game, asking the given target player for a specific rank
    public boolean playTurn(Player targetPlayer, String rankToAsk) {
        if(targetPlayer == null || rankToAsk == null) {
            throw new IllegalArgumentException("targetPlayer and rankToAsk are null.");
        }
        if (currentPlayer == targetPlayer) {
            return false; // Current player cannot ask themselves.
        }

        List<Card> cardsToReceive = targetPlayer.transferCards(rankToAsk);
        if (!cardsToReceive.isEmpty()) {
            currentPlayer.addToHand(cardsToReceive);
            checkForSets(currentPlayer, rankToAsk);
            return true; // Player successfully received cards.
        } else {
            Card drawnCard = cardDeck.drawCard();
            currentPlayer.addToHand(drawnCard);
            if (drawnCard.getRank().equals(rankToAsk)) {
                return true; // Player drew a matching card and gets another turn.
            } else {
                switchToNextPlayer();
                return false; // Player drew a different card, and it's the next player's turn.
            }
        }
    }
    
    //Check for sets in a player's hand based on the given rank
    private void checkForSets(Player player, String rank) {
        int count = player.countRankCards(rank);
        if (count == 4) {
            // Player has collected a set, remove these cards from their hand and increase the score.
            player.removeRankCards(rank);
            player.addToScore(1);
        }
    }
    
    //Switch to the next player in the game
    private void switchToNextPlayer() {
        int currentPlayerIndex = players.indexOf(currentPlayer);
        currentPlayer = players.get((currentPlayerIndex + 1) % players.size());
    }
    
    //Check if the game is over
    public boolean isGameOver() {
        return cardDeck.isEmpty() || allSetsCollected();
    }
    
    //Check if all sets have been collected by players
    private boolean allSetsCollected() {
        for (Player player : players) {
            if (player.getScore() < 13) {
                return false;
            }
        }
        return true;
    }
    
    //Get the winner of the game
    public Player getWinner() {
        Player winner = players.get(0);
        for (Player player : players) {
            if (player.getScore() > winner.getScore()) {
                winner = player;
            }
        }
        return winner;
    }
}
