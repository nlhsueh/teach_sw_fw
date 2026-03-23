package ch04.school;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Student 類別：繼承 SchoolMember，可以選課與查看成績。
 * UML:
 *   SchoolMember <|-- Student
 *   Student "1" *-- "*" Grade  (Composition)
 *   Course "*" -- "*" Student  (Many-to-Many)
 */
public class Student extends SchoolMember {
    private int gradeLevel;

    // Composition：Grade 的生命週期與 Student 綁定
    // HashMap<Course, Grade>：每門課只有一筆成績，查詢 O(1)
    private Map<Course, Grade> grades = new HashMap<>();

    // 選修的課程清單（Many-to-Many 雙向關聯之一端）
    private List<Course> courses = new ArrayList<>();

    public Student(String memberId, String name, int gradeLevel) {
        super(memberId, name);
        this.gradeLevel = gradeLevel;
    }

    /**
     * 選課（雙向同步）：同時把自己加進課程的學生清單。
     */
    public void enrollCourse(Course c) {
        if (!courses.contains(c)) {
            courses.add(c);
        }
    }

    /**
     * 加入成績（由 Teacher.assignGrade 呼叫）。
     * Teacher 是觸發者 (use)，Student 才是擁有者 (Composition)。
     */
    public void addGrade(Grade grade) {
        grades.put(grade.getCourse(), grade);
    }

    public Grade getGrade(Course c) {
        return grades.get(c);
    }

    public List<Course> getCourses() { return courses; }

    public void showCourseInfo() {
        System.out.println(getName() + " 的選課清單：");
        for (Course c : courses) {
            Grade g = grades.get(c);
            String gradeStr = (g != null) ? g.toString() : "尚未評分";
            System.out.println("  - " + c + " | " + gradeStr);
        }
    }

    @Override
    public void displayInfo() {
        System.out.println("[Student] " + getName() + " (ID: " + getMemberId()
                + ", 年級: " + gradeLevel + ", 加入: " + getJoinDate() + ")");
    }
}
