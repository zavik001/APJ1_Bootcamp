package school21.AP1JvT02.exercise5;

public class DogEx5 extends AnimalEx5 {

    public DogEx5(String name, Integer age) {
        super(name, age);
    }

    @Override
    public String toString() {
        return String.format("Dog name = %s, age = %d", this.getName(), this.getAge());
    }
}
