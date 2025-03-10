package com.example.ics449app;

import android.content.Intent;
import android.os.Bundle;
import android.service.controls.templates.ToggleTemplate;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

public class RegisterActivity extends AppCompatActivity{
    private SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        // Initialize the database helper
        dbHelper = SQLiteHelper.instanceOfDatabase(this);

        // Get references to the input fields and register button
        EditText etFirstName = findViewById(R.id.etFirstName);
        EditText etLastName = findViewById(R.id.etLastName);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        EditText etSchoolCode = findViewById(R.id.etSchoolCode);
        Button btnRegister = findViewById(R.id.btnRegister);

        // Set up the register button click Listener
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = etFirstName.getText().toString().trim();
                String lastName = etLastName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String schoolCode = etSchoolCode.getText().toString().trim();

                // Validate User input
                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || schoolCode.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if email already exists
                if (dbHelper.userExists(email)) {
                    Toast.makeText(RegisterActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Generate a unique userID
                String userID = UUID.randomUUID().toString();

                // Create a new student object
                Student newUser = new Student(userID, firstName, lastName, email, password, "Student", schoolCode);

                try {
                    // Add the user to the database
                    dbHelper.addStudent(newUser);

                    // Display success message
                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                    // Navigate to Dashboard
                    Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    Log.e("RegisterActivity", "Error adding user to database", e);
                    Toast.makeText(RegisterActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
