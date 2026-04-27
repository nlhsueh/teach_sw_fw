package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.dto.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class SchoolService {
    private final School school = new School("Spring University");
    private final Map<String, Student> students = new ConcurrentHashMap<>();
    private final Map<String, Teacher> teachers = new ConcurrentHashMap<>();
    private final Map<String, Course> courses = new ConcurrentHashMap<>();

    public SchoolService() {
        initData();
    }

    private void initData() {
        // Create Departments
        Department cs = school.openDepartment("Computer Science");
        Department ee = school.openDepartment("Electrical Engineering");
        Department biz = school.openDepartment("Business School");

        // Create Teachers
        Teacher bob = new Teacher("T001", "Dr. Bob", "CS");
        Teacher alice = new Teacher("T002", "Prof. Alice", "EE");
        Teacher charlie = new Teacher("T003", "Dr. Charlie", "Business");
        
        for (Teacher t : List.of(bob, alice, charlie)) {
            teachers.put(t.getMemberId(), t);
        }
        cs.assignTeacher(bob);
        ee.assignTeacher(alice);
        biz.assignTeacher(charlie);

        // Create Courses
        Course oop = new Course("CS101", "Intro to OOP", 3);
        Course db = new Course("CS102", "Databases", 3);
        Course circuit = new Course("EE201", "Circuit Analysis", 4);
        Course econ = new Course("BIZ301", "Microeconomics", 3);
        
        for (Course c : List.of(oop, db, circuit, econ)) {
            courses.put(c.getCourseId(), c);
        }
        
        cs.addCourse(oop);
        cs.addCourse(db);
        ee.addCourse(circuit);
        biz.addCourse(econ);

        // Assign Teachers to Courses
        bob.addCourse(oop);
        bob.addCourse(db);
        alice.addCourse(circuit);
        charlie.addCourse(econ);

        // Create Students
        Student john = new Student("S001", "John Doe", 1);
        Student jane = new Student("S002", "Jane Smith", 2);
        Student mike = new Student("S003", "Mike Ross", 1);
        Student sarah = new Student("S004", "Sarah Connor", 3);
        
        for (Student s : List.of(john, jane, mike, sarah)) {
            students.put(s.getMemberId(), s);
        }

        // Auto enroll & Grade some for demo
        john.enrollCourse(oop);
        john.enrollCourse(db);
        bob.assignGrade(john, oop, 88);
        
        jane.enrollCourse(oop);
        jane.enrollCourse(circuit);
        bob.assignGrade(jane, oop, 95);
        
        mike.enrollCourse(econ);
        charlie.assignGrade(mike, econ, 78);
        
        sarah.enrollCourse(db);
        sarah.enrollCourse(circuit);
        sarah.enrollCourse(econ);
    }

    public School getSchool() {
        return school;
    }

    public Collection<Student> getAllStudents() {
        return students.values();
    }

    public Collection<Teacher> getAllTeachers() {
        return teachers.values();
    }

    public Collection<Course> getAllCourses() {
        return courses.values();
    }

    public Optional<Student> getStudent(String id) {
        return Optional.ofNullable(students.get(id));
    }

    public Optional<Teacher> getTeacher(String id) {
        return Optional.ofNullable(teachers.get(id));
    }

    public void enrollStudent(String studentId, String courseId) {
        Student student = students.get(studentId);
        Course course = courses.get(courseId);
        if (student != null && course != null) {
            student.enrollCourse(course);
        }
    }

    public void assignGrade(String teacherId, String studentId, String courseId, double score) {
        Teacher teacher = teachers.get(teacherId);
        Student student = students.get(studentId);
        Course course = courses.get(courseId);
        if (teacher != null && student != null && course != null) {
            teacher.assignGrade(student, course, score);
        }
    }

    public List<StudentDTO> getAllStudentDTOs() {
        return students.values().stream().map(this::toStudentDTO).collect(Collectors.toList());
    }

    public List<TeacherDTO> getAllTeacherDTOs() {
        return teachers.values().stream().map(this::toTeacherDTO).collect(Collectors.toList());
    }

    public List<CourseDTO> getAllCourseDTOs() {
        return courses.values().stream().map(this::toCourseDTO).collect(Collectors.toList());
    }

    public StudentDTO toStudentDTO(Student student) {
        List<GradeDTO> gradeDTOs = student.getGrades().stream()
                .map(g -> new GradeDTO(g.getCourse().getName(), g.getScore(), g.getLetterGrade()))
                .collect(Collectors.toList());
        return new StudentDTO(student.getMemberId(), student.getName(), student.getGradeLevel(), gradeDTOs);
    }

    public TeacherDTO toTeacherDTO(Teacher teacher) {
        List<String> courses = teacher.getTaughtCourses().stream().map(Course::getName).collect(Collectors.toList());
        return new TeacherDTO(teacher.getMemberId(), teacher.getName(), teacher.getDepartment(), courses);
    }

    public CourseDTO toCourseDTO(Course course) {
        // Finding teacher who teaches this course
        String teacherName = teachers.values().stream()
                .filter(t -> t.getTaughtCourses().stream().anyMatch(c -> c.getCourseId().equals(course.getCourseId())))
                .map(Teacher::getName)
                .findFirst().orElse("Unassigned");
        return new CourseDTO(course.getCourseId(), course.getName(), course.getCredits(), teacherName);
    }

    public Optional<StudentPerformanceDTO> getStudentPerformance(String studentId) {
        return getStudent(studentId).map(this::toPerformanceDTO);
    }

    private StudentPerformanceDTO toPerformanceDTO(Student student) {
        StudentPerformanceDTO dto = new StudentPerformanceDTO();
        dto.setStudentId(student.getMemberId());

        String rawName = student.getName();
        if (rawName.length() > 2) {
            dto.setMaskedName(rawName.charAt(0) + "***" + rawName.charAt(rawName.length() - 1));
        } else {
            dto.setMaskedName(rawName.charAt(0) + "*");
        }

        double totalWeightedScore = 0;
        int totalCredits = 0;
        int earnedCredits = 0;
        List<String> failedOnes = new ArrayList<>();
        boolean hasCriticalFailure = false;

        for (Grade g : student.getGrades()) {
            int cr = g.getCourse().getCredits();
            double sc = g.getScore();
            
            totalWeightedScore += (sc * cr);
            totalCredits += cr;

            if (sc >= 60) {
                earnedCredits += cr;
            } else {
                failedOnes.add(g.getCourse().getName());
                if (cr >= 4) hasCriticalFailure = true;
            }
        }

        double gpa = totalCredits > 0 ? totalWeightedScore / totalCredits : 0;
        dto.setWeightedGpa(Math.round(gpa * 100.0) / 100.0);
        dto.setTotalEarnedCredits(earnedCredits);
        dto.setFailedCourseNames(failedOnes);

        if (gpa >= 85) dto.setAcademicStanding("Distinction");
        else if (gpa >= 60) dto.setAcademicStanding("Pass");
        else dto.setAcademicStanding("Probation");

        dto.setGraduationProgress(Math.min(1.0, (double)earnedCredits / 120.0));
        dto.setAtRisk(hasCriticalFailure || gpa < 60);

        return dto;
    }
}
