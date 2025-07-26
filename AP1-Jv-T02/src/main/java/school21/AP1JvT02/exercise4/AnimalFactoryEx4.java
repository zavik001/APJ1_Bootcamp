package school21.AP1JvT02.exercise4;

public class AnimalFactoryEx4 {

    public static AnimalEx4 creatAnimal(String s, String name, Integer age) {
        return switch (s) {
            case "dog" ->
                new DogEx4(name, age);
            case "cat" ->
                new CatEx4(name, age);
            default ->
                null;
        };
    }
}
