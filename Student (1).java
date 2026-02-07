package demo4_project_memory;

/**
 * PROJECT: Employee Management - Employee Class
 * Version 1: Memory only (data lost on exit)
 */
public class Student {
    private int id;
    private String name;
    private String branch;
    private int year;
    
    public Student(int id, String name, String branch, int year) {
        this.id = id;
        this.name = name;
        this.branch = branch;
        this.year = year;
    }
    
    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getBranch() { return branch; }
    public double getYear() { return year; }
    
    // Setters
    public void setName(String name) { this.name = name; }
    public void setBranch(String Branch) { this.branch = Branch; }
    public void setYear(int year) { 
        if (year > 0) this.year = year; 
    }
    
    // Display employee details
    public void display() {
        System.out.printf("%d | %-15s | %-10s | %-20s", id, name, branch, year);
    }
}
