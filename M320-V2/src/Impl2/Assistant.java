package Impl2;

import java.util.ArrayList;
import java.util.List;

class Assistant extends Person {
    private String supervisingTeacher;
    private List<String> assistedCourses;
    private double hourlyRate;
    
    public Assistant(String name, String email, int id, String password, double hourlyRate) {
        super(name, email, id, password);
        this.assistedCourses = new ArrayList<>();
        this.hourlyRate = hourlyRate;
    }
    
    @Override
    public String getRole() {
        return "Assistant";
    }
    
    @Override
    public double calculateWorkload() {
        return assistedCourses.size() * 4.0;
    }
    
    @Override
    public void displayInfo() {
        System.out.println("Assistant: " + name + " (ID: " + id + ") - Supervisor: " + 
                          supervisingTeacher + ", Assisting: " + assistedCourses.size() + " courses");
    }
    
    public void assignToTeacher(String teacherName) {
        this.supervisingTeacher = teacherName;
        System.out.println(name + " assigned to assist " + teacherName);
    }
    
    public void assignToTeacher(String teacherName, String courseCode) {
        assignToTeacher(teacherName);
        assistedCourses.add(courseCode);
        System.out.println(name + " will assist with " + courseCode);
    }
    
    public double calculatePay() {
        return calculateWorkload() * hourlyRate;
    }
} 