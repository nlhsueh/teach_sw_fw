package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private String deptName;
    private List<Teacher> faculty = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();

    public Department(String deptName) {
        this.deptName = deptName;
    }

    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    public void assignTeacher(Teacher teacher) {
        if (!faculty.contains(teacher)) {
            faculty.add(teacher);
        }
    }

    public String getDeptName() {
        return deptName;
    }

    public List<Teacher> getFaculty() {
        return new ArrayList<>(faculty);
    }

    public List<Course> getCourses() {
        return new ArrayList<>(courses);
    }
}
