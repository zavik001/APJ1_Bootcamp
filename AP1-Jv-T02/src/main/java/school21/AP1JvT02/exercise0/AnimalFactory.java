package school21.AP1JvT02.exercise0;

public class AnimalFactory {

    public static Animal creatAnimal(String s, String name, Integer age) {
        return switch (s) {
            case "dog" ->
                new Dog(name, age);
            case "cat" ->
                new Cat(name, age);
            default ->
                null;
        };
    }
}
