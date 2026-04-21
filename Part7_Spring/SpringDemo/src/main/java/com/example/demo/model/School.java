package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class School {
    private String schoolName;
    private List<Department> departments = new ArrayList<>();

    public School(String schoolName) {
        this.schoolName = schoolName;
    }

    public Department openDepartment(String name) {
        Department dept = new Department(name);
        departments.add(dept);
        return dept;
    }

    public List<Department> getDepartments() {
        return new ArrayList<>(departments);
    }

    public String getSchoolName() {
        return schoolName;
    }
}
