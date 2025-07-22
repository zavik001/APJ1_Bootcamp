
import java.util.Scanner;

public class ArithmeticNegativeNumbers {

    public static void main(String[] args) {
        int t = inputNumber();
        if (t < 0) {
            outputString("Input error. Size <= 0");
        } else {
            int col = 0;
            long arif = 0L;
            for (int i = 0; i < t; i++) {
                int num = inputNumber();
                if (num < 0) {
                    col++;
                    arif += num;
                }
            }
            if (col > 0) {
                outputString(arif / col + "");
            } else {
                outputString("There are no negative elements");
            }
        }
    }

    public static int inputNumber() {
        int num = 0;
        Scanner in = new Scanner(System.in);
        while (true) {
            try {
                num = Integer.parseInt(in.nextLine());
                break;
            } catch (NumberFormatException e) {
                outputString("Could not parse a number. Please, try again");
            }
        }
        return num;
    }

    public static void outputString(String s) {
        System.out.println(s);
    }
}
