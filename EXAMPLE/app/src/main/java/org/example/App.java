package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

import org.example.animals.Cat;
import org.example.animals.Dog;

public class App {

    static final Consumer<String> print = System.out::println;
    static final Random random = new Random();

    public static void main(String[] args) {
        example2();
    }

    static void example1() {
        // cat: a.equals(b) -> a.hashCode() == b.hashCode()
        Map<Cat, String> cats = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            cats.put(new Cat(random.nextInt() + "", random.nextInt()), "cat" + random.nextInt());
        }
        cats.entrySet()
                .stream()
                .map(entry -> entry.getKey().toString() + entry.getValue())
                .forEach(print);
    }

    static void example2() {
        // dog: a.equals(b) -> a.hashCode() != b.hashCode();
        Map<Dog, String> dogs = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            dogs.put(new Dog(random.nextInt() + "", random.nextInt()), random.nextInt() + "");
        }

        Dog r = new Dog("Rex", 10);
        dogs.put(r, " <-");

        Dog rr = new Dog("Rex", 10);

        print.accept(r.equals(rr) + "");

        print.accept(dogs.getOrDefault(rr, "rr such element does not exist in map ") + rr.toString());
        print.accept(r.toString() + " value: " + dogs.get(r));
    }
}
