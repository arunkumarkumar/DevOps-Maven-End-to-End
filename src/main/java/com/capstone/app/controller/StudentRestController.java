package com.capstone.app.controller;

import com.capstone.app.model.Student;
import com.capstone.app.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class StudentRestController {

    @Autowired
    private StudentService studentService;

    @Value("${app.version:1.0.0}")
    private String appVersion;

    @Value("${app.environment:development}")
    private String environment;

    // ── Health & Info ──────────────────────────────────────────────────────

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("version", appVersion);
        response.put("environment", environment);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info() {
        Map<String, Object> response = new HashMap<>();
        response.put("appName", "Capstone DevOps App");
        response.put("version", appVersion);
        response.put("environment", environment);
        response.put("totalStudents", studentService.getTotalCount());
        response.put("techStack", "Spring Boot + Docker + Kubernetes + Harness + ArgoCD");
        return ResponseEntity.ok(response);
    }

    // ── Student CRUD REST Endpoints ────────────────────────────────────────

    // GET all students
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // GET student by ID
    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST - create new student
    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student created = studentService.addStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT - update existing student
    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id,
                                                  @RequestBody Student student) {
        return studentService.updateStudent(id, student)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE - remove student
    @DeleteMapping("/students/{id}")
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        if (studentService.deleteStudent(id)) {
            response.put("message", "Student deleted successfully");
            return ResponseEntity.ok(response);
        }
        response.put("message", "Student not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
