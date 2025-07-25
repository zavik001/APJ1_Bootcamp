package school21.AP1JvT02.exercise0;

public abstract class AnimalEx0 {
    private String name;
    private Integer age;

    public AnimalEx0(String name, Integer age) {
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
