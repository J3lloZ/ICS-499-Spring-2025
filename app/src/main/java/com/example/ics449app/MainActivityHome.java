package com.example.ics449app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivityHome extends AppCompatActivity{
    private SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        getSupportActionBar().hide();

        // Initialize the database
        dbHelper = SQLiteHelper.instanceOfDatabase(this);

        // Insert data into the database
        insertSampleData();

        // Set up UI element
        setupUI();
    }

    private void insertSampleData() {
        if (dbHelper == null) {
            Log.e("DB", "Database helper is not initialized");
            return;
        }

        // Sample Data
        School newSchool = new School("123", "Central HighSchool", "0000 main Street", "contact@CentralHS.edu");
        Student newUser = new Student("000111", "S001", "John","Doe","johndoe@gmail.com","Password","Student","123");
        Subject newSubject = new Subject("BIO-125","Biology","123");

        try {
            // Check if the school already exists
            if (!dbHelper.schoolExists(newSchool.getSchoolCode())) {
                dbHelper.addSchool(newSchool);
            } else {
                Log.d("DB", "School already exists");
            }

            // Check if the user already exists
            if (!dbHelper.userExists(newUser.getUserID())) {
                dbHelper.addUser(newUser);
            } else {
                Log.d("DB", "School already exists");
            }

            // Check if the subject already exists
            if (!dbHelper.subjectExists(newSubject.getSubjectID())) {
                dbHelper.addSubject(newSubject);
            } else {
                Log.d("DB", "School already exists");
            }
            Log.d("DB", "Sample data inserted sucessfully");
        } catch (Exception e) {
            Log.e("DB", "Error cannot insert data", e);
        }
    }

    public void setupUI() {
        setContentView(R.layout.activity_main_home);
        // Find the button by ID
        Button button = findViewById(R.id.button);

        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Create an intent to start the new activity
                    Intent intent = new Intent(MainActivityHome.this, SignInActivity.class);
                    startActivity(intent);  // Start the new activity
                }
            });
        }

        // Find the Register button by ID
        Button btnRegister = findViewById(R.id.btnRegister);
        if (btnRegister != null) {
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivityHome.this, RegisterActivity.class);
                    startActivity(intent);  // Start the Register activity
                }
            });
        }

        // Find the TextView by ID
        TextView textView = findViewById(R.id.textView);
    }
}