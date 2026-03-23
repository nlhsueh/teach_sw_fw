package ch04.school;

/**
 * Grade 類別：記錄一門課的成績。
 * UML: Student "1" *-- "*" Grade (Composition)
 *      Grade "*" -- "1" Course
 */
public class Grade {
    private Course course;
    private double score;

    public Grade(Course course, double score) {
        this.course = course;
        this.score = score;
    }

    public Course getCourse() { return course; }
    public double getScore() { return score; }

    public String getLetterGrade() {
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }

    @Override
    public String toString() {
        return course.getCourseName() + ": " + score + " (" + getLetterGrade() + ")";
    }
}
