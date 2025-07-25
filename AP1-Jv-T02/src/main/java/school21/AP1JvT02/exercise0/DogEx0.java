package school21.AP1JvT02.exercise0;

public class DogEx0 extends AnimalEx0 {

    public DogEx0(String name, Integer age) {
        super(name, age);
    }

    @Override
    public String toString() {
        return "Dog name = " + this.getName() + ", age = " + this.getAge(); 
    }
}
