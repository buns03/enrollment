package com.enrollment.form.Enrollment;

import jakarta.persistence.Entity;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Entity
public class StudentCOnstructor {

    @Id
    private String YrLvl;
    private int StudentID;
    private int id;
    private String LName;
    private String FName;
    private String MName;
    private String Suffix;
    private int age;
    private String sex;
    private LocalDate dob;
    private String course;
    private String Email;
    private String mnumber;
    private String nationality;
    private String address;
    private String mother;
    private String monum;
    private String mooccu;
    private String moaddress;
    private String moemail;
    private String father;
    private String fnum;
    private String foccu;
    private String faddress;
    private String femail;
    private String guardian;
    private String gnum;
    private String gaddress;
    private String gemail;
    private String SchoolName;
    private String SchoolAddress;
    private String Strand;
    private LocalDate DateGraduate;
    private String Achievements;
    private double downPayment;
    private LocalDate Schedule;
    private double Average;
    private String Status;
    private String Section;
    private String fullName;
    private String subject;
    private double grade;

    public StudentCOnstructor(int StudentID, String LName, String FName, String course) {
        this.StudentID = StudentID;
        this.LName = LName;
        this.FName = FName;
        this.course = course;
    }

    public StudentCOnstructor() {

    }

    public StudentCOnstructor(String yrLvl, int StudentID, int id, String LName, String FName, String MName, String suffix, int age, String sex, LocalDate dob, String course, String email, String mnumber, String nationality, String address, String mother, String monum, String mooccu, String moaddress, String moemail, String father, String fnum, String foccu, String faddress, String femail, String guardian, String gnum, String gaddress, String gemail, String schoolName, String schoolAddress, String strand, double average, LocalDate dateGraduate, String achievements, double downPayment, LocalDate schedule, String Section, String fullName, String Subject, double grade) {
        YrLvl = yrLvl;
        this.StudentID = StudentID;
        this.id = id;
        this.LName = LName;
        this.FName = FName;
        this.MName = MName;
        Suffix = suffix;
        this.age = age;
        this.sex = sex;
        this.dob = dob;
        this.course = course;
        Email = email;
        this.mnumber = mnumber;
        this.nationality = nationality;
        this.address = address;
        this.mother = mother;
        this.monum = monum;
        this.mooccu = mooccu;
        this.moaddress = moaddress;
        this.moemail = moemail;
        this.father = father;
        this.fnum = fnum;
        this.foccu = foccu;
        this.faddress = faddress;
        this.femail = femail;
        this.guardian = guardian;
        this.gnum = gnum;
        this.gaddress = gaddress;
        this.gemail = gemail;
        SchoolName = schoolName;
        SchoolAddress = schoolAddress;
        Strand = strand;
        Average = average;
        DateGraduate = dateGraduate;
        Achievements = achievements;
        this.downPayment = downPayment;
        Schedule = schedule;
        this.Section = Section;
        this.fullName = fullName;
        this.subject = subject;
        grade = grade;
    }

    public String getYrLvl() {
        return YrLvl;
    }

    public void setYrLvl(String yrLvl) {
        YrLvl = yrLvl;
    }

    public int getStudentID() {
        return StudentID;
    }

