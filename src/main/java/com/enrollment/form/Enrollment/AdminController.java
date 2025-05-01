package com.enrollment.form.Enrollment;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final StudentRepository studentRepository;
    private final StudentService studentService;
    private final subjectService subjService;
    private final AdminService adminService;

    public AdminController(StudentRepository studentRepository, StudentService studentService, subjectService subjService, AdminService adminService){
        this.studentRepository=studentRepository;
        this.studentService=studentService;
        this.subjService = subjService;
        this.adminService = adminService;
    }

    @PostMapping("/fetchApplicant")
    public String fetchApplicant(@RequestParam("StudentID") int StudentID,
                                 Model model, RedirectAttributes redirectAttributes){
        StudentCOnstructor applicant = studentRepository.findApplicants(StudentID);

        if(applicant==null){
            redirectAttributes.addFlashAttribute("noData", true);
        }else {
            redirectAttributes.addFlashAttribute("noData", false);
        }

        redirectAttributes.addFlashAttribute("studentData", applicant); // Pass the applicant object to the redirected page
        redirectAttributes.addFlashAttribute("activeSection", "applicants");
        return "redirect:/adminDashboard";
    }

    

    @PostMapping("/approveApplicant")
    public String approveApplicant(@RequestParam("StudentID") int StudentID,
                                   @RequestParam("LName") String LName,
                                   @RequestParam("FName") String FName,
                                   @RequestParam("YrLvl") String YrLvl,
                                   @RequestParam("Course") String Course,
                                   @RequestParam("Status") String Status,
                                   RedirectAttributes redirectAttributes
                                   ){
        boolean exists = studentRepository.existsInEnrolledStudents(StudentID);

        if(exists){
            studentRepository.updateEnrolledStudents(StudentID, LName, FName, YrLvl, Course,
                    Status);
            redirectAttributes.addFlashAttribute("approveMessage", "Added Student Succesfully");
        }
        else{
            studentRepository.insertEnrolledStudents(StudentID, LName, FName, YrLvl, Course,
                    Status);
            redirectAttributes.addFlashAttribute("approveMessage", "Added Student Succesfully");
        }

        studentRepository.deleteManageSchedule(StudentID);

        studentRepository.assignSubjects(StudentID, YrLvl);

        redirectAttributes.addFlashAttribute("approveMessage", "Added Student Successfully");
        redirectAttributes.addFlashAttribute("activeSection", "applicants");
        return "redirect:/adminDashboard";
    }

//    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

//    @GetMapping("/studentsrecords")
//    public String getAllStudentsWithSubjectsAndGrades(Model model) {
//        logger.info("Entered getAllStudentsWithSubjectsAndGrades method"); // Add a log here
//
//        // Get all students with their subjects and grades
//        List<AdminConstructor> students = studentRepository.getAllStudentsWithSubjectsAndGrades();
//
//        if (students.isEmpty()) {
//            logger.info("No students to display.");
//        } else {
//            logger.info("Passing " + students.size() + " students to the model.");
//        }
//
//        model.addAttribute("students", students);
//        return "adminDashboard"; // Ensure the view name is correct
//    }
private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @PostMapping("/updateGrades")
    public String updateGrades(@RequestParam Map<String, String> grades) {

        logger.info("Submitted grades: " + grades);

        for (String key : grades.keySet()) {

            String[] parts = key.replace("grades[", "").replace("]", "").split("\\[");
            int studentNo = Integer.parseInt(parts[0]);
            String subject = parts[1];
            double grade = Double.parseDouble(grades.get(key));


            studentRepository.updateStudentGrade(studentNo, subject, grade);
        }
        return "redirect:/adminDashboard";
    }
}
