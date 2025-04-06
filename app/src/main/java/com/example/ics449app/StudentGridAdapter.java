package com.example.ics449app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class StudentGridAdapter extends BaseAdapter {
    private Context context;
    private List<Student> selectedStudents;

    public StudentGridAdapter(Context context, List<Student> selectedStudents) {
        this.context = context;
        this.selectedStudents = selectedStudents;
    }

    @Override
    public int getCount() {
        return selectedStudents.size();
    }

    @Override
    public Object getItem(int position) {
        return selectedStudents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the view and set the data (e.g., student name)
        // Ensure this is correctly showing the student in the grid
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_student, parent, false);
        }

        // Get the student for this position
        Student student = selectedStudents.get(position);

        // Get the TextView from the layout and set the student name
        TextView textView = convertView.findViewById(R.id.studentName);
        textView.setText(student.getFirstName() + " " + student.getLastName());

        return convertView;
    }
}
