package school21.AP1JvT02.exercise2;

public class AnimalFactoryEx2 {

    public static AnimalEx2 creatAnimal(String s, String name, Integer age) {
        return switch (s) {
            case "dog" ->
                new DogEx2(name, age);
            case "cat" ->
                new CatEx2(name, age);
            case "hamster" ->
                new Hamster(name, age);
            case "guinea" ->
                new GuineaPig(name, age);
            default ->
                null;
        };
    }
}
