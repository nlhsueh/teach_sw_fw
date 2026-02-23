package swfw.labs.lab01;

/**
 * 練習 1.4.1：基礎物件與封裝練習 (Student)
 * 
 * 任務：
 * 1. 將 Student 類別的欄位改為私有 (private)。
 * 2. 實作 Getter 與 Setter。
 * 3. 在 setGrade 方法中加入驗證邏輯：年級必須在 1 到 4 之間。
 * 4. 實作一個判斷是否為「高年級」的方法 (isSenior)，定義為 3 年級或 4 年級。
 */
class Student {
    // TODO: 1. 將以下欄位改為私有
    public String id;
    public String name;
    public int grade;

    public Student(String id, String name, int grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    // TODO: 2. 實作 id 與 name 的 Getter

    // TODO: 3. 實作 grade 的 Getter

    // TODO: 3. 實作 grade 的 Setter，需包含邏輯驗證 (1~4)
    public void setGrade(int grade) {
        // 請在此實作...
    }

    // TODO: 4. 實作 isSenior 方法
    public boolean isSenior() {
        // 請在此實作... (回傳 grade 是否為 3 或 4)
        return false;
    }

    @Override
    public String toString() {
        return "Student[ID=" + id + ", Name=" + name + ", Grade=" + grade + "]";
    }
}

public class StudentLab {
    public static void main(String[] args) {
        System.out.println("--- Lab 01 測試開始 ---");

        // 建立學生
        Student s = new Student("112001", "張小明", 2);
        System.out.println("初始學生資訊: " + s);

        // 嘗試修改年級
        s.setGrade(3);
        System.out.println("升級後: " + s + ", 是否為高年級? " + s.isSenior());

        // 嘗試設定錯誤年級
        System.out.println("\n嘗試設定錯誤年級 (99):");
        s.setGrade(99);

        System.out.println("最終學生資訊: " + s);
    }
}
