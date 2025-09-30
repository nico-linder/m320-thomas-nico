package Impl1;

class Forward extends Player {
    private int shooting;

    public Forward(String name, int age, double baseSalary) {
        super(name, age, baseSalary);
        this.shooting = 75;
    }

    @Override
    public void train() {
        skillLevel += 2;
        shooting += 4;
        setStamina(getStamina() - 14);
        System.out.println(name + " trained attacking skills");
    }

    @Override
    public double calculateSalary() {
        return baseSalary * (1 + (skillLevel + shooting) / 160.0);
    }

    @Override
    public String getPosition() {
        return "Forward";
    }

    public void train(String drillType) {
        if (drillType.equals("shooting")) {
            shooting += 5;
            setStamina(getStamina() - 16);
        } else {
            train();
        }
    }
}
