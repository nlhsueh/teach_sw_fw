import java.util.*;

class Student {
    String name;
    public Student(String name) { this.name = name; }
}

class Course {
    private HashMap<Student, Integer> gradeBook = new HashMap<>(); // 儲存所有學生的成績
       
    // 💡 課堂練習提示 1：請實作取得成績瀏覽物件 (Iterator) 的方法
    // 提示：可以利用 Map 的 values() 所得到的 Collection，再呼叫其 iterator() 方法取得。
    public Iterator<Integer> getGradeIterator() {
        // [TODO: 請回傳對應的成績 Iterator]
        return null;
    }  

    public void addGrade(Student s, int grade) {
        gradeBook.put(s, grade);
    }
}

class GradeComputer {
    // 💡 課堂練習提示 2：請應用 Iterator 模式來計算平均成績，不直接暴露與相依於 Course 內部的儲存結構 (HashMap)
    public void computeAverage(Course c) {   
        // [TODO: 1. 自 Course 物件中取得成績的 Iterator]
        
        // [TODO: 2. 使用 Iterator 的 hasNext() 與 next() 進行迴圈加總，並計算出平均分數]
        
        
        // [TODO: 3. 印出計算好的平均分數]
        
    }
}

public class CourseIteratorExample {
    public static void main(String[] args) {
        Course math = new Course();
        math.addGrade(new Student("Nick"), 90);
        math.addGrade(new Student("Mary"), 85);
        math.addGrade(new Student("John"), 70);
        
        GradeComputer gc = new GradeComputer();
        gc.computeAverage(math);
    }
}
