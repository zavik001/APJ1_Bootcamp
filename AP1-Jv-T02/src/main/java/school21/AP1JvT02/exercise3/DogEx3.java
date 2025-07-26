package school21.AP1JvT02.exercise3;

public class DogEx3 extends AnimalEx3 {

    public DogEx3(String name, Integer age) {
        super(name, age);
    }

    @Override
    public String toString() {
        return String.format("Dog name = %s, age = %d", this.getName(), this.getAge());
    }
}
