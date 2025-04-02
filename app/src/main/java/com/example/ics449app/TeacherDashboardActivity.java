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
    private Button btnAddStudent, btnLockStudent;
    private Student lastSelectedStudent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> logoutUser());

        btnAddStudent = findViewById(R.id.btnAddStudent);
        searchStudent = findViewById(R.id.searchStudent);
        studentListView = findViewById(R.id.studentListView);
        selectedStudentsGrid = findViewById(R.id.selectedStudentsGrid);
        btnLockStudent = findViewById(R.id.btnLockStudent);

        dbHelper = SQLiteHelper.instanceOfDatabase(this);

        // Get the teacher's school code form intent or session
        Intent intent = getIntent();
        teacherSchoolCode = intent.getStringExtra("schoolCode");    // Passed from login

        if(teacherSchoolCode == null){
            Log.e("TeacherDashboard", "No schoolCode received");
        }else {
            Log.e("TeacherDashboard", "schoolCode received " + teacherSchoolCode);
        }

        // Load students from the same school
        loadStudents();

        // Initialize adapter for selected students grid
        selectedStudentsAdapter = new StudentGridAdapter(this, selectedStudents);
        selectedStudentsGrid.setAdapter(selectedStudentsAdapter);

        // Handle student selection
        studentListView.setOnItemClickListener((parent, view, position, id) -> {
            Student selectedStudent = students.get(position);
            if(!selectedStudents.contains(selectedStudent)) {
                selectedStudents.add(selectedStudent);
                selectedStudentsAdapter.notifyDataSetChanged();
            }
        });

        // Add student button on click
        btnAddStudent.setOnClickListener(v -> {
            if(!selectedStudents.isEmpty()) {
                for (Student selectedStudent : selectedStudents) {
                    boolean added = dbHelper.addStudentToClass(selectedStudent.getEmail(), teacherSchoolCode);
                    if (added) {
                        Toast.makeText(this, "Student added to class!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to add student.", Toast.LENGTH_SHORT).show();
                    }
                }
                // Clear selected students after adding
                selectedStudents.clear();
                selectedStudentsAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Please select a student.", Toast.LENGTH_SHORT).show();
            }
        });

        // Search student
        searchStudent.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()) {
                    studentListView.setVisibility(View.GONE);
                } else {
                    studentListView.setVisibility(View.VISIBLE);
                    studentAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });

        // Lock Student button on click
        btnLockStudent.setOnClickListener(v -> {
            if(!selectedStudents.isEmpty()) {
                for(Student student : selectedStudents) {
                    sendLockCommandToStudent(student.getEmail());
                }
                Toast.makeText(this, "Selected students locked!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please select student.", Toast.LENGTH_SHORT).show();
            }
        });

        btnLogout.setOnClickListener(v -> logoutUser());
    }

    private void loadStudents() {
        students = dbHelper.getStudentsBySchool(teacherSchoolCode);  // Initialize class varible
        List<String> studentNames = new ArrayList<>();  // Create a new list for student names

        for (Student student : students) {
            studentNames.add(student.getFirstName() + " " + student.getLastName());
        }

        studentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentNames);
        studentListView.setAdapter(studentAdapter);

        // Hide student List initially
        studentListView.setVisibility(View.GONE);
    }

    private void logoutUser() {
        Intent intent = new Intent(TeacherDashboardActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    public void sendLockCommandToStudent(String studentEmail) {

    }

}

