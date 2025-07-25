package school21.AP1JvT02;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import school21.AP1JvT02.exercise0.AnimalEx0;
import school21.AP1JvT02.exercise0.AnimalFactoryEx0;
import school21.AP1JvT02.exercise1.AnimalEx1;
import school21.AP1JvT02.exercise1.AnimalFactoryEx1;

public class Main {

    static final String ERROR_INPUT = "Incorrect input. Unsupported pet type";
    static final String ERROR = "Incorrect input";
    static final String ERROR_AGE = "Incorrect input. Age <= 0";
    static final String EROR_PARSE = "Could not parse a number. Please, try again";
    static final String ERROR_MASS = "Incorrect input. Mass <= 0";

    public static void main(String[] args) {
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
                case 2 ->
                    exercise1(in);
                case 3 ->
                    flag = false;
            }
        }
    }

    static void selectTask() {
        System.out.print("""
                1. exercise0
                2. exercise1
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

    public static Double inputDoubleNumber(Scanner in) {
        while (true) {
            try {
                return Double.valueOf(in.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(EROR_PARSE);
            }
        }
    }

    static void exercise0(Scanner in) {
        List<AnimalEx0> pets = new LinkedList<>();
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
            pets.add(AnimalFactoryEx0.creatAnimal(animal, name, age));
        }
        for (AnimalEx0 animal : pets) {
            System.out.println(animal.toString());
        }
    }

    static void exercise1(Scanner in) {
        List<AnimalEx1> pets = new LinkedList<>();
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
            Double mass = inputDoubleNumber(in);
            if (mass <= 0) {
                System.out.println(ERROR_MASS);
                continue;
            }
            pets.add(AnimalFactoryEx1.creatAnimal(animal, name, age, mass));
        }
        for (AnimalEx1 animal : pets) {
            System.out.println(animal.toString());
        }
    }
}
