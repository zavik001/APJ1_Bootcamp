
import java.util.Arrays;
import java.util.Scanner;

public class MatchesFirstLast {

    public static void main(String[] args) {
        int t = inputNumber();
        if (t <= 0) {
            outputString("Input error. Size <= 0", true);
        } else {
            int[] arr = new int[t];
            int[] arrLF = new int[t];
            int i = 0;
            int j = -1;
            while (t-- > 0) {
                arr[i] = inputNumber();
                if (checkFirstLast(arr[i])) {
                    j++;
                    arrLF[j] = arr[i];
                }
                i++;
            }
            if (j < 0) {
                outputString("There are no such elements", true);
            } else {
                int[] newArrLF = Arrays.copyOf(arrLF, j + 1);
                for (int k = 0; k <= j; k++) {
                    if (k == j) {
                        outputString(newArrLF[k] + "", false);
                    } else {
                        outputString(newArrLF[k] + " ", false);
                    }
                }
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
                outputString("Could not parse a number. Please, try again", true);
            }
        }
        return num;
    }

    public static void outputString(String s, boolean newline) {
        if (newline) {
            System.out.println(s);
        } else {
            System.out.print(s);
        }
    }

    public static boolean checkFirstLast(int num) {
        if (num < 10 && num > -10) {
            return true;
        }
        if (num < 0) {
            num = -num;
        }
        int last = num % 10;
        int first = num;
        while (first >= 10) {
            first /= 10;
        }
        return first == last;
    }
}
