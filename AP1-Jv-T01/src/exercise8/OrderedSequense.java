
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OrderedSequense {

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int count = 0;
        int err = 0;
        int prev = Integer.MIN_VALUE;
        int tec = 0;
        boolean flag = true;
        boolean yes = false;
        boolean enter = false;
        try {
            int charCode;
            while ((charCode = reader.read()) != - 1) {
                int num = (int) charCode;
                if ((num >= 48 && num <= 57) || num == 32 || num == 10) {
                    if (num != 32 && num != 10) {
                        tec = tec * 10 + num - 48;
                    } else {
                        if (prev > tec) {
                            if (flag) {
                                err = count;
                                flag = false;
                            }
                        }
                        count++;
                        prev = tec;
                        tec = 0;
                    }
                    if (num == 10) {
                        enter = true;
                        break;
                    }
                } else {
                    yes = true;
                    break;
                }
            }
            reader.close();
        } catch (IOException e) {
        }
        if (err > 0) {
            System.out.println("The sequence is not ordered from the ordinal number of the number " + err);
        }
        if (count == 0 || count == 1 && enter) {
            System.out.println("Input error");
        }
        if (count > 0 && err == 0 && yes) {
            System.out.println("The sequence is ordered in ascending order");
        }
    }
}
