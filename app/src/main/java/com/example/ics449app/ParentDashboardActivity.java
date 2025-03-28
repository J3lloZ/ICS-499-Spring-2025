package com.example.ics449app;

import android.os.Bundle;
import android.os.CountDownTimer;
//import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ParentDashboardActivity extends AppCompatActivity {
    private TextView tvTimer;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 60000; // 1 minute
    private boolean isTimerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dashboard);

        Spinner spinnerChildren = findViewById(R.id.spinnerChildren);
        tvTimer = findViewById(R.id.tvTimer);
        Button btnStartStudy = findViewById(R.id.btnStartStudy);
        Button btnStopReset = findViewById(R.id.btnStopReset);
        Button btnLogout = findViewById(R.id.btnLogout);

        // Populate spinner with children's names
        String[] children = {"Child 1", "Child 2", "Child 3"}; // Replace with dynamic list from database
        spinnerChildren.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, children));

        // Button Listeners
        btnStartStudy.setOnClickListener(v -> startStudyTimer());
        btnStopReset.setOnClickListener(v -> stopAndResetTimer());
        btnLogout.setOnClickListener(v -> logoutUser());

        // Initial Timer Display
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
                    tvTimer.setText(getString(R.string.timer_done)); // Using string resource
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
        timeLeftInMillis = 60000; // Reset to 1 minute
        updateTimerDisplay();
    }

    private void updateTimerDisplay() {
        int seconds = (int) (timeLeftInMillis / 1000);
        tvTimer.setText(getString(R.string.timer_format, seconds)); // Using string resource
    }

    private void logoutUser() {
        finish(); // Close Parent Dashboard and return to login screen
    }
}

