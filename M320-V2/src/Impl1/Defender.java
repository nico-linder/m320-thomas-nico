package Impl1;

class Defender extends Player {
    private int tackling;

    public Defender(String name, int age, double baseSalary) {
        super(name, age, baseSalary);
        this.tackling = 65;
    }

    @Override
    public void train() {
        skillLevel += 2;
        tackling += 3;
        setStamina(getStamina() - 12);
        System.out.println(name + " trained defensive skills");
    }

    @Override
    public double calculateSalary() {
        return baseSalary * (1 + (skillLevel + tackling) / 180.0);
    }

    @Override
    public String getPosition() {
        return "Defender";
    }

    public void train(String drillType) {
        if (drillType.equals("tackling")) {
            tackling += 4;
            setStamina(getStamina() - 18);
        } else {
            train();
        }
    }
}