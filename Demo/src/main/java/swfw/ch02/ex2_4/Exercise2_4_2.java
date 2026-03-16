package swfw.ch02.ex2_4;

class Student implements Comparable<Student> {
    String name;
    double height;
    double weight;
    double score;

    public Student(String name, double height, double weight, double score) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.score = score;
    }

    public double getEvaluateScore() {
        return height + score - weight;
    }

    @Override
    public int compareTo(Student o) {
        // "身高+成績-體重 比較高，則較好" -> higher evaluateScore comes first
        return Double.compare(o.getEvaluateScore(), this.getEvaluateScore());
    }

    @Override
    public String toString() {
        return String.format("%s (Score: %.1f, H: %.1f, W: %.1f) => Eval: %.1f", name, score, height, weight, getEvaluateScore());
    }
}

public class Exercise2_4_2 {
    public static <T extends Comparable<T>> void selectionSort(T[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (array[j].compareTo(array[minIdx]) < 0) {
                    minIdx = j;
                }
            }
            T temp = array[minIdx];
            array[minIdx] = array[i];
            array[i] = temp;
        }
    }

    public static void main(String[] args) {
        Student[] students = {
            new Student("A", 170, 60, 80),  // 170+80-60 = 190
            new Student("B", 180, 70, 90),  // 180+90-70 = 200
            new Student("C", 160, 50, 70)   // 160+70-50 = 180
        };

        selectionSort(students);

        for (Student s : students) {
            System.out.println(s);
        }
    }
}
