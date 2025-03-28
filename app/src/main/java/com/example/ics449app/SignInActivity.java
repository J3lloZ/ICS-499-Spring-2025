package com.example.ics449app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {
    private SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.sign_in_activity);

        dbHelper = SQLiteHelper.instanceOfDatabase(this);

        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnGoToRegister = findViewById(R.id.btnGoToRegister);

        btnLogin.setOnClickListener(view -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (dbHelper.validateUser(email, password)) {
                SQLiteDatabase readableDb = dbHelper.getReadableDatabase();
                Cursor cursor = readableDb.rawQuery("SELECT role FROM users WHERE email = ?", new String[]{email});

                if (cursor.moveToFirst()) {
                    String role = cursor.getString(0);
                    cursor.close();

                    if ("student".equalsIgnoreCase(role)) {
                        startActivity(new Intent(this, StudentDashboardActivity.class));
                    } else if ("parent".equalsIgnoreCase(role)) {
                        startActivity(new Intent(this, ParentDashboardActivity.class));
                    } else {
                        Toast.makeText(this, "Unknown role: " + role, Toast.LENGTH_SHORT).show();
                    }

                    finish();
                } else {
                    Toast.makeText(this, "User role not found.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        btnGoToRegister.setOnClickListener(view -> {
            Intent intent = new Intent(SignInActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
