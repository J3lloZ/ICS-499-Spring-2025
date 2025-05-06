package com.example.ics449app;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class DashboardActivity extends AppCompatActivity {
    private TextView welcomeText, tvTimer;
    private FirebaseFirestore db;

    private CountDownTimer countDownTimer;
    private boolean isTimerRunning = false;
    private long timeLeftInMillis = 0;

    private NumberPicker npHour, npMinute, npSecond;
    private DeviceAdminHelper mDeviceAdminHelper;
    private OnBackPressedCallback backPressedCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.dashboard_activity);

        // Firebase
        welcomeText = findViewById(R.id.welcomeText);
        db = FirebaseFirestore.getInstance();
        String email = getIntent().getStringExtra("email");
        if (email != null) {
            fetchFirstName(email);
        } else {
            welcomeText.setText("Welcome!");
        }

        // Timer UI
        tvTimer = findViewById(R.id.tvTimer);
        Button btnStartStudy = findViewById(R.id.btnStartStudy);
        Button btnStopReset = findViewById(R.id.btnStopReset);
        Button btnLogout = findViewById(R.id.btnLogout);

        npHour = findViewById(R.id.npHour);
        npMinute = findViewById(R.id.npMinute);
        npSecond = findViewById(R.id.npSecond);

        npHour.setMinValue(0);
        npHour.setMaxValue(23);
        npMinute.setMinValue(0);
        npMinute.setMaxValue(59);
        npSecond.setMinValue(0);
        npSecond.setMaxValue(59);

        mDeviceAdminHelper = new DeviceAdminHelper(this);

        btnStartStudy.setOnClickListener(v -> startStudyTimer());
        btnStopReset.setOnClickListener(v -> stopAndResetTimer());
        btnLogout.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        });

        updateTimerDisplay();
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

    private void startStudyTimer() {
        if (!isTimerRunning) {
            int hours = npHour.getValue();
            int minutes = npMinute.getValue();
            int seconds = npSecond.getValue();
            timeLeftInMillis = (hours * 3600 + minutes * 60 + seconds) * 1000L;

            mDeviceAdminHelper.startLockTask(this);
            applyRestrictions();

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
                    removeRestrictions();
                    mDeviceAdminHelper.stopLockTask(DashboardActivity.this);
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

        npHour.setValue(0);
        npMinute.setValue(0);
        npSecond.setValue(0);
        timeLeftInMillis = 0;
        updateTimerDisplay();
        mDeviceAdminHelper.stopLockTask(this);
    }

    private void updateTimerDisplay() {
        int hours = (int) (timeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((timeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        tvTimer.setText(timeFormatted);
    }

    private void applyRestrictions() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        backPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Disabled
            }
        };
        getOnBackPressedDispatcher().addCallback(this, backPressedCallback);
    }

    private void removeRestrictions() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        if (backPressedCallback != null) {
            backPressedCallback.remove();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isTimerRunning) {
            stopAndResetTimer();
            removeRestrictions();
            mDeviceAdminHelper.stopLockTask(this);
        }
    }
}
