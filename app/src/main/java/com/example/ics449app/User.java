package com.example.ics449app;

// Abstract class User
public abstract class User {
    private String userID;
    private String Fname;
    private String Lname;
    private String email;
    private String password;
    private String role;

    // Constructor
    public User(String userID, String Fname, String Lname, String email, String password, String role) {
        this.userID = userID;
        this.Fname = Fname;
        this.Lname = Lname;
        this.email = email;
        this.password = password;
        this.role=role;
    }

    // Getters and Setters
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String Fname) {
        this.Fname = Fname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String Lname) {
        this.Lname = Lname;
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
}