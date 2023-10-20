package com.example.goldfish.activities;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.ComponentActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;

public class MainActivity extends ComponentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Linking to the string resources
        String appName = getString(R.string.app_name); // Gets the app_name string
        String startGameText = getString(R.string.start_game); // Gets the start_game string

        // Find the LottieAnimationView
        LottieAnimationView animationView = findViewById(R.id.animationView);
        // Set animation resource
        animationView.setAnimation(R.raw.animation);
        // Start the animation
        animationView.playAnimation();
        // Optionally, loop the animation (for an infinite loop)
        animationView.setRepeatCount(LottieDrawable.INFINITE);

        animationView.setScaleX(4.0f); // Adjust the scale as needed
        animationView.setScaleY(4.0f); // Adjust the scale as needed

        Button startGameButton = findViewById(R.id.startGameButton);

        // Get the TextView by its ID
        TextView titleTextView = findViewById(R.id.titleTextView);

        // Set the text with an emoji
        titleTextView.setText("Go\uD83C\uDCCFFish");

        Button

