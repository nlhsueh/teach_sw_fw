package swfw.ch01.labs;

import java.util.Scanner;

/**
 * 練習 1.1.2：銀行帳戶封裝與驗證 (BankAccount)
 * 
 * 要求：
 * 1. 將 balance 設為 private。
 * 2. 實作 getBalance(), deposit(double amount), withdraw(double amount)。
 * 3. 在 deposit 與 withdraw 中檢查金額是否合法（不能為負數，提款不能超過餘額）。
 */
class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        if (initialBalance >= 0) {
            this.balance = initialBalance;
        } else {
            this.balance = 0;
            System.out.println("初始餘額不能為負，已設為 0");
        }
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("已存入: " + amount);
        } else {
            System.out.println("存款金額必須大於 0");
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("提款金額必須大於 0");
        } else if (amount > balance) {
            System.out.println("餘額不足！");
        } else {
            balance -= amount;
            System.out.println("已取出: " + amount);
        }
    }
}

public class BankAccountLab {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankAccount account = new BankAccount(1000);

        System.out.println("--- 銀行帳戶自學練習 ---");
        System.out.println("目前餘額: " + account.getBalance());

        System.out.print("請輸入要存入的金額: ");
        double dep = scanner.nextDouble();
        account.deposit(dep);

        System.out.print("請輸入要領取的金額: ");
        double wit = scanner.nextDouble();
        account.withdraw(wit);

        System.out.println("最終餘額: " + account.getBalance());
    }
}
