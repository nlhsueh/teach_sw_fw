package com.example.demo.controller;

import com.example.demo.dto.StudentDTO;
import com.example.demo.model.Course;
import com.example.demo.model.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class StudentController {

    @GetMapping("/student")
    public StudentDTO getStudentInfo() {
        // 模擬從資料庫取得資料
        Student student = new Student("John Doe");
        Course javaCourse = new Course("Java Programming", 3);
        Course springCourse = new Course("Spring Framework", 4);

        student.addGrade(javaCourse, 90);
        student.addGrade(springCourse, 85);

        // 轉換為 DTO (防止 Privacy Leak)
        Map<String, Integer> gradeMap = new HashMap<>();
        for (Map.Entry<Course, Integer> entry : student.getGrades().entrySet()) {
            gradeMap.put(entry.getKey().getName(), entry.getValue());
        }

        return new StudentDTO(
                student.getName(),
                gradeMap,
                student.getAverageGrade());
    }
}
