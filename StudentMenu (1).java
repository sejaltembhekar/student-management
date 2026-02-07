import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentMenu {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Student> students = new ArrayList<>();
    static final String FILE_NAME = "students.txt";

    // Student class
    static class Student {
        int id;
        String name;
        String department;

        Student(int id, String name, String department) {
            this.id = id;
            this.name = name;
            this.department = department;
        }

        void display() {
            System.out.println(id + " | " + name + " | " + department);
        }

        String toFileString() {
            return id + "," + name + "," + department;
        }
    }

    public static void main(String[] args) {
        System.out.println();
        System.out.println("===========================");
        System.out.println("STUDENT MANAGEMENT SYSTEM");
        System.out.println("===========================");

        loadFromDB();

        int choice;

        do {
            printMenu();
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addStudent();
                    break;

                case 2:
                    viewAll();
                    break;

                case 3:
                    searchById();
                    break;

                case 4:
                    updateStudent();
                    saveToFile();
                    break;

                case 5:
                    deleteStudent();
                    saveToFile();
                    break;
                case 6:
                    System.out.println("All data is now saved in file");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 6);

        sc.close();
    }

    // Menu
    static void printMenu() {
        System.out.println("\n==== MENU ====");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student by ID");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. Exit");
        System.out.print("Enter choice: ");
    }

    // Add student
    static void addStudent() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Department: ");
        String dept = sc.nextLine();

        String query = "INSERT INTO student VALUES (" + id + ", '" + name + "', '" + dept + "')";
        DBUtils.executeQuery(query);

        students.add(new Student(id, name, dept));
        System.out.println("Student added successfully!");
        saveToFile();
    }

    // View all students
    static void viewAll() {
        System.out.println("ID|NAME|DEPARTMENT");
        System.out.println("---------------------");
        for (Student s : students) {
            s.display();
        }
    }

    // Search by ID
    static void searchById() {
        System.out.print("Enter Student ID to search: ");
        int id = sc.nextInt();
        for (Student s : students) {
            if (s.id == id) {
                System.out.println("Student Found:");
                s.display();
                return;
            }
        }
        System.out.println("Student not found.");
    }

    // Update student
    static void updateStudent() {
        System.out.print("Enter Student ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Student s : students) {
            if (s.id == id) {
                System.out.print("Enter new Name: ");
                s.name = sc.nextLine();

                System.out.print("Enter new Department: ");
                s.department = sc.nextLine();

                System.out.println("Student updated successfully!");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    // Delete student
    static void deleteStudent() {
        System.out.print("Enter Student ID to delete: ");
        int id = sc.nextInt();

        boolean removed = students.removeIf(s -> s.id == id);

        if (removed) {
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Student not found.");
        }
    }

    static void saveToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME));

            for (Student s : students) {
                bw.write(s.toFileString());
                bw.newLine();
            }

            bw.close();
            System.out.println("[Data saved to " + FILE_NAME + "]");

        } catch (IOException ex) {
            System.out.println("Error saving data: " + ex.getMessage());
        }
    }

    static void loadFromDB() 
    {
        students.clear();
        ResultSet rs = DBUtils.executeQueryGetResult("SELECT * FROM student");
        
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String dept = rs.getString("department");
                students.add(new Student(id, name, dept));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
}