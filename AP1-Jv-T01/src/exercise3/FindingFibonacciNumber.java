
import java.util.Scanner;

public class FindingFibonacciNumber {

    public static void main(String[] args) {
        int num = inputNumber();
        if (num < 0) {
            outputString("Incorrect number");
        } else {
            long f0 = 0;
            long f1 = 1;
            try {
                long fNum = findFibonacci(num, f0, f1);
                if (fNum == -1) {
                    outputString("Too large n");
                } else {
                    outputString(fNum + "");
                }
            } catch (StackOverflowError e) {
                outputString("Too large n");
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

    public static long findFibonacci(int num, long f0, long f1) {
        if (num > 2) {
            return findFibonacci(num - 1, f1, f0 + f1);
        } else {
            if (f0 > Long.MAX_VALUE - f1) {
                return -1;
            }
            return f0 + f1;
        }
    }

    public static void outputString(String s) {
        System.out.println(s);
    }
}
