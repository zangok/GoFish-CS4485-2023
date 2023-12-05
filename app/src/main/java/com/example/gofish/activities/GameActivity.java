package com.example.gofish.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.gofish.network.WifiDirect;
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
import java.util.List;

public class GameActivity extends AppCompatActivity {
    private Game goFishGame;
    private WifiDirect wifi;
    private Player currentPlayer;
    private Player opponentPlayer;
    private MediaPlayer clickSound;
    private RecyclerView currentPlayerRecyclerView, opponentPlayerRecyclerView;
    private CardAdapter currentPlayerAdapter, opponentPlayerAdapter;
    private TextView currentPlayerScoreTextView, opponentPlayerScoreTextView;
    private final int MSG_TYPE_RECEIVED = 1;

    private Handler networkHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_TYPE_RECEIVED:
                    String receivedMessage = msg.getData().getString("received");
                    handleReceivedMessage(receivedMessage,goFishGame);
                    break;
            }
        }
    };
    public void handleReceivedMessage(String receivedMessage,Game goFishGame ) {
        goFishGame.playTurn(opponentPlayer,receivedMessage);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        wifi = new WifiDirect(this,networkHandler);
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
        opponentPlayer = getOpponentPlayer(); // Use the helper method to get the opponent player

        currentPlayerAdapter = new CardAdapter(currentPlayer.getHand());
        opponentPlayerAdapter = new CardAdapter(opponentPlayer.getHand());

        currentPlayerRecyclerView.setAdapter(currentPlayerAdapter);
        opponentPlayerRecyclerView.setAdapter(opponentPlayerAdapter);

        updateScores();
        updateUI();
    }

    // Add this helper method to get the opponent player
    private Player getOpponentPlayer() {
        int currentPlayerIndex = goFishGame.getPlayers().indexOf(currentPlayer);
        int opponentPlayerIndex = (currentPlayerIndex + 1) % goFishGame.getPlayers().size();
        return goFishGame.getPlayers().get(opponentPlayerIndex).copy(); // Copy the opponent player instance
    }

    private void playTurn() {
        // Implement the logic for a turn
        // For simplicity, let's assume the user always asks for the first rank in their hand
        String rankToAsk = currentPlayer.getHand().get(0).getRank();
        boolean successfulTurn = goFishGame.playTurn(opponentPlayer, rankToAsk);
        wifi.sendMessage(rankToAsk);

        if (successfulTurn) {
            updateScores();
            updateUI();
        }
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

    private void switchPlayers() {
        currentPlayer = getOpponentPlayer();
    }

    private void showGameOverDialog() {
        // Implement logic to show a dialog or navigate to the end game screen
        // For now, you can display a simple toast message
        Toast.makeText(this, "Game Over! Winner: " + goFishGame.getWinner().getName(), Toast.LENGTH_SHORT).show();
    }
    // Method to play the button click sound
    private void playButtonClickSound() {
        if (clickSound != null) {
            clickSound.start();
        }
    }

    // Override onDestroy to release the MediaPlayer when the activity is destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (clickSound != null) {
            clickSound.release();
            clickSound = null;
        }
    }
}
