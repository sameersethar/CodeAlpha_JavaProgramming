package Task1_StudentGrades;

import java.util.ArrayList;
import java.util.Scanner;

class Student {
    String name;
    double grade;

    public Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }
}

public class StudentGradeManager {
    static ArrayList<Student> students = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Student Grade Manager ===");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add student");
            System.out.println("2. Show summary report");
            System.out.println("3. Exit");
            System.out.print("Choose an option (1-3): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    showReport();
                    break;
                case 3:
                    System.out.println("Exiting program...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    static void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        System.out.print("Enter grade (0-100): ");
        double grade = scanner.nextDouble();

        if (grade < 0 || grade > 100) {
            System.out.println("Invalid grade. Must be between 0 and 100.");
            return;
        }

        students.add(new Student(name, grade));
        System.out.println("Student added successfully.");
    }

    static void showReport() {
        if (students.isEmpty()) {
            System.out.println("No students to show.");
            return;
        }

        double total = 0;
        double highest = Double.MIN_VALUE;
        double lowest = Double.MAX_VALUE;
        String topStudent = "";
        String lowStudent = "";

      
        System.out.println("\n--- Student Summary Report ---\n");
        for(int i=0; i<30; i++){
            System.out.print("-");
        }
        for (Student s : students) {
            System.out.println(s.name + ": " + s.grade);
            total += s.grade;

            if (s.grade > highest) {
                highest = s.grade;
                topStudent = s.name;
            }
            if (s.grade < lowest) {
                lowest = s.grade;
                lowStudent = s.name;
            }
        }

        double average = total / students.size();
        System.out.printf("Average Grade: %.2f\n", average);
        System.out.println("Highest Grade: " + highest + " (" + topStudent + ")");
        System.out.println("Lowest Grade: " + lowest + " (" + lowStudent + ")");
    }
}
