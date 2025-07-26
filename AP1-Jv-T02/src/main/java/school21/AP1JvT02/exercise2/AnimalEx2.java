package school21.AP1JvT02.exercise2;

public abstract class AnimalEx2 {

    String name;
    Integer age;

    public AnimalEx2(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public Integer getAge() {
        return this.age;
    }
}
