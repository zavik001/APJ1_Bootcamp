package school21.AP1JvT02.exercise5;

public class CatEx5 extends AnimalEx5 {

    public CatEx5(String name, Integer age) {
        super(name, age);
    }

    @Override
    public String toString() {
        return String.format("Cat name = %s, age = %d", this.getName(), this.getAge());
    }
}
