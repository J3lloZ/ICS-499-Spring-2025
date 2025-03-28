package com.example.ics449app;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
//import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class StudentDashboardActivity extends AppCompatActivity {
    private TextView tvTimer;
    private CountDownTimer countDownTimer;
    private boolean isTimerRunning = false;
    private long timeLeftInMillis = 60000; // 1 minute

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        tvTimer = findViewById(R.id.tvTimer);
        Button btnStartStudy = findViewById(R.id.btnStartStudy);
        Button btnStopReset = findViewById(R.id.btnStopReset);
        Button btnLogout = findViewById(R.id.btnLogout);

        updateTimerDisplay();

        btnStartStudy.setOnClickListener(v -> startStudyTimer());
        btnStopReset.setOnClickListener(v -> stopAndResetTimer());
        btnLogout.setOnClickListener(v -> logoutUser());
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
                    tvTimer.setText(getString(R.string.timer_done)); // Use string resource
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
        tvTimer.setText(getString(R.string.timer_format, seconds)); // Use string resource
    }

    private void logoutUser() {
        Intent intent = new Intent(StudentDashboardActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
}

