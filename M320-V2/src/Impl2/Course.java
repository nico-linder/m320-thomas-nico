package Impl2;

import java.util.ArrayList;
import java.util.List;

class Course {
    private String courseCode;
    private String courseName;
    private Teacher teacher;
    private Assistant assistant;
    private List<Student> enrolledStudents;
    private int minStudents;
    private int maxStudents;
    
    public Course(String courseCode, String courseName, int minStudents, int maxStudents) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.minStudents = minStudents;
        this.maxStudents = maxStudents;
        this.enrolledStudents = new ArrayList<>();
    }
    
    public boolean assignTeacher(Teacher teacher) {
        if (this.teacher == null) {
            this.teacher = teacher;
            teacher.assignCourse(courseCode);
            return true;
        }
        return false;
    }
    
    public boolean assignAssistant(Assistant assistant) {
        if (this.assistant == null && teacher != null) {
            this.assistant = assistant;
            assistant.assignToTeacher(teacher.getName(), courseCode);
            return true;
        }
        return false;
    }
    
    public boolean enrollStudent(Student student) {
        if (enrolledStudents.size() < maxStudents) {
            enrolledStudents.add(student);
            student.enrollInCourse(courseCode);
            return true;
        }
        System.out.println("Course " + courseCode + " is full");
        return false;
    }
    
    public boolean canStart() {
        return enrolledStudents.size() >= minStudents && teacher != null;
    }
    
    public void displayCourseInfo() {
        System.out.println("\n=== Course: " + courseCode + " - " + courseName + " ===");
        System.out.println("Enrolled: " + enrolledStudents.size() + "/" + maxStudents + 
                          " (min: " + minStudents + ")");
        if (teacher != null) System.out.println("Teacher: " + teacher.getName());
        if (assistant != null) System.out.println("Assistant: " + assistant.getName());
        System.out.println("Can start: " + canStart());
    }
    
    public String getCourseCode() { return courseCode; }
    public int getEnrolledCount() { return enrolledStudents.size(); }
} 