package school21.AP1JvT02.exercise2;

public class Hamster extends AnimalEx2 implements Herbivore {

    public Hamster(String name, Integer age) {
        super(name, age);
    }

    @Override
    public String chill() {
        return "I can chill for 8 hours";
    }

    @Override
    public String toString() {
        return String.format("Hamster name = %s, age = %d. %s", this.getName(), this.getAge(), chill());
    }
}
