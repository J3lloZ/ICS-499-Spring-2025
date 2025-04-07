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
                Cursor cursor = readableDb.rawQuery(
                        "SELECT role, schoolCode FROM users WHERE LOWER(email) = LOWER(?)",
                        new String[]{email}
                );

                if (cursor.moveToFirst()) {
                    String role = cursor.getString(0);
                    String schoolCode = cursor.getString(1);
                    cursor.close();

                    Intent intent;

                    if ("student".equalsIgnoreCase(role)) {
                        intent = new Intent(SignInActivity.this, StudentDashboardActivity.class);
                        intent.putExtra("email", email);
                    } else if ("parent".equalsIgnoreCase(role)) {
                        intent = new Intent(SignInActivity.this, ParentDashboardActivity.class);
                        intent.putExtra("email", email);
                    } else if ("teacher".equalsIgnoreCase(role)) {
                        intent = new Intent(SignInActivity.this, TeacherDashboardActivity.class);
                        intent.putExtra("email", email);
                        intent.putExtra("schoolCode", schoolCode);
                    } else {
                        Toast.makeText(this, "Unknown role: " + role, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    startActivity(intent);
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

