package Impl2;

import java.util.ArrayList;
import java.util.List;

class CourseManager {
    private List<Course> courses;
    private List<Person> people;
    
    public CourseManager() {
        this.courses = new ArrayList<>();
        this.people = new ArrayList<>();
    }
    
    public void addPerson(Person person) {
        people.add(person);
        System.out.println("Added " + person.getRole() + ": " + person.getName());
    }
    
    public void addCourse(Course course) {
        courses.add(course);
        System.out.println("Added course: " + course.getCourseCode());
    }
    
    public void displayAllCourses() {
        System.out.println("\n=== All Courses ===");
        for (Course course : courses) {
            course.displayCourseInfo();
        }
    }
    
    public void displayAllPeople() {
        System.out.println("\n=== All People ===");
        for (Person person : people) {
            person.displayInfo();
            System.out.println("Workload: " + person.calculateWorkload() + " hours/week");
        }
    }
    
    public Course findCourse(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }
    
    public Person findPersonById(int id) {
        for (Person person : people) {
            if (person.getId() == id) {
                return person;
            }
        }
        return null;
    }
    
    public void displayWorkloadSummary() {
        double total = 0.0;
        int count = 0;
        double max = Double.NEGATIVE_INFINITY;
        double min = Double.POSITIVE_INFINITY;
        Person maxPerson = null;
        Person minPerson = null;
        
        for (Person person : people) {
            double load = person.calculateWorkload();
            total += load;
            count++;
            if (load > max) { max = load; maxPerson = person; }
            if (load < min) { min = load; minPerson = person; }
        }
        System.out.println("Total people: " + count);
        System.out.println("Average workload: " + (count == 0 ? 0.0 : total / count));
        if (maxPerson != null) System.out.println("Max workload: " + max + " by " + maxPerson.getName());
        if (minPerson != null) System.out.println("Min workload: " + min + " by " + minPerson.getName());
    }
} 