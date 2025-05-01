package com.enrollment.form.Enrollment;

public class AdminConstructor {

    private Integer studentNo;
    private String fName;
    private String lName;
    private String section;
    private String subject;
    private Double grade;

    public AdminConstructor() {
    }

    public AdminConstructor(Integer studentNo, String fName, String lName, String section, String subject, Double grade) {
        this.studentNo = studentNo;
        this.fName = fName;
        this.lName = lName;
        this.section = section;
        this.subject = subject;
        this.grade = grade;
    }

    public Integer getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(Integer studentNo) {
        this.studentNo = studentNo;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "AdminConstructor{" +
                "studentNo=" + studentNo +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", section='" + section + '\'' +
                ", subject='" + subject + '\'' +
                ", grade=" + grade +
                '}';
    }
}
