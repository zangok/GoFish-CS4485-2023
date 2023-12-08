package com.example.myapplication;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class GameActivity extends AppCompatActivity {
    private Game goFishGame;
    private Player currentPlayer;
    private Player opponentPlayer;
    private MediaPlayer clickSound;
    private RecyclerView currentPlayerRecyclerView, opponentPlayerRecyclerView;
    private CardAdapter currentPlayerAdapter, opponentPlayerAdapter;
    private TextView currentPlayerScoreTextView, opponentPlayerScoreTextView;
    private TextView gameMessageTextView;
    private Player targetPlayer;
    private String rankToAsk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameMessageTextView = findViewById(R.id.gameMessageTextView);

        // Find the LottieAnimationView
        LottieAnimationView animationView = findViewById(R.id.animationView);

        // Set animation resource
        animationView.setAnimation(R.raw.animation);

        // Start the animation
        animationView.playAnimation();

        // Optionally, loop the animation (for infinite loop)
        animationView.setRepeatCount(LottieDrawable.INFINITE);

        // Adjust the scale as needed
        animationView.setScaleX(4.0f);
        animationView.setScaleY(4.0f);

        // Initialize the MediaPlayer with the button click sound
        clickSound = MediaPlayer.create(this, R.raw.bubblesound);

        currentPlayerRecyclerView = findViewById(R.id.currentPlayerRecyclerView);
        opponentPlayerRecyclerView = findViewById(R.id.opponentPlayerRecyclerView);
        currentPlayerScoreTextView = findViewById(R.id.currentPlayerScoreTextView);
        opponentPlayerScoreTextView = findViewById(R.id.opponentPlayerScoreTextView);

        currentPlayerRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        opponentPlayerRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        initializeGame();

        Button playTurnButton = findViewById(R.id.playTurnButton);
        playTurnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playTurn();
            }
        });
        updatePlayTurnButtonText();

        // Set OnClickListener for the homeButton
        Button homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Play the button click sound
                playButtonClickSound();

                // Create an Intent to go back to MainActivity
                Intent intent = new Intent(GameActivity.this, MainActivity.class);

                // Start the MainActivity
                startActivity(intent);

                // Finish the current activity (GameActivity) to remove it from the back stack
                finish();
            }
        });
    }

    private void initializeGame() {
        List<String> playerNames = new ArrayList<>();
        playerNames.add("Player 1");
        playerNames.add("Player 2");

        goFishGame = new Game(playerNames);

        currentPlayer = goFishGame.getCurrentPlayer();
        opponentPlayer = getOpponentPlayer();
        updateGameMessage("Game started. " + currentPlayer.getName() + "'s turn.");

        currentPlayerAdapter = new CardAdapter(currentPlayer.getHand());
        opponentPlayerAdapter = new CardAdapter(opponentPlayer.getHand());

        currentPlayerRecyclerView.setAdapter(currentPlayerAdapter);
        opponentPlayerRecyclerView.setAdapter(opponentPlayerAdapter);

        updateScores();
        updateUI();
    }

    private void updateGameMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gameMessageTextView.setText(message);
            }
        });
    }

    private Player getOpponentPlayer() {
        int currentPlayerIndex = goFishGame.getPlayers().indexOf(currentPlayer);
        int opponentPlayerIndex = (currentPlayerIndex + 1) % goFishGame.getPlayers().size();
        return goFishGame.getPlayers().get(opponentPlayerIndex).copy();
    }

    private void playTurn() {
        if (currentPlayer == null || opponentPlayer == null) {
            System.out.println("Error: currentPlayer or targetPlayer is null.");
            return;
        }

     //   System.out.println("Before Turn - Player 1 Hand: " + currentPlayer.getHand());
       // System.out.println("Before Turn - Player 2 Hand: " + opponentPlayer.getHand());

        String message = currentPlayer.getName() + " asks " + opponentPlayer.getName() + " for a " + rankToAsk + ".";
        updateGameMessage(message);

        if (opponentPlayer.getHand().isEmpty()) {
            message = opponentPlayer.getName() + "'s hand is empty. Go Fish!";
            updateGameMessage(message);
        } else {
            boolean successfulTurn = goFishGame.playTurn();

            if (successfulTurn) {
                message = currentPlayer.getName() + " received cards from " + opponentPlayer.getName() + ".";
                updateGameMessage(message);

                // Set hands after the turn
                currentPlayer.setHand(goFishGame.getCurrentPlayer().getHand());
                opponentPlayer.setHand(goFishGame.getPlayers().get(1).getHand());

                updateScores();
                updateUI();
                updatePlayTurnButtonText(); // Update the button text after the turn
            } else {
                message = "Invalid target player or empty hand. Go Fish!";
                updateGameMessage(message);
            }
        }

       // System.out.println("After Turn - Player 1 Hand: " + currentPlayer.getHand());
        //System.out.println("After Turn - Player 2 Hand: " + opponentPlayer.getHand());
    }

    private void updateScores() {
        currentPlayerScoreTextView.setText("Score P1: " + currentPlayer.getScore());
        opponentPlayerScoreTextView.setText("Score P2: " + getOpponentPlayer().getScore());
    }

    private void updateUI() {
        currentPlayerAdapter.notifyDataSetChanged();
        opponentPlayerAdapter.notifyDataSetChanged();

        if (goFishGame.isGameOver()) {
            showGameOverDialog();
        } else {
            switchPlayers();
        }
    }

    private void updatePlayTurnButtonText() {
        Button playTurnButton = findViewById(R.id.playTurnButton);

        if (currentPlayer.getName().equals("Player 1")) {
            playTurnButton.setText("Player 1's Turn");
        } else if (currentPlayer.getName().equals("Player 2")) {
            playTurnButton.setText("Player 2's Turn");
        }
    }

    private void switchPlayers() {
        currentPlayer = getOpponentPlayer();
        opponentPlayer = getOpponentPlayer(); // Add this line
    }

    private void showGameOverDialog() {
        Toast.makeText(this, "Game Over! Winner: " + goFishGame.getWinner().getName(), Toast.LENGTH_SHORT).show();
    }

    private void playButtonClickSound() {
        if (clickSound != null) {
            clickSound.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (clickSound != null) {
            clickSound.release();
            clickSound = null;
        }
    }
}
