package school21.AP1JvT02.exercise0;

public class AnimalFactoryEx0 {

    public static AnimalEx0 creatAnimal(String s, String name, Integer age) {
        return switch (s) {
            case "dog" ->
                new DogEx0(name, age);
            case "cat" ->
                new CatEx0(name, age);
            default ->
                null;
        };
    }
}
