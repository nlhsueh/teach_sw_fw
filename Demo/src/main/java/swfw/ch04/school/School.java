package swfw.ch04.school;

import java.util.ArrayList;
import java.util.List;

/**
 * School 類別：學校，組合多個系所，管理所有師生。
 * UML:
 *   School "1" *-- "*" Department  (Composition：學校消失，系所也消失)
 *   University "1" o-- "0..*" Member (Aggregation in School.md v05)
 */
public class School {
    private String schoolName;

    // Composition：Department 由 School 建立並管理，生命週期綁定
    private List<Department> departments = new ArrayList<>();

    // 管理所有成員（多型：SchoolMember 可以是 Student 或 Teacher）
    private List<SchoolMember> members = new ArrayList<>();

    public School(String schoolName) {
        this.schoolName = schoolName;
    }

    public void openDepartment(Department dept) {
        departments.add(dept);
        System.out.println(schoolName + " 新增系所：" + dept.getDeptName());
    }

    public void enroll(Student student) {
        members.add(student);
    }

    public void hire(Teacher teacher) {
        members.add(teacher);
    }

    public List<Course> listCourses() {
        List<Course> all = new ArrayList<>();
        for (Department d : departments) {
            all.addAll(d.getCourses());
        }
        return all;
    }

    /**
     * 利用多型（Polymorphism）呼叫 displayInfo()，
     * 動態綁定會自動導向 Student 或 Teacher 的實作。
     */
    public void listAllMembers() {
        System.out.println("=== " + schoolName + " 成員列表 ===");
        for (SchoolMember m : members) {
            m.displayInfo();   // 動態綁定：Student 或 Teacher 的版本
        }
    }

    public String getSchoolName() { return schoolName; }
}
