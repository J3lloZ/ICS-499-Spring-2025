package com.example.ics449app;

public class Student extends User {
    private String schoolCode;
    private boolean isLocked;

    public Student(String userID, String firstName, String lastName, String email, String password, String role, String schoolCode) {
        super(userID, firstName, lastName, email, password, role);
        this.schoolCode=schoolCode;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        schoolCode = schoolCode;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setisLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }
}