package school21.AP1JvT02.exercise1;

public class CatEx1 extends AnimalEx1 {

    static final Double KF = 0.1;

    public CatEx1(String name, Integer age, Double mass) {
        super(name, age, mass);
    }

    @Override
    public Double getFeedInfoKg() {
        return this.getMass() * KF;
    }

    @Override
    public String toString() {
        return String.format("Cat name = %s, age = %d, mass = %.2f, fedd = %.2f", this.getName(), this.getAge(), this.getMass(), this.getFeedInfoKg());
    }
}
