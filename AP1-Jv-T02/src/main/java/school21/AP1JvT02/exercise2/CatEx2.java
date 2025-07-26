package school21.AP1JvT02.exercise2;

public class CatEx2 extends AnimalEx2 implements Omnivore {

    public CatEx2(String name, Integer age) {
        super(name, age);
    }

    @Override
    public String hunt() {
        return "I can hunt for mice";
    }

    @Override
    public String toString() {
        return String.format("Cat name = %s, age = %d. %s", this.getName(), this.getAge(), hunt());
    }
}
