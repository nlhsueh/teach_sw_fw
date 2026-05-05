import java.util.*;

class Student {
    int score;
    int getScore() { return score;}
}

class GradeComputer {
     void computeAverage(ArrayList<Student> s) {
        // compute average
     }

     double getAverage(Iterator<Student> iterator) {
         int sum = 0;
         int count = 0;
         while (iterator.hasNext()) {
             sum += iterator.next().getScore();
             count++;
         }
         return count > 0 ? (double)sum / count : 0;
     }
}
