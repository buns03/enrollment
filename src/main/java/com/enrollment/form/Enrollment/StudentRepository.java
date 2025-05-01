package com.enrollment.form.Enrollment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository {
    private final JdbcTemplate template;
    private DataSource dataSource;

    @Autowired
    public StudentRepository(JdbcTemplate template) {
        this.template = template;
    }

    public int add(String LName, String FName, String MName, String Suffix, int age, String sex,
                   LocalDate dob, String Course, String Email, String mnumber, String Nationality, String Address) {

        // Insert into studentinfo first
        String studentSql = "INSERT INTO studentinfo (LName, FName, MName, Suffix, Age, Sex, Dob, Course, Email, Number, Nationality, " +
                "Address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update((Connection con) -> {
            PreparedStatement ps = con.prepareStatement(studentSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, LName);
            ps.setString(2, FName);
            ps.setString(3, MName);
            ps.setString(4, Suffix);
            ps.setInt(5, age);
            ps.setString(6, sex);
            ps.setObject(7, dob);
            ps.setString(8, Course);
            ps.setString(9, Email);
            ps.setString(10, mnumber);
            ps.setString(11, Nationality);
            ps.setString(12, Address);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public int addParent(int StudentID, String mother, String monum, String mooccu, String moaddress, String moemail,
                         String father, String fnum, String foccu, String faddress, String femail,
                         String guardian, String gnum, String gaddress, String gemail) {
        String parentSql = "INSERT INTO parentinfo (StudentID, mother, monum, mooccu, moaddress, moemail, father, fnum, foccu, faddress, " +
                "femail, guardian, gnum, gaddress, gemail) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return template.update(parentSql, StudentID, mother, monum, mooccu, moaddress, moemail, father, fnum, foccu, faddress,
                femail, guardian, gnum, gaddress, gemail);

    }

    // Insert Past School Info with studentId
    public int addPastSchoolInfo(int StudentID, String LName, String FName, String SchoolName, String Strand, double Average, String Achievements, LocalDate DateGraduation) {
        String schoolSql = "INSERT INTO pastSchool (StudentID, LName, FName, SchoolName, Strand, Average, Achievements, DateGraduation) VALUES (?, ?, ?, ?, ?, ?,?,?)";
        return template.update(schoolSql, StudentID, LName, FName, SchoolName, Strand, Average, Achievements, DateGraduation);
    }

    // Insert Schedule Info with studentId
    public int addSchedule(int StudentID, String LName, String FName, String YrLvl, String Course, String status,
                           double downPayment, LocalDate scheduleDate) {
//            String scheduleSql = "INSERT INTO schedules (StudentID, LName, FName, downPayment, Schedule) VALUES (?, ?,?,?,?)";

        String scheduleSql = "INSERT INTO manageschedule (StudentID, LName, FName, YrLvl, Course, Status, Schedule, downPayment) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        status = "Regular";
        return template.update(scheduleSql, StudentID, LName, FName, YrLvl, Course, status, scheduleDate, downPayment);
    }

    public boolean validateStudent(int StudentID, String FName, String LName, String Course) {
        String sql = "SELECT COUNT(*) FROM enrolledstudents WHERE StudentNo = ? AND FName = ? AND LName = ? AND Course = ?";
        Integer count = template.queryForObject(sql, Integer.class, StudentID, LName, FName, Course);
        return count != null && count == 1;
    }

    public Optional<StudentCOnstructor> findStudentID(int StudentID, String LName, String FName, String Course) {
        String sql = "SELECT * FROM enrolledstudents WHERE StudentNo = ? AND LName = ? AND FName = ? AND Course = ?";

        try {
            StudentCOnstructor student = template.queryForObject(sql, new Object[]{StudentID, LName, FName, Course},
                    (rs, rowNum) -> {
                        StudentCOnstructor s = new StudentCOnstructor();
                        s.setStudentID(rs.getInt("StudentNo"));
                        s.setLName(rs.getString("LName"));
                        s.setFName(rs.getString("FName"));
                        s.setCourse(rs.getString("Course"));
                        return s;
                    });
            return Optional.of(student);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty(); // No match found
        }
    }

    public StudentCOnstructor fetchStudentID(String StudentID) {
        return template.queryForObject(
                "SELECT StudentNo, LName, FName, Course " +
                        "FROM enrolledstudents WHERE StudentNo = ?",
                new Object[]{StudentID},
                (rs, rowNum) -> {
                    // Assuming StudentConstructor is already set up to handle these fields
                    return new StudentCOnstructor(
                            rs.getInt("StudentNo"),
                            rs.getString("LName"),
                            rs.getString("FName"),
                            rs.getString("course")
                    );
                }
        );
    }


//    public List<subjectConstructor> getSubjectsByStudentId(String StudentID) {
//        String sql = "Select ss.subjectcode, s.subjectName as subject, sg.grade from studentsubject as ss " +
//                "Join subject as s on ss.subjectcode = s.subjectcode " +
//                "left join studentsgrades as sg on ss.StudentNo = sg.StudentNo and ss.SubjectCode = sg.subjectCode " +
//                "where ss.studentNo=?";
//
//        return template.query(sql, new Object[]{StudentID}, (rs, rowNum) -> {
//            subjectConstructor s = new subjectConstructor();
//            s.setSubject(rs.getString("subjectCode"));
//            s.setSubjectName(rs.getString("Subject"));
//            s.setGwa(rs.getDouble("grade"));
//
//            return s;
//        });
//    }

//    public List<subjectConstructor> getSubjectsByStudentId(String StudentID) {
//        String sql = "SELECT ss.subjectcode, s.subjectName, sg.grade " +
//                "FROM studentsubject ss " +
//                "JOIN subject s ON ss.subjectcode = s.subjectcode " +
//                "LEFT JOIN studentsgrades sg ON ss.StudentNo = sg.StudentNo AND ss.SubjectCode = sg.subjectCode " +
//                "WHERE ss.StudentNo = ?";
//
//        List<subjectConstructor> subjects = template.query(sql, new Object[]{StudentID}, (rs, rowNum) -> {
//            subjectConstructor subject = new subjectConstructor();
//            subject.setSubjectCode(rs.getString("subjectcode"));
//            subject.setSubjectName(rs.getString("subjectName"));
//            Double grade = rs.getObject("grade", Double.class);
//            subject.setGwa(grade != null ? grade : null);
//            return subject;
//        });
//
//        // Log the results to check if grades are coming in correctly
//        subjects.forEach(subject -> System.out.println("Subject: " + subject.getSubjectName() + " Grade: " + subject.getGwa()));
//        return subjects;
//    }

    public String getStudentNoByStudentID(int studentID) {
        String sql = "SELECT StudentNo FROM enrolledstudents WHERE StudentNo = ?";
        return template.queryForObject(sql, new Object[]{studentID}, String.class);
    }

    public List<subjectConstructor> getSubjectsByStudentNo(String studentNo) {
        String sql = "SELECT ss.subjectcode, s.subjectName, sg.grade " +
                "FROM studentsubject ss " +
                "JOIN subject s ON ss.subjectcode = s.subjectcode " +  // Join with subject table for name
                "LEFT JOIN studentsgrades sg ON ss.StudentNo = sg.StudentNo AND ss.SubjectCode = sg.subjectCode " +  // Get grades
                "WHERE ss.StudentNo = ?";  // Filter by studentNo

        return template.query(sql, new Object[]{studentNo}, (rs, rowNum) -> {
            subjectConstructor subject = new subjectConstructor();
            subject.setSubjectCode(rs.getString("subjectcode"));
            subject.setSubjectName(rs.getString("subjectName"));  // Get subject name
            subject.setGwa(rs.getObject("grade", Double.class));  // Handle null gracefully
            return subject;
        });
    }






//    public void saveSchedule(int StudentID, String LName,
//                             String FName, String YrLvl, String Course, LocalDate Schedule,
//                             double downPayment){
//        String sql = "Insert into manageschedule (StudentID, LName, FName, YrLvl, Course, Schedule, downPayment) " +
//                "Values (?,?,?,?,?,?,?)";
//
//        try(Connection conn =  dataSource.getConnection();
//            PreparedStatement preparedStatement = conn.prepareStatement(sql)){
//
//            preparedStatement.setInt(1, StudentID);
//            preparedStatement.setString(2, FName);
//            preparedStatement.setString(3, LName);
//            preparedStatement.setString(4, YrLvl);
//            preparedStatement.setString(5, Course);
//            preparedStatement.setDate(6, java.sql.Date.valueOf(Schedule));
//            preparedStatement.setDouble(7, downPayment);
//
//            preparedStatement.executeUpdate();
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//    }

    public void saveSchedule(int StudentID, String LName,
                             String FName, String YrLvl, String Course, String Status, LocalDate Schedule,
                             double downPayment) {
        String sql = "Insert into manageschedule (StudentID, LName, FName, YrLvl, Course, Status, Schedule, downPayment) " +
                "Values (?,?,?,?,?,?,?,?)";

        template.update(sql, StudentID, LName, FName, YrLvl, Course, Status, java.sql.Date.valueOf(Schedule), downPayment);
    }

//    public Map<String, Object> findApplicants(int StudentID) {
//        String sql = "Select * from manageschedule where StudentID=?";
//
//        try {
//            return template.queryForMap(sql, StudentID);
//        } catch (EmptyResultDataAccessException e) {
//            return null;
//        }
//    }

    public StudentCOnstructor findApplicants(int StudentID){
        String sql = "Select * from manageschedule where StudentID=?";

        try{
            return template.queryForObject(sql, new Object[]{StudentID}, (rs, rowNum) -> {
                StudentCOnstructor applicant = new StudentCOnstructor();
                applicant.setStudentID(rs.getInt("StudentID"));
                applicant.setLName(rs.getString("LName"));
                applicant.setFName(rs.getString("FName"));
                applicant.setCourse(rs.getString("Course"));
                applicant.setYrLvl(rs.getString("YrLvl"));
                applicant.setStatus(rs.getString("Status"));
                applicant.setDownPayment(rs.getDouble("downPayment"));
                return applicant;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public boolean existsInEnrolledStudents(int StudentID){
        String sql = "Select COunt(*) from enrolledstudents where studentno=?";
        Integer count = template.queryForObject(sql, Integer.class, StudentID);
        return count != null && count > 0;
    }

    public void updateEnrolledStudents(int StudentID, String LName, String FName, String YrLvl,
                                       String Course, String Status){
        String sql = "Update enrolledstudents set LName=?, FName=?, YrLvl=?, Course=?, Status=? where studentno=?";
        template.update(sql, LName, FName, YrLvl, Course, Status, StudentID);
    }

    public void insertEnrolledStudents(int StudentID, String LName, String FName, String YrLvl,
                                       String Course, String Status){
        String sql = "Insert Into enrolledstudents (StudentNo, LName, FName, YrLvl, Course, Status) Values (?,?,?,?,?,?)";
        template.update(sql, StudentID, LName, FName, YrLvl, Course, Status);
    }

    public void deleteManageSchedule(int StudentID){
        String sql = "Delete from manageschedule where studentid=?";
        template.update(sql, StudentID);
    }

    public void assignSubjects(int StudentID, String YrLvl){
        String sql = "Select subjectCode from subject where YearLvl = ? and Semester = '1st Sem'";
        List<String> subjectCodes = template.queryForList(sql, String.class, YrLvl);

        for(String code : subjectCodes){
            String insertSql = "Insert Into studentsubject (StudentNo, subjectCode) Values (?,?)";
            template.update(insertSql, StudentID, code);

            String gradeSql = "Insert Into studentsgrades (StudentNo, subjectCode, SchoolYear, grade)" +
                    "Values (?,?,Year(CURDATE()), NUll)";
            template.update(gradeSql, StudentID, code);
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(StudentRepository.class);

    private static final String GET_STUDENTS_BY_SECTION_AND_SUBJECT =
            "SELECT es.StudentNo, es.FName, es.LName, es.Section, ss.SubjectCode, sg.Grade " +
                    "FROM enrolledstudents es " +
                    "JOIN studentsubject ss ON es.StudentNo = ss.StudentNo " +
                    "LEFT JOIN studentsgrades sg ON es.StudentNo = sg.StudentNo AND ss.SubjectCode = sg.SubjectCode";

    public List<AdminConstructor> getAllStudentsWithSubjectsAndGrades() {
        List<AdminConstructor> students = template.query(GET_STUDENTS_BY_SECTION_AND_SUBJECT,
                (rs, rowNum) -> new AdminConstructor(
                        rs.getInt("StudentNo"),
                        rs.getString("FName"),
                        rs.getString("LName"),
                        rs.getString("Section"),
                        rs.getString("SubjectCode"),
                        rs.getDouble("Grade")
                ));

        // Log the result
        if (students.isEmpty()) {
            logger.info("No students found!");
        } else {
            logger.info("Fetched " + students.size() + " students.");
        }



        return students;
    }

    //private static final Logger logger = LoggerFactory.getLogger(StudentRepository.class);

    public int updateStudentGrade(int studentNo, String subjectCode, double grade) {
        String query = "UPDATE studentsgrades SET grade = ? WHERE StudentNo = ? AND subjectcode = ?";

        logger.info("Executing query: " + query + " with parameters: grade=" + grade + ", studentNo=" + studentNo + ", subjectCode=" + subjectCode);


        try {
            int rowsUpdated = template.update(query, grade, studentNo, subjectCode);
            return rowsUpdated;
        } catch (Exception e) {
            logger.error("Error executing update", e);
            return 0; 
        }
    }
}
//}

