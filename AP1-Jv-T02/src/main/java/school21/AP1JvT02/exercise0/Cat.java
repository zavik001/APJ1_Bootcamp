package school21.AP1JvT02.exercise0;

public class Cat extends Animal {

    public Cat(String name, Integer age) {
        super(name, age);
    }

    @Override
    public String toString() {
        return "Cat name = " + this.getName() + ", age = " + this.getAge();
    }
}