    public void setStudentID(int studentID) {
        StudentID = studentID;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getMName() {
        return MName;
    }

    public void setMName(String MName) {
        this.MName = MName;
    }

    public String getSuffix() {
        return Suffix;
    }

    public void setSuffix(String suffix) {
        Suffix = suffix;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMnumber() {
        return mnumber;
    }

    public void setMnumber(String mnumber) {
        this.mnumber = mnumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getMonum() {
        return monum;
    }

    public void setMonum(String monum) {
        this.monum = monum;
    }

    public String getMooccu() {
        return mooccu;
    }

    public void setMooccu(String mooccu) {
        this.mooccu = mooccu;
    }

    public String getMoaddress() {
        return moaddress;
    }

    public void setMoaddress(String moaddress) {
        this.moaddress = moaddress;
    }

    public String getMoemail() {
        return moemail;
    }

    public void setMoemail(String moemail) {
        this.moemail = moemail;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getFnum() {
        return fnum;
    }

    public void setFnum(String fnum) {
        this.fnum = fnum;
    }

    public String getFoccu() {
        return foccu;
    }

    public void setFoccu(String foccu) {
        this.foccu = foccu;
    }

    public String getFaddress() {
        return faddress;
    }

    public void setFaddress(String faddress) {
        this.faddress = faddress;
    }

    public String getFemail() {
        return femail;
    }

    public void setFemail(String femail) {
        this.femail = femail;
    }

    public String getGuardian() {
        return guardian;
    }

    public void setGuardian(String guardian) {
        this.guardian = guardian;
    }

    public String getGnum() {
        return gnum;
    }

    public void setGnum(String gnum) {
        this.gnum = gnum;
    }

    public String getGaddress() {
        return gaddress;
    }

    public void setGaddress(String gaddress) {
        this.gaddress = gaddress;
    }

    public String getGemail() {
        return gemail;
    }

    public void setGemail(String gemail) {
        this.gemail = gemail;
    }

    public String getSchoolName() {
        return SchoolName;
    }

    public void setSchoolName(String schoolName) {
        SchoolName = schoolName;
    }

    public String getSchoolAddress() {
        return SchoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        SchoolAddress = schoolAddress;
    }

    public String getStrand() {
        return Strand;
    }

    public void setStrand(String strand) {
        Strand = strand;
    }

    public double getAverage() {
        return Average;
    }

    public void setAverage(double average) {
        Average = average;
    }

    public LocalDate getDateGraduate() {
        return DateGraduate;
    }

    public void setDateGraduate(LocalDate dateGraduate) {
        DateGraduate = dateGraduate;
    }

    public String getAchievements() {
        return Achievements;
    }

    public void setAchievements(String achievements) {
        Achievements = achievements;
    }

    public double getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(double downPayment) {
        this.downPayment = downPayment;
    }

    public LocalDate getSchedule() {
        return Schedule;
    }

    public void setSchedule(LocalDate schedule) {
        Schedule = schedule;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String section) {
        Section = section;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "StudentCOnstructor{" +
                "YrLvl='" + YrLvl + '\'' +
                ", StudentID=" + StudentID +
                ", id=" + id +
                ", LName='" + LName + '\'' +
                ", FName='" + FName + '\'' +
                ", MName='" + MName + '\'' +
                ", Suffix='" + Suffix + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", dob=" + dob +
                ", course='" + course + '\'' +
                ", Email='" + Email + '\'' +
                ", mnumber='" + mnumber + '\'' +
                ", nationality='" + nationality + '\'' +
                ", address='" + address + '\'' +
                ", mother='" + mother + '\'' +
                ", monum='" + monum + '\'' +
                ", mooccu='" + mooccu + '\'' +
                ", moaddress='" + moaddress + '\'' +
                ", moemail='" + moemail + '\'' +
                ", father='" + father + '\'' +
                ", fnum='" + fnum + '\'' +
                ", foccu='" + foccu + '\'' +
                ", faddress='" + faddress + '\'' +
                ", femail='" + femail + '\'' +
                ", guardian='" + guardian + '\'' +
                ", gnum='" + gnum + '\'' +
                ", gaddress='" + gaddress + '\'' +
                ", gemail='" + gemail + '\'' +
                ", SchoolName='" + SchoolName + '\'' +
                ", SchoolAddress='" + SchoolAddress + '\'' +
                ", Strand='" + Strand + '\'' +
                ", Average=" + Average +
                ", DateGraduate=" + DateGraduate +
                ", Achievements='" + Achievements + '\'' +
                ", downPayment=" + downPayment +
                ", Schedule=" + Schedule +
                ", Status=" + Status +
                ", Section=" + Section +
                ", fullName=" + fullName +
                ", subject=" + subject +
                ", grade=" + grade +
                '}';
    }
}
