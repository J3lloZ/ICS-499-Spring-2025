package com.example.ics449app;

public class School {
    private String schoolCode;
    private String SchoolName;
    private String address;
    private String contact_email;

    School(String SchoolCode, String schoolName,String address,String contact_email){
        this.schoolCode=SchoolCode;
        this.SchoolName=schoolName;
        this.address=address;
        this.contact_email=contact_email;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolID(String SchoolCode) {
        schoolCode = SchoolCode;
    }

    public String getSchoolName() {
        return SchoolName;
    }

    public void setSchoolName(String schoolName) {
        SchoolName = schoolName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact_email() {
        return contact_email;
    }

    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }
}