package com.example.ics449app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StudentDashboardActivity extends AppCompatActivity {
    private TextView tvTimer, tvWelcome;
    private CountDownTimer countDownTimer;
    private boolean isTimerRunning = false;
    private long timeLeftInMillis = 0;

    private NumberPicker npHour, npMinute, npSecond;
    private SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        dbHelper = SQLiteHelper.instanceOfDatabase(this);

        tvTimer = findViewById(R.id.tvTimer);
        tvWelcome = findViewById(R.id.tvWelcome);
        Button btnStartStudy = findViewById(R.id.btnStartStudy);
        Button btnStopReset = findViewById(R.id.btnStopReset);
        Button btnLogout = findViewById(R.id.btnLogout);

        // NumberPickers
        npHour = findViewById(R.id.npHour);
        npMinute = findViewById(R.id.npMinute);
        npSecond = findViewById(R.id.npSecond);

        npHour.setMinValue(0);
        npHour.setMaxValue(23);
        npMinute.setMinValue(0);
        npMinute.setMaxValue(59);
        npSecond.setMinValue(0);
        npSecond.setMaxValue(59);

        // Welcome message
        String email = getIntent().getStringExtra("email");
        setWelcomeMessage(email);

        // Buttons
        btnStartStudy.setOnClickListener(v -> startStudyTimer());
        btnStopReset.setOnClickListener(v -> stopAndResetTimer());
        btnLogout.setOnClickListener(v -> logoutUser());

        updateTimerDisplay();
    }

    private void setWelcomeMessage(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT firstName FROM users WHERE LOWER(email) = LOWER(?)", new String[]{email});

        if (cursor.moveToFirst()) {
            String firstName = cursor.getString(0);
            tvWelcome.setText("Welcome, " + firstName + "!");
        } else {
            tvWelcome.setText("Welcome!");
        }

        cursor.close();
    }

    private void startStudyTimer() {
        if (!isTimerRunning) {
            // Get time from NumberPickers
            int hours = npHour.getValue();
            int minutes = npMinute.getValue();
            int seconds = npSecond.getValue();
            timeLeftInMillis = (hours * 3600 + minutes * 60 + seconds) * 1000L;

            if (timeLeftInMillis == 0) {
                tvTimer.setText("00:00:00");
                return;
            }

            countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftInMillis = millisUntilFinished;
                    updateTimerDisplay();
                }

                @Override
                public void onFinish() {
                    isTimerRunning = false;
                    tvTimer.setText("Done!");
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

        // Reset values
        npHour.setValue(0);
        npMinute.setValue(0);
        npSecond.setValue(0);
        timeLeftInMillis = 0;
        updateTimerDisplay();
    }

    private void updateTimerDisplay() {
        int hours = (int) (timeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((timeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        tvTimer.setText(timeFormatted);
    }

    private void logoutUser() {
        Intent intent = new Intent(StudentDashboardActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
}

