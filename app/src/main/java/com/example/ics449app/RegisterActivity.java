package com.example.ics449app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {
    private SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        Spinner spinnerRole = findViewById(R.id.spinnerRole);  // New spinner
        Button btnRegister = findViewById(R.id.btnRegister);

        // Set up the register button click listener
        btnRegister.setOnClickListener(view -> {
            String firstName = etFirstName.getText().toString().trim();
            String lastName = etLastName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String schoolCode = etSchoolCode.getText().toString().trim();
            String selectedRole = spinnerRole.getSelectedItem().toString().trim(); // Get role

            // Validate user input
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||
                    password.isEmpty() || schoolCode.isEmpty() || selectedRole.equals("Select Role")) {
                Toast.makeText(RegisterActivity.this, "Please fill in all fields and select a valid role", Toast.LENGTH_SHORT).show();
                return;
            }


            // Check if user already exists
            if (dbHelper.userExists(email)) {
                Toast.makeText(RegisterActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                return;
            }

            // Generate a unique userID
            String userID = UUID.randomUUID().toString();

            User newUser = null;

            try {
                if(selectedRole.equalsIgnoreCase("Teacher")) {
                    // Generate teacherId
                    String teacherId = "TCH-" + UUID.randomUUID().toString().substring(0, 8);

                    // Create teacher object
                    newUser  = new Teacher(userID, teacherId, firstName, lastName, email, password, selectedRole, schoolCode);
                } else if (selectedRole.equalsIgnoreCase("Parent")) {
                    // Generate parentId
                    String parentId = "TCH-" + UUID.randomUUID().toString().substring(0, 8);

                    // Create Parent object
                    newUser  = new Parent(userID, parentId, firstName, lastName, email, password, selectedRole, schoolCode);
                } else if (selectedRole.equalsIgnoreCase("Student")) {
                    // Generate parentId
                    String studentId = "TCH-" + UUID.randomUUID().toString().substring(0, 8);

                    // Create Parent object
                    newUser  = new Student(userID, studentId, firstName, lastName, email, password, selectedRole, schoolCode);
                }

                if(newUser != null) {
                    // Call the correct method based on the type of newUser
                    if (newUser instanceof Teacher) {
                        dbHelper.addUser((Teacher) newUser);
                    } else if (newUser instanceof Parent) {
                        dbHelper.addUser((Parent) newUser);
                    } else if (newUser instanceof Student) {
                        dbHelper.addUser((Student) newUser);
                    }


                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                    // Navigate to login screen after registration
                    Intent intent = new Intent(RegisterActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                }
            } catch (Exception e) {
                Log.e("RegisterActivity", "Error adding user to database", e);
                Toast.makeText(RegisterActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
