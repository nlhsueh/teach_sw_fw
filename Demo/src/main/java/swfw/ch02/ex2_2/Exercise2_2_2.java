package swfw.ch02.ex2_2;

import java.util.ArrayList;

abstract class Employee {
    protected String name;

    public Employee(String name) {
        this.name = name;
    }

    public abstract double calculateSalary();
}

class FullTimeEmployee extends Employee {
    private double monthlySalary;

    public FullTimeEmployee(String name, double monthlySalary) {
        super(name);
        this.monthlySalary = monthlySalary;
    }

    @Override
    public double calculateSalary() {
        return monthlySalary;
    }
}

class PartTimeEmployee extends Employee {
    private double hourlyRate;
    private int hoursWorked;

    public PartTimeEmployee(String name, double hourlyRate, int hoursWorked) {
        super(name);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double calculateSalary() {
        return hourlyRate * hoursWorked;
    }
}

public class Exercise2_2_2 {
    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new FullTimeEmployee("Alice", 50000));
        employees.add(new PartTimeEmployee("Bob", 200, 120)); // 24000
        employees.add(new PartTimeEmployee("Charlie", 180, 80)); // 14400

        double totalSalary = 0;
        for (Employee e : employees) {
            double salary = e.calculateSalary();
            System.out.println(e.name + " salary: " + salary);
            totalSalary += salary;
        }

        System.out.println("Total Salary Expense: " + totalSalary);
    }
}
