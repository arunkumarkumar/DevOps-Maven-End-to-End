package com.capstone.app;

import com.capstone.app.model.Student;
import com.capstone.app.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MyAppApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentService studentService;

    // ── Service Layer Tests ────────────────────────────────────────────────

    @Test
    void contextLoads() {
        // Verifies Spring context starts correctly
        assertNotNull(studentService);
    }

    @Test
    void shouldReturnSeededStudents() {
        assertTrue(studentService.getTotalCount() > 0,
                "Service should have seeded students on startup");
    }

    @Test
    void shouldAddAndRetrieveStudent() {
        Student s = new Student(null, "Test User", "test@test.com", "Testing 101", 22);
        Student saved = studentService.addStudent(s);

        assertNotNull(saved.getId());
        assertTrue(studentService.getStudentById(saved.getId()).isPresent());
        assertEquals("Test User", saved.getName());
    }

    @Test
    void shouldDeleteStudent() {
        Student s = new Student(null, "Delete Me", "del@test.com", "Temp Course", 20);
        Student saved = studentService.addStudent(s);

        assertTrue(studentService.deleteStudent(saved.getId()));
        assertFalse(studentService.getStudentById(saved.getId()).isPresent());
    }

    // ── REST API Tests ─────────────────────────────────────────────────────

    @Test
    void healthEndpointReturnsUp() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    void studentsEndpointReturnsList() throws Exception {
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void homePageLoads() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void actuatorHealthEndpointExists() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk());
    }
}
