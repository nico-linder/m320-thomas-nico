package Impl1;

class Midfielder extends Player {
    private int passing;

    public Midfielder(String name, int age, double baseSalary) {
        super(name, age, baseSalary);
        this.passing = 70;
    }

    @Override
    public void train() {
        skillLevel += 2;
        passing += 3;
        setStamina(getStamina() - 10);
        System.out.println(name + " trained midfield skills");
    }

    @Override
    public double calculateSalary() {
        return baseSalary * (1 + (skillLevel + passing) / 170.0);
    }

    @Override
    public String getPosition() {
        return "Midfielder";
    }

    public void train(String drillType) {
        if (drillType.equals("passing")) {
            passing += 4;
            setStamina(getStamina() - 8);
        } else {
            train();
        }
    }
}