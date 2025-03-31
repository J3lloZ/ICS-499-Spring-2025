package com.example.ics449app;

public class Subject {
    private String SubjectID;
    private String schoolCode;
    private String SubjectName;
    Subject(String SubjectID, String SubjectName, String schoolCode){
        this.SubjectID=SubjectID;
        this.schoolCode=schoolCode;
        this.SubjectName=SubjectName;
    }

    public String getSubjectID() {
        return SubjectID;
    }

    public void setSubjectID(String subjectID) {
        SubjectID = subjectID;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String SchoolCode) {
        schoolCode = SchoolCode;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }
}