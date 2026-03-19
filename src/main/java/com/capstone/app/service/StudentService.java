package com.capstone.app.service;

import com.capstone.app.model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class StudentService {

    // In-memory list — no database needed for capstone steps by arun
    private final List<Student> students = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public StudentService() {
        // Seed with some sample data on startup as new
        students.add(new Student(idCounter.getAndIncrement(), "Arun Kumar",   "arun@example.com",   "DevOps Engineering", 26));
        students.add(new Student(idCounter.getAndIncrement(), "Priya Sharma", "priya@example.com",  "Cloud Computing",    24));
        students.add(new Student(idCounter.getAndIncrement(), "Ravi Patel",   "ravi@example.com",   "Java Development",   27));
        students.add(new Student(idCounter.getAndIncrement(), "Meena Singh",  "meena@example.com",  "Kubernetes Admin",   25));
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public Optional<Student> getStudentById(Long id) {
        return students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    public Student addStudent(Student student) {
        student.setId(idCounter.getAndIncrement());
        students.add(student);
        return student;
    }

    public Optional<Student> updateStudent(Long id, Student updated) {
        return students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setEmail(updated.getEmail());
                    existing.setCourse(updated.getCourse());
                    existing.setAge(updated.getAge());
                    return existing;
                });
    }

    public boolean deleteStudent(Long id) {
        return students.removeIf(s -> s.getId().equals(id));
    }

    public int getTotalCount() {
        return students.size();
    }
}
