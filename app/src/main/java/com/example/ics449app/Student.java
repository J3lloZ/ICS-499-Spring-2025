package com.example.ics449app;

public class Student extends User {
    private boolean isLocked;
    private String studentId;

    public Student(String userID, String studentId, String firstName, String lastName, String email, String password, String role, String schoolCode) {
        super(userID, firstName, lastName, email, password, role, schoolCode);
        this.studentId = studentId;
    }
    public Student(String studentId, String firstName, String lastName, String email, String password, String role, String schoolCode) {
        super(studentId, firstName, lastName, email, password, role, schoolCode);
        this.studentId = studentId;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setisLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId() {
        this.studentId = studentId;
    }
}