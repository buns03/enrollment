package com.enrollment.form.Enrollment;


import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class LoginController {

    private final StudentRepository studentRepository;
    private final StudentService studentService;
    private final subjectService subjService;
    private final AdminService adminService;

    public LoginController(StudentRepository studentRepository, StudentService studentService, subjectService subjService, AdminService adminService){
        this.studentRepository=studentRepository;
        this.studentService=studentService;
        this.subjService = subjService;
        this.adminService = adminService;
    }

    @GetMapping("/loginForm")
    public String showloginForm(){
        return "loginForm";
    }


    @GetMapping("/registerForm")
    public String showregisterForm(){
        return "registerForm";
    }

    @GetMapping("/adminDashboard")
    public String adminDashboard(Model model) {
        List<AdminConstructor> students = studentRepository.getAllStudentsWithSubjectsAndGrades();

        // Calculate statistics
        int totalStudents = students.size();
        double averageGrade = students.stream()
                .filter(s -> s.getGrade() != null) // Handle null grades
                .mapToDouble(AdminConstructor::getGrade)
                .average()
                .orElse(0.0); // Default to 0.0 if no grades exist

        Map<String, Long> studentsPerSubject = students.stream()
                .collect(Collectors.groupingBy(AdminConstructor::getSubject, Collectors.counting()));

        // Pass the data to the view (Thymeleaf)
        model.addAttribute("students", students); // List of students for the table
        model.addAttribute("totalStudents", totalStudents); // Total number of students
        model.addAttribute("averageGrade", averageGrade); // Average grade of students
        model.addAttribute("studentsPerSubject", studentsPerSubject); // Subject statistics

        return "adminDashboard"; // Return the name of the template (adminDashboard.html)
    }
}
