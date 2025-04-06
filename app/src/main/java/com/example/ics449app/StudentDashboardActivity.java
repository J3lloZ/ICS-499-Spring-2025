package com.example.ics449app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class StudentDashboardActivity extends AppCompatActivity {
    private TextView tvTimer, tvWelcome;
    private CountDownTimer countDownTimer;
    private boolean isTimerRunning = false;
    private long timeLeftInMillis = 60000; // 1 minute
    private SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        dbHelper = SQLiteHelper.instanceOfDatabase(this);

        tvTimer = findViewById(R.id.tvTimer);
        tvWelcome = findViewById(R.id.tvWelcome); // Reference welcome text

        Button btnStartStudy = findViewById(R.id.btnStartStudy);
        Button btnStopReset = findViewById(R.id.btnStopReset);
        Button btnLogout = findViewById(R.id.btnLogout);

        updateTimerDisplay();

        btnStartStudy.setOnClickListener(v -> startStudyTimer());
        btnStopReset.setOnClickListener(v -> stopAndResetTimer());
        btnLogout.setOnClickListener(v -> logoutUser());

        // Get email passed from SignInActivity
        String email = getIntent().getStringExtra("email");
        setWelcomeMessage(email);  //  Call function to show name
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
        Intent intent = new Intent(StudentDashboardActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
}

