package com.example.demo.dto;

public class GradeDTO {
    private String courseName;
    private double score;
    private String letterGrade;

    public GradeDTO(String courseName, double score, String letterGrade) {
        this.courseName = courseName;
        this.score = score;
        this.letterGrade = letterGrade;
    }

    public String getCourseName() { return courseName; }
    public double getScore() { return score; }
    public String getLetterGrade() { return letterGrade; }
}
