package com.enrollment.form.Enrollment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class subjectService {

    private final StudentRepository studentRepository;

    @Autowired
    public subjectService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public String getStudentNoByStudentID(int studentID) {
        return studentRepository.getStudentNoByStudentID(studentID);
    }

    public List<subjectConstructor> getSubjectsByStudentNo(String studentNo) {
        return studentRepository.getSubjectsByStudentNo(studentNo);
    }

    // Evaluate grades and determine the overall status of the student
    public String evaluateStudentGradeStatus(List<subjectConstructor> subjects) {
        double totalGrade = 0.0;
        int subjectCount = 0;
        List<String> failedSubjects = new ArrayList<>();

        // Iterate over subjects and check each subject grade
        for (subjectConstructor subject : subjects) {
            Double grade = subject.getGwa();
            if (grade != null) {
                totalGrade += grade;
                subjectCount++;
                if (grade < 75.0) {
                    failedSubjects.add(subject.getSubjectName());  // Track failed subjects
                }
            }
        }

        // Calculate GWA
        double gwa = subjectCount > 0 ? totalGrade / subjectCount : 0.0;

        // Determine the overall status
        String status = failedSubjects.isEmpty() ? "Regular" : "Irregular";

        // You could add the status and gwa to a student object here (not per subject)
        // Assuming you're updating the student's overall status (not individual subjects)
        // student.setStatus(status);
        // student.setGwa(gwa);

        // Optionally, you can return the list of subjects with GWA information as well
        return status;  // Return the overall student status
    }
}
