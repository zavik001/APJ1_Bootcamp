package school21.AP1JvT02.exercise2;

public class GuineaPig extends AnimalEx2 implements Herbivore {

    public GuineaPig(String name, Integer age) {
        super(name, age);
    }

    @Override
    public String chill() {
        return "I can chill for 12 hours";
    }

    @Override
    public String toString() {
        return String.format("GuineaPig name = %s, age = %d. %s", this.getName(), this.getAge(), chill());
    }
}
