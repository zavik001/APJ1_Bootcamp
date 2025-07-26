package school21.AP1JvT02.example;

public class Process {

    public String process(String s) {
        if (s.length() > 10) {
            System.out.println("String is too long");
        } else {
            System.out.println("String length is fine");
        }
        String upperCased = s.toUpperCase();

        return upperCased.replace('O', ')');
    }
}
