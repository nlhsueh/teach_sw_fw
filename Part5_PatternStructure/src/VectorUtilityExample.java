import java.util.Vector;
import java.util.Enumeration;

// 1. Define the Copyable interface
interface Copyable {
    Copyable copy(); // Define copy method
    boolean isCopyable(); // Define check method
}

// 2. Utility class using the Copyable interface
class VectorUtility {
    public static <T extends Copyable> Vector<T> copy(Vector<T> vin) {
        Vector<T> vout = new Vector<>();
        Enumeration<T> e = vin.elements();
        while (e.hasMoreElements()) {
            T c = e.nextElement();
            if (c.isCopyable()) {
                vout.addElement((T) c.copy());
            }
        }
        return vout;
    }
}

// 3. Book class implementing Copyable
class Book implements Copyable {
    private String data;
    private boolean canCopy;

    public Book(String data, boolean canCopy) {
        this.data = data;
        this.canCopy = canCopy;
    }

    public String getData() { return data; }

    @Override
    public Book copy() {
        return new Book(this.data, this.canCopy);
    }

    @Override
    public boolean isCopyable() {
        return this.canCopy;
    }
}

// 4. Student class that DOES NOT support Copyable
class Student {
    private String name;
    private int age;
    private boolean valid;

    public Student(String name, int age, boolean valid) {
        this.name = name;
        this.age = age;
        this.valid = valid;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public boolean isValid() { return valid; }
}

// 5. Adapter to make Student work with VectorUtility
class StudentAdapter implements Copyable {
    private Student student;

    public StudentAdapter(Student student) {
        this.student = student;
    }

    @Override
    public StudentAdapter copy() {
        if (student.isValid()) {
            return new StudentAdapter(new Student(student.getName(), student.getAge(), student.isValid()));
        } else {
            return null;
        }
    }

    @Override
    public boolean isCopyable() {
        return student.isValid();
    }

    public Student getStudent() {
        return student;
    }
}

// 6. Demo code
public class VectorUtilityExample {
    public static void main(String[] args) {
        // Using Book directly
        Vector<Book> originalBooks = new Vector<>();
        originalBooks.addElement(new Book("Book 1", true));
        originalBooks.addElement(new Book("Book 2", false));
        Vector<Book> copiedBooks = VectorUtility.copy(originalBooks);

        // Using Student via Adapter
        Vector<StudentAdapter> studentVector = new Vector<>();
        studentVector.addElement(new StudentAdapter(new Student("Alice", 20, true)));
        studentVector.addElement(new StudentAdapter(new Student("Bob", 22, false)));
        studentVector.addElement(new StudentAdapter(new Student("Charlie", 19, true)));

        Vector<StudentAdapter> copiedStudents = VectorUtility.copy(studentVector);

        System.out.println("Original Student Vector:");
        for (StudentAdapter adapter : studentVector) {
            Student s = adapter.getStudent();
            System.out.println(s.getName() + " (" + s.getAge() + ", Valid: " + s.isValid() + ")");
        }

        System.out.println("\nCopied Student Vector:");
        for (StudentAdapter adapter : copiedStudents) {
            Student s = adapter.getStudent();
            System.out.println(s.getName() + " (" + s.getAge() + ", Valid: " + s.isValid() + ")");
        }
    }
}
