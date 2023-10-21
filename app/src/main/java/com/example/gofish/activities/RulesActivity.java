package com.example.gofish.activities;
import com.airbnb.lottie.LottieAnimationView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieDrawable;
import com.example.myapplication.R;

import androidx.appcompat.app.AppCompatActivity;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);


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

        Button homeButton = findViewById(R.id.homeButton);

        // Set OnClickListener for the homeButton
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to go back to MainActivity
                Intent intent = new Intent(RulesActivity.this, MainActivity.class);

                // Start the MainActivity
                startActivity(intent);

                // Finish the current activity (RulesActivity) to remove it from the back stack
                finish();
            }
        });
    }
}