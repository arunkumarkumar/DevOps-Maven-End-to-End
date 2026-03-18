package com.capstone.app.controller;

import com.capstone.app.model.Student;
import com.capstone.app.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UIController {

    @Autowired
    private StudentService studentService;

    @Value("${app.version:1.0.0}")
    private String appVersion;

    @Value("${app.environment:development}")
    private String environment;

    // Home page - shows all students
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("totalCount", studentService.getTotalCount());
        model.addAttribute("appVersion", appVersion);
        model.addAttribute("environment", environment);
        model.addAttribute("newStudent", new Student());
        return "index";
    }

    // Add student form submission from UI
    @PostMapping("/ui/students")
    public String addStudent(@ModelAttribute Student student) {
        studentService.addStudent(student);
        return "redirect:/";
    }

    // Delete student from UI
    @PostMapping("/ui/students/{id}/delete")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/";
    }
}
