package com.example.goldfish.activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import com.example.goldfish.R;
import com.example.goldfish.network.WifiDirect;
=======
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.ComponentActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;


public class MainActivity extends ComponentActivity {

    private static final int PERMISSION_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WifiDirect a = new WifiDirect(this,new Handler());
        String[] permissionsToRequest = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.NEARBY_WIFI_DEVICES,
        };

        ActivityCompat.requestPermissions(this, permissionsToRequest, 1);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            // Check if permissions were granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                // Permission denied
            }
        }
    }

}


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


