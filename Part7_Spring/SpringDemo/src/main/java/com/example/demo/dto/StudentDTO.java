package com.example.demo.dto;

import java.util.Map;

public class StudentDTO {
    private String name;
    private Map<String, Integer> courseGrades;
    private double average;

    public StudentDTO(String name, Map<String, Integer> courseGrades, double average) {
        this.name = name;
        this.courseGrades = courseGrades;
        this.average = average;
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getCourseGrades() {
        return courseGrades;
    }

    public double getAverage() {
        return average;
    }
}
