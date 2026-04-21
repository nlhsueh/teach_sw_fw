package com.example.demo.dto;

public class CourseDTO {
    private String courseId;
    private String name;
    private int credits;
    private String teacherName;

    public CourseDTO(String courseId, String name, int credits, String teacherName) {
        this.courseId = courseId;
        this.name = name;
        this.credits = credits;
        this.teacherName = teacherName;
    }

    public String getCourseId() { return courseId; }
    public String getName() { return name; }
    public int getCredits() { return credits; }
    public String getTeacherName() { return teacherName; }
}
