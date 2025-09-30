package Impl2;

import java.util.ArrayList;
import java.util.List;

class Teacher extends Person {
    private List<String> taughtCourses;
    private double baseSalary;
    private int yearsExperience;
    
    public Teacher(String name, String email, int id, String password, double baseSalary) {
        super(name, email, id, password);
        this.taughtCourses = new ArrayList<>();
        this.baseSalary = baseSalary;
        this.yearsExperience = 0;
    }
    
    @Override
    public String getRole() {
        return "Teacher";
    }
    
    @Override
    public double calculateWorkload() {
        return taughtCourses.size() * 8.0 + yearsExperience * 0.5;
    }
    
    @Override
    public void displayInfo() {
        System.out.println("Teacher: " + name + " (ID: " + id + ") - Experience: " + 
                          yearsExperience + " years, Courses: " + taughtCourses.size());
    }
    
    public void assignCourse(String courseCode) {
        if (taughtCourses.size() < 4) {
            taughtCourses.add(courseCode);
            System.out.println(name + " assigned to teach " + courseCode);
        } else {
            System.out.println("Maximum teaching load reached for " + name);
        }
    }
    
    public void assignCourse(String courseCode, int creditHours) {
        assignCourse(courseCode);
        System.out.println("Course " + courseCode + " has " + creditHours + " credit hours");
    }
    
    public double calculateSalary() {
        return baseSalary * (1 + yearsExperience * 0.05) * (1 + taughtCourses.size() * 0.1);
    }
    
    public void addExperience(int years) {
        yearsExperience += years;
    }
} 