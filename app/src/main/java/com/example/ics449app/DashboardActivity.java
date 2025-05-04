package com.example.ics449app;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class DashboardActivity extends AppCompatActivity {
    private TextView welcomeText;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.dashboard_activity);

        welcomeText = findViewById(R.id.welcomeText);
        Button btnLogout = findViewById(R.id.btnLogout);

        db = FirebaseFirestore.getInstance();

        String email = getIntent().getStringExtra("email");

        if (email != null) {
            fetchFirstName(email);
        } else {
            welcomeText.setText("Welcome!");
        }

        btnLogout.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void fetchFirstName(String email) {
        db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(query -> {
                    if (!query.isEmpty()) {
                        String firstName = query.getDocuments().get(0).getString("firstName");
                        welcomeText.setText("Welcome, " + firstName + "!");
                    } else {
                        welcomeText.setText("Welcome!");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.w("FIREBASE", "Error fetching first name", e);
                    welcomeText.setText("Welcome!");
                });
    }
}
