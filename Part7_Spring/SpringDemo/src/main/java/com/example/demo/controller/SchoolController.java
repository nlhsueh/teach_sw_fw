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

    /**
     * [Design Pattern / Principle 應用說明]
     * 1. 相依反轉原則 (DIP): 這裡透過 @Autowired 實現相依注入 (DI)。
     *    - 角色: 高階模組 (此 Controller) 相依於低階模組 (SchoolService)。
     *    - 教學提示: 目前是直接相依於具體類別，若能改為相依於介面（如 SchoolOperations），則能更完美地符合 DIP。
     * 2. 工廠方法模式 (Factory Method): 當 Spring 看到 @Autowired 時，底層會透過 BeanFactory 
     *    這個「工廠」來負責尋找、實例化並返回對應的物件。
     *    - 角色: 客戶端 (此 Controller) 透過工廠 (Spring 容器) 取得產品 (SchoolService)。
     */
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
