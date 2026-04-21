package com.example.demo.dto;

import java.util.List;

public class TeacherDTO {
    private String memberId;
    private String name;
    private String department;
    private List<String> taughtCourseNames;

    public TeacherDTO(String memberId, String name, String department, List<String> taughtCourseNames) {
        this.memberId = memberId;
        this.name = name;
        this.department = department;
        this.taughtCourseNames = taughtCourseNames;
    }

    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public List<String> getTaughtCourseNames() { return taughtCourseNames; }
}
