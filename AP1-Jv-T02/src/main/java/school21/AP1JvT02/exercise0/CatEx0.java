package school21.AP1JvT02.exercise0;

public class CatEx0 extends AnimalEx0 {

    public CatEx0(String name, Integer age) {
        super(name, age);
    }

    @Override
    public String toString() {
        return String.format("Cat name = %s, age = %d", this.getName(), this.getAge());
    }
}
