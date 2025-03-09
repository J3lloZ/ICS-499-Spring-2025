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

        School newSchool = new School("123", "Central HighSchool", "0000 main Street", "contact@CentralHS.edu");
        Student newUser = new Student("000111","John","Doe","johndoe@gmail.com","Password","Student","123");
        Subject newSubject = new Subject("BIO-125","123");

        SQLiteHelper dbHelper = SQLiteHelper.instanceOfDatabase(this);
        dbHelper.addSchool(newSchool);

        dbHelper.addStudent(newUser);
        dbHelper.addSubject(newSubject);

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