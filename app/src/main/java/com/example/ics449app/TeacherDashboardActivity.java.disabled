package com.example.ics449app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TeacherDashboardActivity extends AppCompatActivity {
    private SQLiteHelper dbHelper;
    private ListView studentListView;
    private GridView selectedStudentsGrid;
    private ArrayAdapter<String> studentAdapter;
    private SearchView searchStudent;
    private List<Student> students = new ArrayList<>();
    private List<Student> selectedStudents = new ArrayList<>();
    private StudentGridAdapter selectedStudentsAdapter;
    private String teacherSchoolCode;
    private Button btnAddStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);


        Intent intent = getIntent();
        String teacherId = intent.getStringExtra("teacherId");
        String email = intent.getStringExtra("email");

        // Initialize DB and UI elements
        dbHelper = SQLiteHelper.instanceOfDatabase(this);

        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> logoutUser());

        Button btnViewStudents = findViewById(R.id.btnViewStudents);
        btnViewStudents.setOnClickListener(v -> {
            Intent viewIntent = new Intent(TeacherDashboardActivity.this, ViewStudentsActivity.class);
            viewIntent.putExtra("teacherId", teacherId);
            startActivity(viewIntent);
        });

        btnAddStudent = findViewById(R.id.btnAddStudent);
        searchStudent = findViewById(R.id.searchStudent);
        studentListView = findViewById(R.id.studentListView);
        selectedStudentsGrid = findViewById(R.id.selectedStudentsGrid);


        dbHelper = SQLiteHelper.instanceOfDatabase(this);

        // Show welcome message
        TextView tvWelcome = findViewById(R.id.tvWelcomeTeacher);
        teacherSchoolCode = intent.getStringExtra("schoolCode");

        String firstName = dbHelper.getFirstNameByEmail(email);  // You already have this method
        tvWelcome.setText("Welcome, " + firstName);

        loadStudents();

        selectedStudentsAdapter = new StudentGridAdapter(this, selectedStudents);
        selectedStudentsGrid.setAdapter(selectedStudentsAdapter);

        studentListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedName = (String) parent.getItemAtPosition(position);
            for (Student student : students) {
                String fullName = student.getFirstName() + " " + student.getLastName();
                if (fullName.equals(selectedName)) {
                    if (!selectedStudents.contains(student)) {
                        selectedStudents.add(student);
                        selectedStudentsAdapter.notifyDataSetChanged();
                        selectedStudentsGrid.setVisibility(View.VISIBLE);
                    }
                    break;
                }
            }
        });

        btnAddStudent.setOnClickListener(v -> {
            if (!selectedStudents.isEmpty()) {
                for (Student selectedStudent : selectedStudents) {
                    boolean added = dbHelper.addStudentToClass(selectedStudent.getEmail(), teacherSchoolCode);
                    if (added) {
                        Toast.makeText(this, "Student added to class!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to add student.", Toast.LENGTH_SHORT).show();
                    }
                }
                selectedStudents.clear();
                selectedStudentsAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Please select a student.", Toast.LENGTH_SHORT).show();
            }
        });

        searchStudent.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) { return false; }
            @Override public boolean onQueryTextChange(String newText) {
                studentListView.setVisibility(newText.isEmpty() ? View.GONE : View.VISIBLE);
                studentAdapter.getFilter().filter(newText);
                selectedStudentsAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    private void loadStudents() {
        students = dbHelper.getStudentsBySchool(teacherSchoolCode);
        List<String> studentNames = new ArrayList<>();
        for (Student student : students) {
            studentNames.add(student.getFirstName() + " " + student.getLastName());
        }
        studentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentNames);
        studentListView.setAdapter(studentAdapter);
        studentListView.setVisibility(View.GONE);
    }

    private void logoutUser() {
        Intent intent = new Intent(TeacherDashboardActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
}

