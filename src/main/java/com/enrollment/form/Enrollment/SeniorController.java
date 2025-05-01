package com.enrollment.form.Enrollment;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/students")
@SessionAttributes("enrollmentForm")
public class SeniorController {

    private final StudentRepository studentRepository;
    private final StudentService studentService;
    private final subjectService subjService;

    public SeniorController(StudentRepository studentRepository, StudentService studentService, subjectService subjService){
        this.studentRepository=studentRepository;
        this.studentService=studentService;
        this.subjService = subjService;
    }


    @GetMapping("/seniors")
    public String showSeniors(Model model, @ModelAttribute("enrollmentForm") StudentCOnstructor studentCOnstructor){
        System.out.println("In seniors -> YrLvl = " + studentCOnstructor.getYrLvl());
        model.addAttribute("enrollmentForm", studentCOnstructor);
        return "seniors";
    }

    @PostMapping("/seniorsGrd")
    public String validation(@RequestParam("StudentID") int StudentID,
                             @RequestParam("LName") String LName,
                             @RequestParam("FName") String FName,
                             @RequestParam("Course") String Course,
                             Model model, @ModelAttribute("enrollmentForm") StudentCOnstructor studentCOnstructor){
        //model.addAttribute("enrollmentForm", new StudentCOnstructor());
        boolean valid = studentRepository.validateStudent(StudentID, LName, FName, Course);

        if(valid){
            model.addAttribute("StudentID",StudentID);
            return "redirect:/students/seniorsGrade?StudentID=" + StudentID;
        }else{
            model.addAttribute("error","Invalid");
            return "seniors";
        }
    }

    @GetMapping("/seniorsGrade")
    public String evaluateGrades(@RequestParam("StudentID") int studentID, Model model,
                                 @ModelAttribute("enrollmentForm") StudentCOnstructor studentConstructor) {

        // Fetch StudentNo using the StudentID
        String studentNo = subjService.getStudentNoByStudentID(studentID);

        // Fetch subjects and their grades
        List<subjectConstructor> subjects = subjService.getSubjectsByStudentNo(studentNo);

        // Check if subjects were fetched correctly
        System.out.println("Fetched subjects: " + subjects);

        // Calculate GWA and evaluate failed subjects
        double totalGrade = 0.0;
        int subjectCount = 0;
        List<String> failedSubjects = new ArrayList<>();

        // Loop through subjects to calculate GWA and track failed subjects
        for (subjectConstructor subject : subjects) {
            Double grade = subject.getGwa();
            if (grade != null) {
                totalGrade += grade;
                subjectCount++;
                if (grade < 75) {  // Assuming 75 is the passing grade
                    failedSubjects.add(subject.getSubjectCode());  // Track failed subjects
                }
            }
        }

        // Calculate GWA
        double gwa = subjectCount > 0 ? totalGrade / subjectCount : 0.0;

        // Set the student status (Regular or Irregular)
        String status = failedSubjects.isEmpty() ? "Regular" : "Irregular";
        studentConstructor.setStatus(status);

        // Add attributes to model
        model.addAttribute("subjects", subjects);
        model.addAttribute("status", status);
        model.addAttribute("failedSubjects", failedSubjects);
        model.addAttribute("gwa", gwa);
        model.addAttribute("StudentID", studentID);

        return "seniorsGrade";  // Return the view name for rendering
    }


    @PostMapping("/enrollFor")
    public String enrollFor(@ModelAttribute("enrollmentForm") StudentCOnstructor studentCOnstructor, RedirectAttributes redirectAttributes,
                            Model model) {
        redirectAttributes.addFlashAttribute("enrollmentForm", studentCOnstructor);
        model.addAttribute("enrollmentForm", studentCOnstructor);
        return "redirect:/students/enrollTo?StudentID=" + studentCOnstructor.getStudentID();
    }

    @GetMapping("/enrollTo")
    public String showEnrollFor(@RequestParam("StudentID") String StudentID,
                                @ModelAttribute("enrollmentForm") StudentCOnstructor enrollmentForm,
                                Model model, RedirectAttributes redirectAttributes) {


        StudentCOnstructor student = studentRepository.fetchStudentID(StudentID);
        System.out.println("In confirmDetails -> YrLvl = " + enrollmentForm.getYrLvl());
        System.out.println("In confirmDetails -> Status = " + enrollmentForm.getStatus());

        if (student != null) {

            enrollmentForm.setStudentID(student.getStudentID());
            enrollmentForm.setLName(student.getLName());
            enrollmentForm.setFName(student.getFName());
            enrollmentForm.setCourse(student.getCourse());

            redirectAttributes.addFlashAttribute("enrollmentForm", enrollmentForm);
            return "redirect:/students/confirmDetails";

        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid Student ID.");
            return "redirect:/students/error";
        }
    }

    @GetMapping("/confirmDetails")
    public String showConfirmDetails(Model model) {
        model.addAttribute("enrollmentForm", model.asMap().get("enrollmentForm"));
        return "confirmDetails"; // Ensure this corresponds to the actual HTML template
    }


    @PostMapping("/sched")
    public String seniorSched(@ModelAttribute("enrollmentForm") StudentCOnstructor studentCOnstructor, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("enrollmentForm", studentCOnstructor);
        return "redirect:/students/seniorSched"; // Redirect to the second page
    }

    @GetMapping("/seniorSched")
    public String showseniorSched(Model model, @ModelAttribute("enrollmentForm") StudentCOnstructor studentCOnstructor,
                                  RedirectAttributes redirectAttributes) {

        if(!model.containsAttribute("enrollmentForm"))
        {
            redirectAttributes.addFlashAttribute("error", "Please complete previous steps first.");
            return "redirect:/students/";
        }

        System.out.println("In seniorSched - Enrollment Form: " + studentCOnstructor);


        //model.addAttribute("enrollmentForm", model.asMap().get("enrollmentForm"));
        return "seniorSched.html";

    }


    @PostMapping("/succ")
    public String Success(@ModelAttribute("enrollmentForm") StudentCOnstructor studentCOnstructor, RedirectAttributes redirectAttributes) {

        studentRepository.saveSchedule(
                studentCOnstructor.getStudentID(),
                studentCOnstructor.getLName(),
                studentCOnstructor.getFName(),
                studentCOnstructor.getYrLvl(),
                studentCOnstructor.getCourse(),
                studentCOnstructor.getStatus(),
                studentCOnstructor.getSchedule(),
                studentCOnstructor.getDownPayment()
        );


        redirectAttributes.addFlashAttribute("StudentID", studentCOnstructor.getStudentID());
        redirectAttributes.addFlashAttribute("downPayment", studentCOnstructor.getDownPayment());
        redirectAttributes.addFlashAttribute("enrollmentForm", studentCOnstructor);
        return "redirect:/students/2success"; // Redirect to the second page
    }

    @GetMapping("/2success")
    public String showseniorSuccess(Model model) {
        //model.addAttribute("studentCOnstructor", new StudentCOnstructor());
        model.addAttribute("enrollmentForm", model.asMap().get("enrollmentForm"));
        return "2success.html";
    }
}
