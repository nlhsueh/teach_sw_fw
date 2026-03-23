package swfw.ch04.school;

/**
 * Main：校園管理系統示範程式。
 *
 * 示範流程：
 *   1. 建立學校、系所、課程
 *   2. 老師開課（Association: Teacher -- Course）
 *   3. 學生選課（Many-to-Many: Student -- Course）
 *   4. 老師打分數（Dependency: Teacher ..> Student, Grade）
 *   5. 多型展示（SchoolMember.displayInfo() 動態綁定）
 */
public class Main {
    public static void main(String[] args) {

        // ── 1. 建立學校與系所 ──────────────────────────────────
        School school = new School("逢甲大學");
        Department cs = new Department("資訊工程學系");
        school.openDepartment(cs);

        // ── 2. 建立課程 ────────────────────────────────────────
        Course oop  = new Course("CS101", "物件導向設計", 3);
        Course algo = new Course("CS201", "演算法",       3);
        cs.addCourse(oop);
        cs.addCourse(algo);

        // ── 3. 建立老師、開課（Association） ───────────────────
        Teacher alice = new Teacher("T001", "Alice");
        Teacher bob   = new Teacher("T002", "Bob");
        cs.assignTeacher(alice);
        cs.assignTeacher(bob);
        school.hire(alice);
        school.hire(bob);

        System.out.println();
        alice.offer(oop);
        bob.offer(algo);

        // ── 4. 建立學生、選課（Many-to-Many） ──────────────────
        Student s1 = new Student("S001", "小明", 2);
        Student s2 = new Student("S002", "小華", 1);
        school.enroll(s1);
        school.enroll(s2);

        System.out.println();
        s1.enrollCourse(oop);
        s1.enrollCourse(algo);
        s2.enrollCourse(oop);

        // ── 5. 老師打分數（Teacher use Student, Grade） ─────────
        System.out.println();
        alice.assignGrade(s1, oop,  88);
        alice.assignGrade(s2, oop,  72);
        bob.assignGrade(s1,   algo, 95);

        // ── 6. 學生查看成績 ────────────────────────────────────
        System.out.println();
        s1.showCourseInfo();
        System.out.println();
        s2.showCourseInfo();

        // ── 7. 多型展示（SchoolMember.displayInfo() 動態綁定） ──
        System.out.println();
        school.listAllMembers();

        // ── 8. 學校課程列表 ─────────────────────────────────────
        System.out.println();
        System.out.println("=== " + school.getSchoolName() + " 開設課程 ===");
        school.listCourses().forEach(System.out::println);
    }
}
