package com.example.ics449app;
import java.util.*;
public class Teacher extends User{
    private String schoolCode;
    private Student[] Studentlist;
    private List<String> ClassList = new ArrayList<String>();

    public Teacher(String userID, String Fname, String Lname, String email, String password, String role) {
        super(userID, Fname, Lname, email, password, role);
    }
}