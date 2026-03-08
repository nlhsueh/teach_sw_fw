package com.example.demo.model;

import java.util.HashMap;
import java.util.Map;

public class Student {
    private String name;
    private Map<Course, Integer> grades = new HashMap<>();

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addGrade(Course course, int score) {
        grades.put(course, score);
    }

    public Map<Course, Integer> getGrades() {
        // Defensive copy to prevent privacy leak
        return new HashMap<>(grades);
    }

    public double getAverageGrade() {
        if (grades.isEmpty())
            return 0.0;
        int total = 0;
        for (int score : grades.values()) {
            total += score;
        }
        return (double) total / grades.size();
    }
}
