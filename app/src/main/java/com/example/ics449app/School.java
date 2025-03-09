package com.example.ics449app;

public class School {
    private String schoolCode;
    private String Schoolname;
    private String address;
    private String contact_email;

    School(String SchoolCode, String schoolname,String address,String contact_email){
        this.schoolCode=SchoolCode;
        this.Schoolname=schoolname;
        this.address=address;
        this.contact_email=contact_email;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolID(String SchoolCode) {
        schoolCode = SchoolCode;
    }

    public String getSchoolname() {
        return Schoolname;
    }

    public void setSchoolname(String schoolname) {
        Schoolname = schoolname;
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