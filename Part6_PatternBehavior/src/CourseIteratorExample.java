import java.util.*;

class Student {
    String name;
    public Student(String name) { this.name = name; }
}

class Course {
   HashMap<Student, Integer> gradeBook = new HashMap<>(); // store all grade of students
      
   // 取得成績的瀏覽物件
   public Iterator<Integer> getGradeIterator() {
      return gradeBook.values().iterator();
   }  

   void addGrade(Student s, int grade) {
      gradeBook.put(s, grade);
   }
}

class GradeComputer {
   void computeAverage(Course c) {   
       Iterator<Integer> it = c.getGradeIterator();
       int sum = 0;
       int count = 0;
       while (it.hasNext()) {
           sum += it.next();
           count++;
       }
       double averageGrade = count > 0 ? (double)sum / count : 0;
       System.out.println("Average Grade: " + averageGrade);
   }
}

public class CourseIteratorExample {
   public static void main(String[] args) {
      Course math = new Course();
      math.addGrade(new Student("Nick"), 90);
      math.addGrade(new Student("Mary"), 85);
      
      GradeComputer gc = new GradeComputer();
      gc.computeAverage(math);
   }
}
