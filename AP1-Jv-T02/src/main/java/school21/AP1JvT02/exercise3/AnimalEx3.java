package school21.AP1JvT02.exercise3;

public abstract class AnimalEx3 {

    String name;
    Integer age;

    public AnimalEx3(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public Integer getAge() {
        return this.age;
    }

    public void incrementAge() {
        this.age++;
    }
}
