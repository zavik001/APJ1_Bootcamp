package school21.AP1JvT02.exercise4;

public abstract class AnimalEx4 {

    private String name;
    private Integer age;

    public AnimalEx4(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public abstract Double goToWalk();

    public String getName() {
        return this.name;
    }

    public Integer getAge() {
        return this.age;
    }
}
