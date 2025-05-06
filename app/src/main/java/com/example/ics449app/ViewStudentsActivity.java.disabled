package com.example.ics449app;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;
    private List<Student> studentList = new ArrayList<>();
    private SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);

        // Initialize RecyclerView and SQLiteHelper
        recyclerView = findViewById(R.id.recyclerViewStudents);
        sqLiteHelper = SQLiteHelper.instanceOfDatabase(this); // Make sure this is correct

        // Fetch teacher's id from the Intent
        String teacherId = getIntent().getStringExtra("teacherId");


        // Fetch students by teacher's email
        studentList = sqLiteHelper.getStudentsByTeacher(teacherId);

        // Log the student list to check if it's populated
        Log.d("ViewStudentsActivity", "Student List: " + studentList.size());

        Log.d("ViewStudentsActivity", "Student List size: " + studentList.size());
        for (Student student : studentList) {
            Log.d("ViewStudentsActivity", "Student: " + student.getFirstName() + " " + student.getLastName());
        }


        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set the adapter
        studentAdapter = new StudentAdapter(this, studentList);
        recyclerView.setAdapter(studentAdapter);
        studentAdapter.notifyDataSetChanged();
    }
}
