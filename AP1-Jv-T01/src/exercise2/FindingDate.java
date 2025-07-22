
import java.util.Scanner;

public class FindingDate {

    public static void main(String[] args) {
        outputDate(findDate(inputSeconds()));
    }

    public static int inputSeconds() {
        int sec = 0;
        Scanner in = new Scanner(System.in);
        while (true) {
            try {
                sec = Integer.parseInt(in.nextLine());
                break;
            } catch (NumberFormatException e) {
                outputDate("Could not parse a number. Please, try again");
            }
        }
        return sec;
    }

    public static String findDate(int sec) {
        if (sec < 0) {
            return "Incorrect time";
        }
        return String.format("%02d:%02d:%02d", sec / 3600, sec / 60 % 60, sec % 60);
    }

    public static void outputDate(String date) {
        System.out.println(date);
    }
}
