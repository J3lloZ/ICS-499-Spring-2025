package com.example.ics449app;

// Abstract class User
public abstract class User {
    private String userID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private String schoolCode;

    // Constructor
    public User(String userID, String firstName, String lastName, String email, String password, String role, String schoolCode) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.schoolCode = schoolCode;
    }

    public User(String studentID, String firstName, String lastName) {
    }

    // Getters and Setters
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String schoolCode) {
        this.role = role;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode() {
        this.schoolCode = schoolCode;
    }
}