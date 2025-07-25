package school21.AP1JvT02;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import school21.AP1JvT02.exercise0.Animal;
import school21.AP1JvT02.exercise0.AnimalFactory;
import school21.AP1JvT02.exercise0.Cat;
import school21.AP1JvT02.exercise0.Dog;

public class Main {

    static final String ERROR_INPUT = "Incorrect input. Unsupported pet type";
    static final String ERROR = "Incorrect input";
    static final String ERROR_AGE = "Incorrect input. Age <= 0";
    static final String EROR_PARSE = "Could not parse a number. Please, try again";

    public static void main(String[] args) {
        Animal animal = new Dog("Rex", 10);
        List<Animal> animals = new LinkedList<>();
        animals.add(animal);
        animals.add(new Dog("rex", 12));

        List<? extends Animal> animalsOnlyRead = new LinkedList<>(animals);
        List<? super Animal> animalsOnlyWrite = new LinkedList<>(animals);
        animalsOnlyWrite.add(new Cat("kitty", 10));

        System.out.println(animalsOnlyRead.toString());
        System.out.println(animalsOnlyWrite.toString());

        Scanner in = new Scanner(System.in);
        start(in);
        in.close();
    }

    static void start(Scanner in) {
        Boolean flag = true;
        while (flag) {
            selectTask();
            Integer t = inputIntNumber(in);
            switch (t) {
                case 1 ->
                    exercise0(in);
                case 3 ->
                    flag = false;
            }
        }
    }

    static void selectTask() {
        System.out.print("""
                1. exercise0
                2.
                3. quit
                """);
    }

    static Integer inputIntNumber(Scanner in) {
        while (true) {
            try {
                return Integer.valueOf(in.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(EROR_PARSE);
            }
        }
    }

    static void exercise0(Scanner in) {
        List<Animal> pets = new LinkedList<>();
        Integer t = inputIntNumber(in);
        if (t <= 0) {
            System.out.println(ERROR);
            return;
        }
        for (int i = 0; i < t; i++) {
            String animal = in.nextLine();
            if (!"dog".equals(animal) && !"cat".equals(animal)) {
                System.out.println(ERROR_INPUT);
                continue;
            }
            String name = in.nextLine();
            Integer age = inputIntNumber(in);
            if (age <= 0) {
                System.out.println(ERROR_AGE);
                continue;
            }
            pets.add(AnimalFactory.creatAnimal(animal, name, age));
        }
        for (Animal animal : pets) {
            System.out.println(animal.toString());
        }
    }
}
