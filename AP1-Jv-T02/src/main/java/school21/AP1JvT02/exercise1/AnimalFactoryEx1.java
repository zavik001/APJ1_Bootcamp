package school21.AP1JvT02.exercise1;

public class AnimalFactoryEx1 {

    public static AnimalEx1 creatAnimal(String s, String name, Integer age, Double mass) {
        return switch (s) {
            case "dog" ->
                new DogEx1(name, age, mass);
            case "cat" ->
                new CatEx1(name, age, mass);
            default ->
                null;
        };
    }
}
