package school21.AP1JvT02.exercise3;

public class CatEx3 extends AnimalEx3 {

    public CatEx3(String name, Integer age) {
        super(name, age);
    }

    @Override
    public String toString() {
        return String.format("Cat name = %s, age = %d", this.getName(), this.getAge());
    }
}
