package Impl1;

class Goalkeeper extends Player {
    private int reflexes;

    public Goalkeeper(String name, int age, double baseSalary) {
        super(name, age, baseSalary);
        this.reflexes = 60;
    }

    @Override
    public void train() {
        skillLevel += 3;
        reflexes += 2;
        setStamina(getStamina() - 15);
        System.out.println(name + " trained goalkeeping skills");
    }

    @Override
    public double calculateSalary() {
        return baseSalary * (1 + (skillLevel + reflexes) / 200.0);
    }

    @Override
    public String getPosition() {
        return "Goalkeeper";
    }

    public void train(String drillType) {
        if (drillType.equals("reflexes")) {
            reflexes += 5;
            setStamina(getStamina() - 20);
        } else {
            train();
        }
    }
}