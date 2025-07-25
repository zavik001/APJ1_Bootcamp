package school21.AP1JvT02.exercise1;

public abstract class AnimalEx1 {

    private String name;
    private Integer age;
    private Double mass;

    public AnimalEx1(String name, Integer age, Double weight) {
        this.name = name;
        this.age = age;
        this.mass = weight;
    }

    public String getName() {
        return this.name;
    }

    public Integer getAge() {
        return this.age;
    }

    public Double getMass() {
        return this.mass;
    }

    public abstract Double getFeedInfoKg();
}
