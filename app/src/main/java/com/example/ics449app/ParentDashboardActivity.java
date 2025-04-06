package com.example.ics449app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ParentDashboardActivity extends AppCompatActivity {
    private TextView tvTimer, tvWelcome;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 60000; // 1 minute
    private boolean isTimerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dashboard);

        tvTimer = findViewById(R.id.tvTimer);
        tvWelcome = findViewById(R.id.tvWelcome); //  New Welcome TextView

        Spinner spinnerChildren = findViewById(R.id.spinnerChildren);
        Button btnStartStudy = findViewById(R.id.btnStartStudy);
        Button btnStopReset = findViewById(R.id.btnStopReset);
        Button btnLogout = findViewById(R.id.btnLogout);

        // Dynamic welcome message
        String email = getIntent().getStringExtra("email");
        if (email != null) {
            SQLiteHelper dbHelper = SQLiteHelper.instanceOfDatabase(this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT firstName FROM users WHERE LOWER(email) = LOWER(?)", new String[]{email});
            if (cursor.moveToFirst()) {
                String firstName = cursor.getString(0);
                tvWelcome.setText("Welcome, " + firstName + "!");
            }
            cursor.close();
        }

        // Populate spinner with dummy child names (replace later)
        String[] children = {"Child 1", "Child 2", "Child 3"};
        spinnerChildren.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, children));

        btnStartStudy.setOnClickListener(v -> startStudyTimer());
        btnStopReset.setOnClickListener(v -> stopAndResetTimer());
        btnLogout.setOnClickListener(v -> logoutUser());

        updateTimerDisplay();
    }

    private void startStudyTimer() {
        if (!isTimerRunning) {
            countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftInMillis = millisUntilFinished;
                    updateTimerDisplay();
                }

                @Override
                public void onFinish() {
                    tvTimer.setText(getString(R.string.timer_done));
                    isTimerRunning = false;
                }
            }.start();
            isTimerRunning = true;
        }
    }

    private void stopAndResetTimer() {
        if (isTimerRunning) {
            countDownTimer.cancel();
            isTimerRunning = false;
        }
        timeLeftInMillis = 60000;
        updateTimerDisplay();
    }

    private void updateTimerDisplay() {
        int seconds = (int) (timeLeftInMillis / 1000);
        tvTimer.setText(getString(R.string.timer_format, seconds));
    }

    private void logoutUser() {
        Intent intent = new Intent(ParentDashboardActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
}

