package exercise10;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FindingNames {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Integer t = inputIntNumber(in);
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < t; i++) {
            String name = in.nextLine();
            Integer age = inputIntNumber(in);
            if (age <= 0) {
                System.out.println("Incorrect input. Age <= 0");
                t++;
                continue;
            }
            users.add(new User(name, age));
        }
        List<User> adults = users.stream()
                .filter(u -> u.age >= 18)
                .collect(Collectors.toList());
        StringBuilder s = new StringBuilder(adults.toString());
        s.deleteCharAt(0);
        s.deleteCharAt(s.length() - 1);
        if (s.length() > 0) {
            System.out.println(s);
        }
    }

    public static Integer inputIntNumber(Scanner in) {
        Integer num = 0;
        while (true) {
            try {
                num = Integer.parseInt(in.nextLine());
                break;
            } catch (NumberFormatException e) {
                outputString("Could not parse a number. Please, try again", true);
            }
        }
        return num;
    }

    public static void outputString(String s, Boolean newline) {
        if (newline) {
            System.out.println(s);
        } else {
            System.out.print(s);
        }
    }
}
