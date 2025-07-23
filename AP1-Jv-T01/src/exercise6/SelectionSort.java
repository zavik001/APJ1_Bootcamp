
import java.util.Scanner;

public class SelectionSort {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = inputIntNumber(in);
        if (t <= 0) {
            outputString("Input error. Size <= 0", true);
        } else {
            double[] arr = new double[t];
            for (int i = 0; i < t; i++) {
                arr[i] = inputDoubleNumber(in);
            }
            selectionSort(arr);
            for (int i = 0; i < t; i++) {
                if (i == t - 1) {
                    outputString(arr[i] + "", false);
                } else {
                    outputString(arr[i] + " ", false);
                }
            }
        }
        in.close();
    }

    public static int inputIntNumber(Scanner in) {
        int num = 0;
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

    public static double inputDoubleNumber(Scanner in) {
        double num = 0;
        while (true) {
            try {
                num = Double.parseDouble(in.nextLine());
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

    public static void selectionSort(double[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            double min = arr[i];
            int inx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < min) {
                    inx = j;
                    min = arr[j];
                }
            }
            double temp = arr[i];
            arr[i] = arr[inx];
            arr[inx] = temp;
        }
    }
}
