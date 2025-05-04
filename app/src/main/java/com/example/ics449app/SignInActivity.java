package com.example.ics449app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class SignInActivity extends AppCompatActivity {
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.sign_in_activity);

        db = FirebaseFirestore.getInstance();

        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnGoToRegister = findViewById(R.id.btnGoToRegister);

        btnLogin.setOnClickListener(view -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignInActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            authenticateUser(email, password);
        });

        btnGoToRegister.setOnClickListener(view -> {
            Intent intent = new Intent(SignInActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void authenticateUser(String email, String password) {
        db.collection("users")
                .whereEqualTo("email", email)
                .whereEqualTo("password", password)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            navigateToDashboard(email);
                            return;
                        }
                    } else {
                        Toast.makeText(SignInActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(SignInActivity.this, "Error checking credentials", Toast.LENGTH_SHORT).show();
                });
    }

    private void navigateToDashboard(String email) {
        Intent intent = new Intent(SignInActivity.this, DashboardActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
        finish();
    }
}
