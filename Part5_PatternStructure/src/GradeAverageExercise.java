import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

// 1. Target Interface: Iterator
// 2. Adaptee: Enumeration
// 3. Adapter: EnumerationIteratorAdapter

class School {
    public double getAverage(Iterator<Integer> iterator) {
        int sum = 0;
        int count = 0;
        while (iterator.hasNext()) {
            sum += iterator.next();
            count++;
        }
        return count == 0 ? 0 : (double) sum / count;
    }
}

// 隨堂練習：實作轉接器將 Enumeration 轉為 Iterator
class EnumerationIteratorAdapter implements Iterator<Integer> {
    private Enumeration<Integer> enumeration;

    public EnumerationIteratorAdapter(Enumeration<Integer> enumeration) {
        this.enumeration = enumeration;
    }

    @Override
    public boolean hasNext() {
        // // 提示：請呼叫 enumeration 判斷是否還有元素的方法
        // return enumeration.hasMoreElements();
        return false; // 請修改
    }

    @Override
    public Integer next() {
        // // 提示：請呼叫 enumeration 取得下一個元素的方法
        // return enumeration.nextElement();
        return null; // 請修改
    }
}

public class GradeAverageExercise {
    public static void main(String[] args) {
        School school = new School();
        Vector<Integer> group = new Vector<>();
        group.add(80);
        group.add(90);
        group.add(70);

        Enumeration<Integer> enumeration = group.elements();

        // // 提示：利用 Adapter 進行轉接
        // Iterator<Integer> adapter = new EnumerationIteratorAdapter(enumeration);
        // double avg = school.getAverage(adapter);
        
        System.out.println("Average: " /* + avg */);
    }
}
