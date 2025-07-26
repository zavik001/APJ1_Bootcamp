package school21.AP1JvT02.exercise2;

public class DogEx2 extends AnimalEx2 implements Omnivore {

    public DogEx2(String name, Integer age) {
        super(name, age);
    }

    @Override
    public String hunt() {
        return "I can hunt for robbers";
    }

    @Override
    public String toString() {
        return String.format("Dog name = %s, age = %d. %s", this.getName(), this.getAge(), hunt());
    }
}
