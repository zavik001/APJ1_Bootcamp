package school21.AP1JvT02.exercise0;

public class Dog extends Animal {

    public Dog(String name, Integer age) {
        super(name, age);
    }

    @Override
    public String toString() {
        return "Dog name = " + this.getName() + ", age = " + this.getAge(); 
    }
}
