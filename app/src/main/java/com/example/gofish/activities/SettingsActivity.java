package com.example.gofish.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.myapplication.R;

public class SettingsActivity extends AppCompatActivity {

    private MediaPlayer clickSound; // Declare MediaPlayer variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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
        Button homeButton = findViewById(R.id.homeButton);

        // Set OnClickListener for the homeButton
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Play the button click sound
                playButtonClickSound();

                // Create an Intent to go back to MainActivity
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);

                // Start the MainActivity
                startActivity(intent);

                // Finish the current activity (RulesActivity) to remove it from the back stack
                finish();
            }
        });
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
