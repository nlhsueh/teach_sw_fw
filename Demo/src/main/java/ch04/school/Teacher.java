package ch04.school;

import java.util.ArrayList;
import java.util.List;

/**
 * Teacher 類別：繼承 SchoolMember，可以開課與打分數。
 * UML:
 *   SchoolMember <|-- Teacher
 *   Teacher "1" -- "*" Course   (Association: teach)
 *   Teacher ..> Student         (Dependency: use)
 *   Teacher ..> Grade           (Dependency: use/creates)
 */
public class Teacher extends SchoolMember {

    // 老師所開設的課程（Association）
    private List<Course> taughtCourses = new ArrayList<>();

    public Teacher(String memberId, String name) {
        super(memberId, name);
    }

    /**
     * 開設一門課（Association: Teacher ── Course）
     */
    public void offer(Course course) {
        taughtCourses.add(course);
        System.out.println(getName() + " 開設課程：" + course);
    }

    public void showCourse() {
        System.out.println(getName() + " 開設的課程：");
        taughtCourses.forEach(c -> System.out.println("  - " + c));
    }

    /**
     * 打分數：Teacher 依賴（use）Student、Course、Grade。
     * Teacher 是觸發者；Grade 的擁有權屬於 Student（Composition）。
     */
    public void assignGrade(Student student, Course course, double score) {
        Grade grade = new Grade(course, score);   // 建立 Grade，but 不保留參考
        student.addGrade(grade);                  // 委託 Student 擁有與管理
        System.out.println(getName() + " 給 " + student.getName()
                + " 的【" + course.getCourseName() + "】評分：" + score
                + " (" + grade.getLetterGrade() + ")");
    }

    public List<Course> getTaughtCourses() { return taughtCourses; }

    @Override
    public void displayInfo() {
        System.out.println("[Teacher] " + getName() + " (ID: " + getMemberId()
                + ", 加入: " + getJoinDate() + ")");
    }
}
