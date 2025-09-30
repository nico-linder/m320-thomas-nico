package Impl2;

import java.util.ArrayList;
import java.util.List;

abstract class Person {
    protected String name;
    protected String email;
    protected int id;
    private String password;
    
    public Person(String name, String email, int id, String password) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.password = password;
    }
    
    protected boolean verifyPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }
    
    protected void setPassword(String newPassword) {
        this.password = newPassword;
    }
    
    public abstract String getRole();
    public abstract double calculateWorkload();
    public abstract void displayInfo();
    
    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getId() { return id; }
}
