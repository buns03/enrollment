package com.enrollment.form.Enrollment;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/students")
@SessionAttributes("enrollmentForm")
public class StudentController {

    private final StudentRepository studentRepository;
    private final StudentService studentService;
    private final subjectService subjService;

    public StudentController(StudentRepository studentRepository, StudentService studentService, subjectService subjService){
        this.studentRepository=studentRepository;
        this.studentService=studentService;
        this.subjService = subjService;
    }

    //homepage
    @GetMapping("/")
    public String showLandingPage() {
        return "enroll";
    }

    //year selection
    @PostMapping("/first")
    public String showEnroll(@ModelAttribute("enrollmentForm") StudentCOnstructor studentCOnstructor, RedirectAttributes redirectAttributes)
    {
        redirectAttributes.addFlashAttribute("enrollmentForm", studentCOnstructor);
        return "redirect:/students/yearSelection";
    }

    @GetMapping("/yearSelection")
    public String showFirst(Model model)
    {
        model.addAttribute("enrollmentForm", new StudentCOnstructor());
        return "first";
    }


    @PostMapping("/yearController")
    public String showInfo(@ModelAttribute("enrollmentForm") StudentCOnstructor studentCOnstructor, RedirectAttributes redirectAttributes)
    {
        String yrLvl = studentCOnstructor.getYrLvl();
        System.out.println("Selected YrLvl: " + yrLvl);
        //redirectAttributes.addFlashAttribute("YrLvl", yrLvl);

        if ("1st Year".equals(yrLvl)) {
            return "redirect:/students/studentInformation";
        } else {
            return "redirect:/students/seniors";
        }

    }

    @GetMapping("/studentInformation")
    public String showstudentCOnstructor(Model model) {
        model.addAttribute("enrollmentForm", new StudentCOnstructor()); // Passing empty form object to the template
        return "firstpage";
    }
//
//    @GetMapping("/seniors")
//    public String showSeniors(Model model, @ModelAttribute("enrollmentForm") StudentCOnstructor studentCOnstructor){
//        System.out.println("In confirmDetails -> YrLvl = " + studentCOnstructor.getYrLvl());
//        model.addAttribute("enrollmentForm", studentCOnstructor);
//        return "seniors";
//    }

    //freshman parent info
    @PostMapping("/parent")
    public String nextPage(@ModelAttribute("enrollmentForm") StudentCOnstructor studentCOnstructor, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("enrollmentForm", studentCOnstructor);
        return "redirect:/students/parentInformation"; // Redirect to the second page
    }

    @GetMapping("/parentInformation")
    public String showParentInfoPage(Model model) {
        //model.addAttribute("studentCOnstructor", new StudentCOnstructor());
        model.addAttribute("enrollmentForm", model.asMap().get("enrollmentForm"));
        return "second.html";
    }

    @PostMapping("/education")
    public String nextEducation(@ModelAttribute("enrollmentForm") StudentCOnstructor studentCOnstructor, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("enrollmentForm", studentCOnstructor);
        return "redirect:/students/educationInformation";
    }

    @GetMapping("/educationInformation")
    public String showEducationInfoPage(Model model) {
        //model.addAttribute("studentCOnstructor", new StudentCOnstructor());
        model.addAttribute("enrollmentForm", model.asMap().get("enrollmentForm"));
        return "educational.html";
    }

    @PostMapping("/schedule")
    public String nextDocuments(@ModelAttribute("enrollmentForm") StudentCOnstructor studentCOnstructor, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("enrollmentForm", studentCOnstructor);
        return "redirect:/students/paymentSchedule";
    }

    @GetMapping("/paymentSchedule")
    public String showDocuInfoPage(Model model) {
        model.addAttribute("enrollmentForm", model.asMap().get("enrollmentForm"));
        return "documents.html";
    }


    @PostMapping("/addFreshmen")
    public String saveData(@ModelAttribute("enrollmentForm") StudentCOnstructor studentCOnstructor, Model model, RedirectAttributes redirectAttributes){

        if (studentCOnstructor.getDownPayment() < 500) {
            model.addAttribute("downPaymentError", "Minimum down payment is 500.");
            return "documents"; // Stay on the form page with the error message
        }

        int StudentID = studentRepository.add(studentCOnstructor.getLName(), studentCOnstructor.getFName(), studentCOnstructor.getMName(), studentCOnstructor.getSuffix(),
                studentCOnstructor.getAge(), studentCOnstructor.getSex(), studentCOnstructor.getDob(), studentCOnstructor.getCourse(),
                studentCOnstructor.getEmail(), studentCOnstructor.getMnumber(), studentCOnstructor.getNationality(), studentCOnstructor.getAddress());

        if (StudentID > 0) {
            // Step 2: Save parent info
            studentRepository.addParent(StudentID, studentCOnstructor.getMother(), studentCOnstructor.getMonum(),
                    studentCOnstructor.getMooccu(), studentCOnstructor.getMoaddress(), studentCOnstructor.getMoemail(),
                    studentCOnstructor.getFather(), studentCOnstructor.getFnum(), studentCOnstructor.getFoccu(),
                    studentCOnstructor.getFaddress(), studentCOnstructor.getFemail(), studentCOnstructor.getGuardian(),
                    studentCOnstructor.getGnum(), studentCOnstructor.getGaddress(), studentCOnstructor.getGemail());

            // Step 3: Save past school info
            studentRepository.addPastSchoolInfo(StudentID, studentCOnstructor.getLName(), studentCOnstructor.getFName(), studentCOnstructor.getSchoolName(), studentCOnstructor.getStrand(),
                    studentCOnstructor.getAverage(), studentCOnstructor.getAchievements(), studentCOnstructor.getDateGraduate());

            // Step 4: Save schedule info (if applicable)
            studentRepository.addSchedule(StudentID,
                    studentCOnstructor.getLName(),
                    studentCOnstructor.getFName(),
                    "1st Year",
                    studentCOnstructor.getCourse(),
                    "Regular",
                    studentCOnstructor.getDownPayment(),
                    studentCOnstructor.getSchedule());

            redirectAttributes.addFlashAttribute("StudentID", StudentID);
            redirectAttributes.addFlashAttribute("downPayment", studentCOnstructor.getDownPayment());

            return "redirect:/students/successPage"; // Redirect to success page
        } else {
            model.addAttribute("error", "Failed to save data.");
            return "redirect:/students/"; // Error page
        }
    }


    @GetMapping("/successPage")
    public String showEndInfoPage(Model model) {
        model.addAttribute("studentCOnstructor", new StudentCOnstructor());
        return "1success.html";
    }

}



