package Impl1;

abstract class Player {
    protected String name;
    protected int age;
    protected double baseSalary;
    protected int skillLevel;
    private int stamina;

    public Player(String name, int age, double baseSalary) {
        this.name = name;
        this.age = age;
        this.baseSalary = baseSalary;
        this.skillLevel = 50;
        this.stamina = 100;
    }

    protected int getStamina() {
        return stamina;
    }

    protected void setStamina(int stamina) {
        this.stamina = Math.max(0, Math.min(100, stamina));
    }

    public abstract void train();
    public abstract double calculateSalary();
    public abstract String getPosition();

    public void displayInfo() {
        System.out.println(name + " (" + getPosition() + ") - Skill: " + skillLevel +
                ", Salary: $" + Math.round(calculateSalary()));
    }

    public String getName() { return name; }
    public int getSkillLevel() { return skillLevel; }
}