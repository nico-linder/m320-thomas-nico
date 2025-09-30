package Impl2;

import java.util.ArrayList;
import java.util.List;

class Student extends Person {
    private List<String> enrolledCourses;
    private double gpa;
    
    public Student(String name, String email, int id, String password) {
        super(name, email, id, password);
        this.enrolledCourses = new ArrayList<>();
        this.gpa = 0.0;
    }
    
    @Override
    public String getRole() {
        return "Student";
    }
    
    @Override
    public double calculateWorkload() {
        return enrolledCourses.size() * 3.5;
    }
    
    @Override
    public void displayInfo() {
        System.out.println("Student: " + name + " (ID: " + id + ") - GPA: " + gpa + 
                          ", Courses: " + enrolledCourses.size());
    }
    
    public boolean enrollInCourse(String courseCode) {
        if (enrolledCourses.size() < 8) {
            enrolledCourses.add(courseCode);
            System.out.println(name + " enrolled in " + courseCode);
            return true;
        }
        System.out.println("Maximum course limit reached for " + name);
        return false;
    }
    
    public void enrollInCourse(String courseCode, double expectedGrade) {
        if (enrollInCourse(courseCode)) {
            updateGPA(expectedGrade);
        }
    }
    
    private void updateGPA(double newGrade) {
        gpa = (gpa * (enrolledCourses.size() - 1) + newGrade) / enrolledCourses.size();
    }
    
    public List<String> getEnrolledCourses() {
        return new ArrayList<>(enrolledCourses);
    }
} 