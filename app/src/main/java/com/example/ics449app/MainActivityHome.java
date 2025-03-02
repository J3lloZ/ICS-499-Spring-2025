package com.example.ics449app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivityHome extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle saveInstanceState)  {
        super.onCreate(saveInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_home);

        // Find the button by ID
        Button button = findViewById(R.id.button);

        // Set OnClickListener to the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent to start the new activity
                Intent intent = new Intent(MainActivityHome.this, SignInActivity.class);
                startActivity(intent);  // Start the new activity
            }
        });

        // Find the TextView by ID
        TextView textView = findViewById(R.id.textView);
    }
}