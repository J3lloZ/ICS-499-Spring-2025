package com.example.study;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivityStart extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle saveInstanceState)  {
        super.onCreate(saveInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_start);

        // Find the button by ID
        Button button = findViewById(R.id.button);

        // Set OnClickListener to the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent to start the new activity
                Intent intent = new Intent(MainActivityStart.this, MainActivityHome.class);
                startActivity(intent);  // Start the new activity
            }
        });

        // Find the TextView by ID
        TextView textView = findViewById(R.id.textView);
    }
}


