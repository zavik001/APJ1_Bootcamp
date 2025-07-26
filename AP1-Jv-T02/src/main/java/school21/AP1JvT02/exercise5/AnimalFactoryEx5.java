package school21.AP1JvT02.exercise5;

public class AnimalFactoryEx5 {

    public static AnimalEx5 creatAnimal(String s, String name, Integer age) {
        return switch (s) {
            case "dog" ->
                new DogEx5(name, age);
            case "cat" ->
                new CatEx5(name, age);
            default ->
                null;
        };
    }
}
