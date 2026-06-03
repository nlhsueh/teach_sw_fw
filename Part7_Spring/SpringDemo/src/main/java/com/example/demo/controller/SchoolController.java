package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/school")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @GetMapping("/students")
    public List<StudentDTO> getAllStudents() {
        return schoolService.getAllStudentDTOs();
    }

    @GetMapping("/teachers")
    public List<TeacherDTO> getAllTeachers() {
        return schoolService.getAllTeacherDTOs();
    }

    @GetMapping("/courses")
    public List<CourseDTO> getAllCourses() {
        return schoolService.getAllCourseDTOs();
    }

    @PostMapping("/enroll")
    public void enroll(@RequestBody Map<String, String> payload) {
        schoolService.enrollStudent(payload.get("studentId"), payload.get("courseId"));
    }

    @PostMapping("/grade")
    public void grade(@RequestBody Map<String, Object> payload) {
        String teacherId = (String) payload.get("teacherId");
        String studentId = (String) payload.get("studentId");
        String courseId = (String) payload.get("courseId");
        double score = Double.parseDouble(payload.get("score").toString());
        schoolService.assignGrade(teacherId, studentId, courseId, score);
    }

    @GetMapping("/performance/{studentId}")
    public StudentPerformanceDTO getPerformance(@PathVariable String studentId) {
        return schoolService.getStudentPerformance(studentId).orElse(null);
    }
}
