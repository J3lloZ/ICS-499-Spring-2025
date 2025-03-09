package com.example.ics449app;

public class Subject {
    private String SubjectID;
    private String schoolCode;
    private Subject[] subjectList;
    Subject(String SubjectID, String schoolCode){
        this.SubjectID=SubjectID;
        this.schoolCode=schoolCode;

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

    public Subject[] getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(Subject[] subjectList) {
        this.subjectList = subjectList;
    }
}