package com.example.gofish.activities;

public class GameActivity {
    private List<Player> players;
    private CardDeck cardDeck;
    private Player currentPlayer;

    public GameActivity(List<String> playerNames) {
        initializePlayers(playerNames);
        cardDeck = new CardDeck();
        currentPlayer = players.get(0);
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

    public boolean playTurn(Player targetPlayer, String rankToAsk) {
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

    private void checkForSets(Player player, String rank) {
        int count = player.countRankCards(rank);
        if (count == 4) {
            // Player has collected a set, remove these cards from their hand and increase the score.
            player.removeRankCards(rank);
            player.addToScore(1);
        }
    }

    private void switchToNextPlayer() {
        int currentPlayerIndex = players.indexOf(currentPlayer);
        currentPlayer = players.get((currentPlayerIndex + 1) % players.size());
    }

    public boolean isGameOver() {
        return cardDeck.isEmpty() || allSetsCollected();
    }

    private boolean allSetsCollected() {
        for (Player player : players) {
            if (player.getScore() < 13) {
                return false;
            }
        }
        return true;
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
}
