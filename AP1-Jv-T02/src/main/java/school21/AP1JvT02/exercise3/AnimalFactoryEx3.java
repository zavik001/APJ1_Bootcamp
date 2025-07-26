package school21.AP1JvT02.exercise3;

public class AnimalFactoryEx3 {

    public static AnimalEx3 creatAnimal(String s, String name, Integer age) {
        return switch (s) {
            case "dog" ->
                new DogEx3(name, age);
            case "cat" ->
                new CatEx3(name, age);
            default ->
                null;
        };
    }
}
