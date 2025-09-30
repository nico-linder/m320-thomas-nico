package Impl2;

import javax.swing.*;
import java.io.Console;
import java.util.Arrays;
import java.util.Scanner;

public class CourseRegistrationSystem {
    private static CourseManager manager = new CourseManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeData();
        showMainMenu();
    }

    private static void initializeData() {
        Student s1 = new Student("Alice Johnson", "alice@email.com", 1001, "pass123");
        Student s2 = new Student("Bob Smith", "bob@email.com", 1002, "pass456");
        Student s3 = new Student("Carol Davis", "carol@email.com", 1003, "pass789");

        Teacher t1 = new Teacher("Dr. Wilson", "wilson@email.com", 2001, "teachpass", 60000);
        Teacher t2 = new Teacher("Prof. Martinez", "martinez@email.com", 2002, "profpass", 65000);

        Assistant a1 = new Assistant("Mike Brown", "mike@email.com", 3001, "assistpass", 25);
        Assistant a2 = new Assistant("Sarah Lee", "sarah@email.com", 3002, "assistpass2", 28);

        manager.addPerson(s1);
        manager.addPerson(s2);
        manager.addPerson(s3);
        manager.addPerson(t1);
        manager.addPerson(t2);
        manager.addPerson(a1);
        manager.addPerson(a2);

        Course java = new Course("CS101", "Introduction to Java", 2, 5);
        Course algorithms = new Course("CS201", "Data Structures and Algorithms", 3, 8);
        Course database = new Course("CS301", "Database Systems", 2, 6);

        manager.addCourse(java);
        manager.addCourse(algorithms);
        manager.addCourse(database);

        java.assignTeacher(t1);
        java.assignAssistant(a1);
        algorithms.assignTeacher(t2);
        algorithms.assignAssistant(a2);
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("\n========================================");
            System.out.println("    COURSE REGISTRATION SYSTEM");
            System.out.println("========================================");
            System.out.println("1. Student Registration");
            System.out.println("2. Course Management");
            System.out.println("3. View Information");
            System.out.println("4. Staff Management");
            System.out.println("5. Exit");
            System.out.print("Select option (1-5): ");

            int choice = getIntInput();

            switch (choice) {
                case 1: studentMenu(); break;
                case 2: courseMenu(); break;
                case 3: viewMenu(); break;
                case 4: staffMenu(); break;
                case 5:
                    System.out.println("Thank you for using the system!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void studentMenu() {
        while (true) {
            System.out.println("\n=== STUDENT REGISTRATION ===");
            System.out.println("1. Add New Student");
            System.out.println("2. Enroll Student in Course");
            System.out.println("3. View Student Details");
            System.out.println("4. Back to Main Menu");
            System.out.print("Select option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1: addNewStudent(); break;
                case 2: enrollStudentInCourse(); break;
                case 3: viewStudentDetails(); break;
                case 4: return;
                default: System.out.println("Invalid option.");
            }
        }
    }
//
//    private static void courseMenu() {
        while (true) {
        System.out.println("\n=== COURSE MANAGEMENT ===");
        System.out.println("1. Add New Course");
        System.out.println("2. Assign Teacher to Course");
        System.out.println("3. Assign Assistant to Course");
        System.out.println("4. View Course Status");
        System.out.println("5. Back to Main Menu");
        System.out.print("Select option: ");

        int choice = getIntInput();

        switch (choice) {
            case 1: addNewCourse(); break;
            case 2: assignTeacherToCourse(); break;
            case 3: assignAssistantToCourse(); break;
            case 4: viewCourseStatus(); break;
            case 5: return;
            default: System.out.println("Invalid option.");
        }
    }
}

private static void viewMenu() {
    while (true) {
        System.out.println("\n=== VIEW INFORMATION ===");
        System.out.println("1. View All Courses");
        System.out.println("2. View All People");
        System.out.println("3. View Workload Summary");
        System.out.println("4. Back to Main Menu");
        System.out.print("Select option: ");

        int choice = getIntInput();

        switch (choice) {
            case 1: manager.displayAllCourses(); break;
            case 2: manager.displayAllPeople(); break;
            case 3: displayWorkloadSummary(); break;
            case 4: return;
            default: System.out.println("Invalid option.");
        }
    }
}

private static void staffMenu() {
    while (true) {
        System.out.println("\n=== STAFF MANAGEMENT ===");
        System.out.println("1. Add New Teacher");
        System.out.println("2. Add New Assistant");
        System.out.println("3. Update Teacher Experience");
        System.out.println("4. Back to Main Menu");
        System.out.print("Select option: ");

        int choice = getIntInput();

        switch (choice) {
            case 1: addNewTeacher(); break;
            case 2: addNewAssistant(); break;
            case 3: updateTeacherExperience(); break;
            case 4: return;
            default: System.out.println("Invalid option.");
        }
    }
}

private static void addNewStudent() {
    System.out.print("Enter student name: ");
    String name = scanner.nextLine();
    System.out.print("Enter email: ");
    String email = scanner.nextLine();
    System.out.print("Enter student ID: ");
    int id = getIntInput();
    System.out.println("Enter password: ");
    String password = scanner.nextLine();

    Student student = new Student(name, email, id, password);
    manager.addPerson(student);
}

private static void enrollStudentInCourse() {
    System.out.print("Enter student ID: ");
    int studentId = getIntInput();
    System.out.print("Enter course code: ");
    String courseCode = scanner.nextLine();

    Person student = manager.findPersonById(studentId);
    Course course = manager.findCourse(courseCode);

    if (student instanceof Student && course != null) {
        course.enrollStudent((Student) student);
    } else {
        System.out.println("Student or course not found.");
    }
}

private static void viewStudentDetails() {
    System.out.print("Enter student ID: ");
    int studentId = getIntInput();

    Person student = manager.findPersonById(studentId);
    if (student instanceof Student) {
        student.displayInfo();
        System.out.println("Enrolled courses: " + ((Student) student).getEnrolledCourses());
    } else {
        System.out.println("Student not found.");
    }
}

private static void addNewCourse() {
    System.out.print("Enter course code: ");
    String code = scanner.nextLine();
    System.out.print("Enter course name: ");
    String name = scanner.nextLine();
    System.out.print("Enter minimum students: ");
    int min = getIntInput();
    System.out.print("Enter maximum students: ");
    int max = getIntInput();

    Course course = new Course(code, name, min, max);
    manager.addCourse(course);
}

private static void assignTeacherToCourse() {
    System.out.print("Enter course code: ");
    String courseCode = scanner.nextLine();
    System.out.print("Enter teacher ID: ");
    int teacherId = getIntInput();

    Course course = manager.findCourse(courseCode);
    Person teacher = manager.findPersonById(teacherId);

    if (course != null && teacher instanceof Teacher) {
        course.assignTeacher((Teacher) teacher);
    } else {
        System.out.println("Course or teacher not found.");
    }
}

private static void assignAssistantToCourse() {
    System.out.print("Enter course code: ");
    String courseCode = scanner.nextLine();
    System.out.print("Enter assistant ID: ");
    int assistantId = getIntInput();

    Course course = manager.findCourse(courseCode);
    Person assistant = manager.findPersonById(assistantId);

    if (course != null && assistant instanceof Assistant) {
        course.assignAssistant((Assistant) assistant);
    } else {
        System.out.println("Course or assistant not found.");
    }
}

private static void viewCourseStatus() {
    System.out.print("Enter course code: ");
    String courseCode = scanner.nextLine();

    Course course = manager.findCourse(courseCode);
    if (course != null) {
        course.displayCourseInfo();
    } else {
        System.out.println("Course not found.");
    }
}

private static void addNewTeacher() {
    System.out.print("Enter teacher name: ");
    String name = scanner.nextLine();
    System.out.print("Enter email: ");
    String email = scanner.nextLine();
    System.out.print("Enter teacher ID: ");
    int id = getIntInput();
    System.out.print("Enter password: ");
    String password = scanner.nextLine();
    System.out.print("Enter base salary: ");
    double salary = getDoubleInput();

    Teacher teacher = new Teacher(name, email, id, password, salary);
    manager.addPerson(teacher);
}

private static void addNewAssistant() {
    System.out.print("Enter assistant name: ");
    String name = scanner.nextLine();
    System.out.print("Enter email: ");
    String email = scanner.nextLine();
    System.out.print("Enter assistant ID: ");
    int id = getIntInput();
    System.out.print("Enter password: ");
    String password = scanner.nextLine();
    System.out.print("Enter hourly rate: ");
    double rate = getDoubleInput();

    Assistant assistant = new Assistant(name, email, id, password, rate);
    manager.addPerson(assistant);
}

private static void updateTeacherExperience() {
    System.out.print("Enter teacher ID: ");
    int teacherId = getIntInput();
    System.out.print("Enter years to add: ");
    int years = getIntInput();

    Person teacher = manager.findPersonById(teacherId);
    if (teacher instanceof Teacher) {
        ((Teacher) teacher).addExperience(years);
        System.out.println("Experience updated successfully.");
    } else {
        System.out.println("Teacher not found.");
    }
}

private static void displayWorkloadSummary() {
    System.out.println("\n=== WORKLOAD SUMMARY ===");
    manager.displayWorkloadSummary();
}

private static int getIntInput() {
    while (true) {
        try {
            int result = scanner.nextInt();
            scanner.nextLine();
            return result;
        } catch (Exception e) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.nextLine();
        }
    }
}

private static double getDoubleInput() {
    while (true) {
        try {
            double result = scanner.nextDouble();
            scanner.nextLine();
            return result;
        } catch (Exception e) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.nextLine();
        }
    }
}
}