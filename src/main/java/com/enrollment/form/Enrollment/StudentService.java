package com.enrollment.form.Enrollment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.Subject;
import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository=studentRepository;
    }

    public int add(StudentCOnstructor student) {
        // First insert student basic info
        int studentId = studentRepository.add(
                student.getLName(), student.getFName(), student.getMName(), student.getSuffix(),
                student.getAge(), student.getSex(), student.getDob(), student.getCourse(),
                student.getEmail(), student.getMnumber(), student.getNationality(), student.getAddress()
        );

        // Then insert parent info
        studentRepository.addParent(
                studentId, student.getMother(), student.getMonum(), student.getMooccu(),
                student.getMoaddress(), student.getMoemail(), student.getFather(), student.getFnum(),
                student.getFoccu(), student.getFaddress(), student.getFemail(), student.getGuardian(),
                student.getGnum(), student.getGaddress(), student.getGemail()
        );

        // Then insert past school info
        studentRepository.addPastSchoolInfo(
                studentId, student.getLName(), student.getFName(), student.getSchoolName(), student.getStrand(), student.getAverage(),
                student.getAchievements(), student.getDateGraduate()
        );

        // Finally insert schedule info
        studentRepository.addSchedule(studentId,
                student.getLName(),
                student.getFName(),
                "1st Year",
                student.getCourse(),
                "Regular",
                student.getDownPayment(),
                student.getSchedule());

        return studentId;
    }

    public boolean validateStudents(StudentCOnstructor studentCOnstructor){
        return studentRepository.findStudentID(studentCOnstructor.getStudentID(), studentCOnstructor.getLName(),
                studentCOnstructor.getFName(), studentCOnstructor.getCourse()).isPresent();
    }

    public StudentCOnstructor getStudentDetails(StudentCOnstructor studentCOnstructor){
        return studentRepository.findStudentID(studentCOnstructor.getStudentID(), studentCOnstructor.getLName(),
                studentCOnstructor.getFName(), studentCOnstructor.getCourse()).orElse(null);
    }


}
