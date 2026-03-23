package swfw.ch04.school;

/**
 * Course 類別：代表一門課程。
 * UML: class Course { -courseId, -courseName, -credits }
 */
public class Course {
    private String courseId;
    private String courseName;
    private int credits;

    public Course(String courseId, String courseName, int credits) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
    }

    public String getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public int getCredits() { return credits; }

    @Override
    public String toString() {
        return courseName + "(" + courseId + ", " + credits + " 學分)";
    }
}
