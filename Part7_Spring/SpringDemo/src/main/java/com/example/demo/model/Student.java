package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Student extends SchoolMember {
    private int gradeLevel;
    private List<Grade> grades = new ArrayList<>();

    public Student(String memberId, String name, int gradeLevel) {
        super(memberId, name);
        this.gradeLevel = gradeLevel;
    }

    public void enrollCourse(Course course) {
        // Enrollment is represented by a Grade object with no score (or 0) initially
        // In this implementation, we add a Grade object to represent the association
        if (grades.stream().noneMatch(g -> g.getCourse().getCourseId().equals(course.getCourseId()))) {
            grades.add(new Grade(course, 0));
        }
    }

    public List<Grade> getGrades() {
        return new ArrayList<>(grades);
    }

    public int getGradeLevel() {
        return gradeLevel;
    }

    @Override
    public String displayInfo() {
        return "Student [" + memberId + "] " + name + " (Level: " + gradeLevel + ")";
    }
}
