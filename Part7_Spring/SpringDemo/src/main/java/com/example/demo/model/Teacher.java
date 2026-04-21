package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends SchoolMember {
    private String department;
    private List<Course> taughtCourses = new ArrayList<>();

    public Teacher(String memberId, String name, String department) {
        super(memberId, name);
        this.department = department;
    }

    public void addCourse(Course course) {
        if (!taughtCourses.contains(course)) {
            taughtCourses.add(course);
        }
    }

    public void assignGrade(Student student, Course course, double score) {
        // Teacher assigns a grade to a student for a specific course
        student.getGrades().stream()
                .filter(g -> g.getCourse().getCourseId().equals(course.getCourseId()))
                .findFirst()
                .ifPresent(g -> g.setScore(score));
    }

    public List<Course> getTaughtCourses() {
        return new ArrayList<>(taughtCourses);
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String displayInfo() {
        return "Teacher [" + memberId + "] " + name + " (Dept: " + department + ")";
    }
}
