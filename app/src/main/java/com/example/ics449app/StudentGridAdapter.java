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
    private LayoutInflater inflater;

    public StudentGridAdapter(Context context, List<Student> selectedStudents) {
        this.context = context;
        this.selectedStudents = selectedStudents;
        this.inflater = LayoutInflater.from(context);
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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item_student, parent, false);
        }

        TextView studentName = convertView.findViewById(R.id.studentName);
        Student student = selectedStudents.get(position);
        studentName.setText(student.getFirstName() + " " + student.getLastName());

        return convertView;
    }
}
