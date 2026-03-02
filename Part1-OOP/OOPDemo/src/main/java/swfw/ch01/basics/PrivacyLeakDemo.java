package swfw.ch01.basics;

import java.util.Date;

/**
 * 演示 隱私洩漏 (Privacy Leak) 與 防禦性複製 (Defensive Copy)。
 */
class VulnerableEmployee {
    private Date hireDate;

    public VulnerableEmployee(Date hireDate) {
        this.hireDate = hireDate; // 直接儲存參考
    }

    public Date getHireDate() {
        return hireDate; // 直接回傳參考 (洩漏！)
    }

    @Override
    public String toString() {
        return "VulnerableEmployee[hireDate=" + hireDate + "]";
    }
}

class SecureEmployee {
    private Date hireDate;

    public SecureEmployee(Date hireDate) {
        // 防禦性複製：儲存副本而非原始參考
        this.hireDate = new Date(hireDate.getTime());
    }

    public Date getHireDate() {
        // 防禦性複製：回傳副本
        return new Date(hireDate.getTime());
    }

    @Override
    public String toString() {
        return "SecureEmployee[hireDate=" + hireDate + "]";
    }
}

public class PrivacyLeakDemo {
    public static void main(String[] args) {
        System.out.println("--- 隱私洩漏演示 (Vulnerable) ---");
        Date date = new Date();
        VulnerableEmployee vEmp = new VulnerableEmployee(date);
        System.out.println("修改前: " + vEmp);

        Date leakedDate = vEmp.getHireDate();
        leakedDate.setTime(0); // 外部修改了內部屬性
        System.out.println("外部修改後: " + vEmp);

        System.out.println("\n--- 防禦性複製演示 (Secure) ---");
        Date date2 = new Date();
        SecureEmployee sEmp = new SecureEmployee(date2);
        System.out.println("修改前: " + sEmp);

        Date safeDate = sEmp.getHireDate();
        safeDate.setTime(0); // 修改的是副本
        System.out.println("外部嘗試修改後: " + sEmp);
    }
}
