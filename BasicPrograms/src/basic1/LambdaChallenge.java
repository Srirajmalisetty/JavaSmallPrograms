package basic1;

import java.util.*;
import java.util.stream.Collectors;

class Employeesalary {
    private String name;
    private double salary;
    private String department;
    private int age;

    public Employeesalary(String name, double salary, String department, int age) {
        this.name = name;
        this.salary = salary;
        this.department = department;
        this.age = age;
    }

    public String getName() { return name; }
    public double getSalary() { return salary; }
    public String getDepartment() { return department; }
    public int getAge() { return age; }

    public void setSalary(double salary) { this.salary = salary; }

    @Override
    public String toString() {
        return name + " (" + salary + ", " + age + ")";
    }
}

public class LambdaChallenge {
    public static void main(String[] args) {
        List<Employeesalary> employees = Arrays.asList(
            new Employeesalary("Alice", 60000, "IT", 28),
            new Employeesalary("Bob", 45000, "Sales", 35),
            new Employeesalary("Charlie", 70000, "IT", 42),
            new Employeesalary("David", 55000, "Marketing", 31),
            new Employeesalary("Eve", 80000, "Sales", 29),
            new Employeesalary("Frank", 48000, "IT", 45)
        );

        // 1. Filter salaries > 50000
        System.out.println("1. Employees earning > 50000:");
        employees.stream()
            .filter(e -> e.getSalary() > 50000)
            .forEach(e -> System.out.println("   " + e.getName() + " (" + e.getSalary() + ")"));

        // 2. IT department names sorted
        List<String> itNames = employees.stream()
            .filter(e -> e.getDepartment().equals("IT"))
            .map(Employeesalary::getName)
            .sorted()
            .collect(Collectors.toList());
        System.out.println("\n2. IT department names (sorted):\n   " + itNames);

        // 3. Average salary of employees aged > 30
        double avgSalary = employees.stream()
            .filter(e -> e.getAge() > 30)
            .mapToDouble(Employeesalary::getSalary)
            .average()
            .orElse(0);
        System.out.printf("\n3. Average salary of employees > 30 years: %.2f%n", avgSalary);

        // 4. Sort by salary desc, then age asc
        System.out.println("\n4. Sorted by salary desc, age asc:");
        employees.stream()
            .sorted(Comparator.comparingDouble(Employeesalary::getSalary).reversed()
                    .thenComparingInt(Employeesalary::getAge))
            .forEach(e -> System.out.println("   " + e));

        // 5. Increase Sales salary by 10%
        System.out.println("\n5. Sales department +10% salary:");
        employees.stream()
            .filter(e -> e.getDepartment().equals("Sales"))
            .forEach(e -> {
                e.setSalary(e.getSalary() * 1.10);
                System.out.println("   " + e.getName() + ": " + e.getSalary());
            });
    }
}