package school21.AP1JvT02.exercise1;

public class DogEx1 extends AnimalEx1 {

    static final Double KF = 0.3;

    public DogEx1(String name, Integer age, Double mass) {
        super(name, age, mass);
    }

    @Override
    public Double getFeedInfoKg() {
        return this.getMass() * KF;
    }

    @Override
    public String toString() {
        return String.format("Dog name = %s, age = %d, mass = %.2f, fedd = %.2f", this.getName(), this.getAge(), this.getMass(), this.getFeedInfoKg());
    }
}
