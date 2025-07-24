
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FindMaxMin {

    static final String PATH = "result.txt";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        try {
            Scanner sc = new Scanner(new FileInputStream(inputString(in)));
            in.close();
            int t = sc.nextInt();
            if (t <= 0) {
                outputString("Input error. Size <= 0", true);
            } else {
                double[] arr = new double[t];
                int i = 0;
                while (sc.hasNext()) {
                    if (sc.hasNextDouble()) {
                        if (i < t) {
                            arr[i] = sc.nextDouble();
                            // outputString(arr[i] + "", true);
                            i++;
                        } else {
                            break;
                        }
                    } else {
                        sc.next();
                    }
                }
                sc.close();
                // outputString(i + "", true);
                if (i != t) {
                    outputString("Input error. Insufficient number of elements", true);
                } else {
                    outputString(t + "", true);
                    double min = Double.MAX_VALUE;
                    double max = Double.MIN_VALUE;
                    for (int j = 0; j < t; j++) {
                        if (j == t - 1) {
                            outputString(arr[j] + "", true);
                        } else {
                            outputString(arr[j] + " ", false);
                        }
                        if (arr[j] < min) {
                            min = arr[j];
                        }
                        if (arr[j] > max) {
                            max = arr[j];
                        }
                    }
                    outputString("Saving min and max values in file", true);
                    FileWriter writer = new FileWriter(PATH);
                    writer.write(min + " " + max);
                    writer.close();
                }
            }
        } catch (IOException e) {
            outputString("Input error. File doesn't exist", true);
        }
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

    public static String inputString(Scanner in) {
        return in.nextLine();
    }

    public static void outputString(String s, boolean newline) {
        if (newline) {
            System.out.println(s);
        } else {
            System.out.print(s);
        }
    }
}
