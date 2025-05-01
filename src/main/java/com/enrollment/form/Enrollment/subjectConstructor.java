package com.enrollment.form.Enrollment;

import jakarta.persistence.Entity;
import java.util.List;

@Entity
public class subjectConstructor {
    private double gwa;
    private String subjectCode;
    private String subjectName; // Keep this as the primary subject name field
    private int subjectID;
    private List<String> failedSubjects; // List to store failed subjects
    private String status; // Final status based on grade evaluation

    // Default constructor
    public subjectConstructor() {
    }

    // Parameterized constructor for initialization
    public subjectConstructor(double gwa, String subjectName, String subjectCode, int subjectID, List<String> failedSubjects, String status) {
        this.gwa = gwa;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.subjectID = subjectID;
        this.failedSubjects = failedSubjects;
        this.status = status;
    }

    // Getter and Setter methods
    public double getGwa() {
        return gwa;
    }

    public void setGwa(double gwa) {
        this.gwa = gwa;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public List<String> getFailedSubjects() {
        return failedSubjects;
    }

    public void setFailedSubjects(List<String> failedSubjects) {
        this.failedSubjects = failedSubjects;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "subjectConstructor{" +
                "gwa=" + gwa +
                ", subjectName='" + subjectName + '\'' +
                ", subjectCode='" + subjectCode + '\'' +
                ", subjectID=" + subjectID +
                ", failedSubjects=" + failedSubjects +
                ", status='" + status + '\'' +
                '}';
    }
}
