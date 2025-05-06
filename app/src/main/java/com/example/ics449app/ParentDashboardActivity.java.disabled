package com.example.ics449app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ParentDashboardActivity extends AppCompatActivity {

    private TextView tvTimer, tvWelcome;
    private CountDownTimer countDownTimer;
    private boolean isTimerRunning = false;
    private long timeLeftInMillis;

    private NumberPicker npHour, npMinute, npSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dashboard);

        tvTimer = findViewById(R.id.tvTimer);
        tvWelcome = findViewById(R.id.tvWelcome);
        npHour = findViewById(R.id.npHour);
        npMinute = findViewById(R.id.npMinute);
        npSecond = findViewById(R.id.npSecond);

        Button btnStartStudy = findViewById(R.id.btnStartStudy);
        Button btnStopReset = findViewById(R.id.btnStopReset);
        Button btnLogout = findViewById(R.id.btnLogout);
        Spinner spinnerChildren = findViewById(R.id.spinnerChildren);

        // Setup NumberPickers
        npHour.setMinValue(0);
        npHour.setMaxValue(23);

        npMinute.setMinValue(0);
        npMinute.setMaxValue(59);

        npSecond.setMinValue(0);
        npSecond.setMaxValue(59);

        // Setup welcome message
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

        // Dummy child data for spinner
        String[] children = {"Child 1", "Child 2", "Child 3"};
        spinnerChildren.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, children));

        btnStartStudy.setOnClickListener(v -> startStudyTimer());
        btnStopReset.setOnClickListener(v -> stopAndResetTimer());
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(ParentDashboardActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        });

        updateTimerDisplay(0);
    }

    private void startStudyTimer() {
        if (!isTimerRunning) {
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
                    updateTimerDisplay(timeLeftInMillis);
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
        updateTimerDisplay(0);
    }

    private void updateTimerDisplay(long millis) {
        int hours = (int) (millis / 1000) / 3600;
        int minutes = (int) ((millis / 1000) % 3600) / 60;
        int seconds = (int) (millis / 1000) % 60;

        String timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        tvTimer.setText(timeFormatted);
    }
}

