package school21.AP1JvT02;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import school21.AP1JvT02.example.Process;
import school21.AP1JvT02.example.ProcessWithFunctionalInterface;
import school21.AP1JvT02.example.ProcessWithInterface;
import school21.AP1JvT02.example.functional.interfaces.CheckRealisation;
import school21.AP1JvT02.example.functional.interfaces.Replace;
import school21.AP1JvT02.example.functional.interfaces.Upper;
import school21.AP1JvT02.example.interfaces.ProcessInterface;
import school21.AP1JvT02.example.interfaces.ProcessInterfaceRealisation;
import school21.AP1JvT02.exercise0.AnimalEx0;
import school21.AP1JvT02.exercise0.AnimalFactoryEx0;
import school21.AP1JvT02.exercise1.AnimalEx1;
import school21.AP1JvT02.exercise1.AnimalFactoryEx1;
import school21.AP1JvT02.exercise2.AnimalEx2;
import school21.AP1JvT02.exercise2.AnimalFactoryEx2;
import school21.AP1JvT02.exercise3.AnimalEx3;
import school21.AP1JvT02.exercise3.AnimalFactoryEx3;

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
                case 0 ->
                    exercise0(in);
                case 1 ->
                    exercise1(in);
                case 2 ->
                    exercise2(in);
                case 3 ->
                    exercise3(in);
                case 6 ->
                    functionExemples(in);
                case 7 ->
                    flag = false;
            }
        }
    }

    static void selectTask() {
        System.out.print("""
                0. exercise0
                1. exercise1
                2. exercise2
                3. exercise3
                6. function
                7. quit
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

    public static boolean isUnderLine(String underLine, String line) {
        int i = 0;
        boolean flag = false;
        if (underLine == null || line == null) {
            return false;
        }
        if (underLine.length() > line.length()) {
            return false;
        }
        for (char c : line.toCharArray()) {
            if (c == underLine.charAt(i)) {
                flag = true;
                i++;
                if (i == underLine.length()) {
                    break;
                }
            } else {
                flag = false;
                i = 0;
            }
        }
        return flag;
    }

    public static String trimLine(String s, Integer num) {
        return s.substring(0, num);
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

    static void exercise2(Scanner in) {
        List<AnimalEx2> pets = new LinkedList<>();
        Integer t = inputIntNumber(in);
        if (t <= 0) {
            System.out.println(ERROR);
            return;
        }
        for (int i = 0; i < t; i++) {
            String animal = in.nextLine();
            if (!"dog".equals(animal) && !"cat".equals(animal) && !"hamster".equals(animal) && !"guinea".equals(animal)) {
                System.out.println(ERROR_INPUT);
                continue;
            }
            String name = in.nextLine();
            Integer age = inputIntNumber(in);
            if (age <= 0) {
                System.out.println(ERROR_AGE);
                continue;
            }
            pets.add(AnimalFactoryEx2.creatAnimal(animal, name, age));
        }
        pets.stream()
                .filter(x -> isUnderLine("GuineaPig", trimLine(x.toString(), 9)) || isUnderLine("Hamster", trimLine(x.toString(), 7)))
                .forEach(System.out::println);
        pets.stream()
                .filter(x -> isUnderLine("Dog", trimLine(x.toString(), 3)) || isUnderLine("Cat", trimLine(x.toString(), 3)))
                .forEach(System.out::println);
    }

    static void exercise3(Scanner in) {
        List<AnimalEx3> pets = new LinkedList<>();
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
            pets.add(AnimalFactoryEx3.creatAnimal(animal, name, age));
        }
        pets.stream()
                .peek(pet -> {
                    if (pet.getAge() > 10) {
                        pet.incrementAge();
                    }
                })
                .forEach(System.out::println);
    }

    static void functionExemples(Scanner in) {
        Process process = new Process();

        // 1
        ProcessWithInterface processWithInterface = new ProcessWithInterface(new ProcessInterfaceRealisation());

        // anonim class 
        ProcessInterface anonimProcess = new ProcessInterface() {
            @Override
            public void check(String s) {
                if (s.length() > 10) {
                    System.out.println("nice");
                } else {
                    System.out.println("niceee");
                }
            }

            @Override
            public String upper(String s) {
                return s.toUpperCase();
            }

            @Override
            public String replase(String s) {
                return s.replace('O', 'D');
            }
        };

        // 2
        ProcessWithInterface processWithAnonimInterfaceClass = new ProcessWithInterface(anonimProcess);

        // 3 
        ProcessWithInterface prcessAnonimClass = new ProcessWithInterface(new ProcessInterface() {
            @Override
            public void check(String s) {
                if (s.length() > 116) {
                    System.out.println("nice");
                } else {
                    System.out.println("niceee");
                }
            }

            @Override
            public String upper(String s) {
                return s.toUpperCase();
            }

            @Override
            public String replase(String s) {
                return s.replace('C', 'R');
            }
        });

        String s = "Hellooolll";
        String s2 = "BAAAS";
        String s3 = "hgjahdfsgj";
        String s4 = "kasdfgh";
        System.out.printf("""
                process : %s
                processWithInterface : %s
                processWithanonimIn : %s
                processWithanonim: %s
                """, process.process(s), processWithInterface.process(s2), processWithAnonimInterfaceClass.process(s3), prcessAnonimClass.process(s4));

        // Funtional with anonim
        ProcessWithFunctionalInterface processWithFunctionalInterface
                = new ProcessWithFunctionalInterface(
                        new CheckRealisation(),
                        new Upper() {
                    @Override
                    public String upper(String s) {
                        return s.toLowerCase();
                    }
                },
                        new Replace() {
                    @Override
                    public String replace(String s) {
                        return s.replace('q', 'w');
                    }
                });

        // Funtional with lamda-expressions
        // Let's look at how anonymous classes can be replaced with lambda expressions
        ProcessWithFunctionalInterface processWithFunctionalInterface1
                = new ProcessWithFunctionalInterface(
                        (String input) -> {
                            System.out.println(input + "good");
                        },
                        (String input) -> {
                            return input.toUpperCase();
                        },
                        (String input) -> {
                            return input.replace('T', 'Q');
                        });
        String sss = "WEEERRRTTTWWWW";
        String ssss = "qqq";
        System.out.printf("""
                processWithFuntionalIntreface1(lamda): %s
                processWithFuntionalIntreface1(anonim classes): %s
                """, processWithFunctionalInterface1.process(sss), processWithFunctionalInterface.process(ssss));

        // reduction lambda
        ProcessWithFunctionalInterface processWithFunctionalInterface2
                = new ProcessWithFunctionalInterface(
                        input -> System.out.println(input + "good"),
                        input -> input.toUpperCase(),
                        input -> input.replace('T', 'Q')
                );
        System.out.printf("""
                processWithFuntionalIntreface1(lamda): %s
                processWithFuntionalIntreface1(anonim classes): %s
                """, processWithFunctionalInterface1.process(sss), processWithFunctionalInterface2.process(ssss));

        // reduction method reference
        ProcessWithFunctionalInterface processWithFunctionalInterface3
                = new ProcessWithFunctionalInterface(
                        input -> System.out.println(input + "good"),
                        String::toUpperCase,
                        input -> input.replace('T', 'Q')
                );
        System.out.printf("""
                processWithFuntionalIntreface1(lamda): %s
                processWithFuntionalIntreface1(anonim classes): %s
                """, processWithFunctionalInterface1.process(sss), processWithFunctionalInterface3.process(ssss));
    }
}
