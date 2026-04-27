package school;

public class SchoolSystem {

    public void registerStudent(String firstName, String lastName, String email, String phone, 
                                String street, String city, String zipCode, int studentType) {
        
        String fullName = firstName + " " + lastName;
        System.out.println("正在註冊學生: " + fullName);
        
        String typeLabel = "";
        if (studentType == 1) {
            typeLabel = "一般生";
        } else if (studentType == 2) {
            typeLabel = "交換生";
        } else if (studentType == 3) {
            typeLabel = "研究生";
        }
        
        System.out.println("學生類別: " + typeLabel);
        System.out.println("聯絡資訊: " + email + ", " + phone);
        System.out.println("通訊地址: " + street + ", " + city + " " + zipCode);
    }

    public void calculateAndPrintReport(String studentName, double mathScore, double englishScore, double scienceScore) {
        double total = mathScore + englishScore + scienceScore;
        double average = total / 3;
        
        String grade = "";
        if (average >= 90) {
            grade = "A";
        } else if (average >= 80) {
            grade = "B";
        } else if (average >= 70) {
            grade = "C";
        } else if (average >= 60) {
            grade = "D";
        } else {
            grade = "F";
        }

        System.out.println("===== 學生成績單 =====");
        System.out.println("姓名: " + studentName);
        System.out.println("數學分數: " + mathScore);
        System.out.println("英文分數: " + englishScore);
        System.out.println("科學分數: " + scienceScore);
        System.out.println("--------------------");
        System.out.println("總分: " + total);
        System.out.println("平均: " + String.format("%.2f", average));
        System.out.println("最終等第: " + grade);
        System.out.println("====================");
    }

    public void printCourseInfo(Course course) {
        System.out.println("課程名稱: " + course.getTitle());
        System.out.println("授課教師: " + course.getTeacherName());
        System.out.println("學分數: " + course.getCredits());
        if (course.getCredits() > 3) {
            System.out.println("這是一門重課。");
        }
    }
}

class Course {
    private String title;
    private String teacherName;
    private int credits;

    public Course(String title, String teacherName, int credits) {
        this.title = title;
        this.teacherName = teacherName;
        this.credits = credits;
    }

    public String getTitle() { return title; }
    public String getTeacherName() { return teacherName; }
    public int getCredits() { return credits; }
}
