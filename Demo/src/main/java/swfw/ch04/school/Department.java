package swfw.ch04.school;

import java.util.ArrayList;
import java.util.List;

/**
 * Department 類別：系所，聚合教師與課程。
 * UML:
 *   School "1" *-- "*" Department  (Composition)
 *   Department "1" -- "*" Teacher  (Association: hire)
 *   Department "1" -- "*" Course   (Association: offer)
 */
public class Department {
    private String deptName;

    // 聚合（Aggregation）：Teacher 在外部建立後傳入，生命週期獨立
    private List<Teacher> faculty = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();

    public Department(String deptName) {
        this.deptName = deptName;
    }

    public void assignTeacher(Teacher teacher) {
        faculty.add(teacher);
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public String getDeptName() { return deptName; }
    public List<Teacher> getFaculty() { return faculty; }
    public List<Course> getCourses() { return courses; }

    public void displayInfo() {
        System.out.println("【系所】" + deptName);
        System.out.println("  教師：");
        faculty.forEach(t -> System.out.println("    - " + t.getName()));
        System.out.println("  課程：");
        courses.forEach(c -> System.out.println("    - " + c));
    }
}
