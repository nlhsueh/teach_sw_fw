package com.example.demo.dto;

import java.util.List;

public class StudentDTO {
    private String memberId;
    private String name;
    private int gradeLevel;
    private List<GradeDTO> grades;

    public StudentDTO(String memberId, String name, int gradeLevel, List<GradeDTO> grades) {
        this.memberId = memberId;
        this.name = name;
        this.gradeLevel = gradeLevel;
        this.grades = grades;
    }

    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public int getGradeLevel() { return gradeLevel; }
    public List<GradeDTO> getGrades() { return grades; }
}
