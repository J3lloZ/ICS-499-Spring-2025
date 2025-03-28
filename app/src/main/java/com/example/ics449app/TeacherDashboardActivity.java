package com.example.ics449app;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TeacherDashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        TextView tvMessage = findViewById(R.id.tvTeacherWelcome);
        tvMessage.setText("Welcome to the Teacher Dashboard!");
    }
}

