package com.example.ics449app;

public class Parent extends User {
    private Student[] Childlist;

    public Parent(String userID, String Fname, String Lname, String email, String password, String role) {
        super(userID, Fname, Lname, email, password, role);
    }

    public Student[] getChildlist() {
        return Childlist;
    }

    public void setChildlist(Student[] childlist) {
        Childlist = childlist;
    }
}
