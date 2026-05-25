import java.util.*;

class StudentSolution {
    String name;
    public StudentSolution(String name) { this.name = name; }
}

class CourseSolution {
    private HashMap<StudentSolution, Integer> gradeBook = new HashMap<>(); // 儲存所有學生的成績
       
    public Iterator<Integer> getGradeIterator() {
        return gradeBook.values().iterator();
    }  

    public void addGrade(StudentSolution s, int grade) {
        gradeBook.put(s, grade);
    }
}

class GradeComputerSolution {
    public void computeAverage(CourseSolution c) {   
        Iterator<Integer> it = c.getGradeIterator();
        int sum = 0;
        int count = 0;
        while (it != null && it.hasNext()) {
            sum += it.next();
            count++;
        }
        double averageGrade = count > 0 ? (double)sum / count : 0;
        System.out.println("Average Grade: " + averageGrade);
    }
}

public class CourseIterator {
    public static void main(String[] args) {
        CourseSolution math = new CourseSolution();
        math.addGrade(new StudentSolution("Nick"), 90);
        math.addGrade(new StudentSolution("Mary"), 85);
        math.addGrade(new StudentSolution("John"), 70);
        
        GradeComputerSolution gc = new GradeComputerSolution();
        gc.computeAverage(math);
    }
}
