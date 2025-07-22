
import java.util.Scanner;

public class PerimeterOfTringle {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        float[] nodes = new float[6];

        System.out.println("Enter the coordinates of the triangle vertices:");
        int t = 0;
        while (t < 6) {
            String val = in.nextLine();
            try {
                float node = Float.parseFloat(val);
                nodes[t] = node;
                t++;
            } catch (NumberFormatException e) {
                System.out.println("Could not parse a number. Please, try again");
            }
        }

        float AB = (float) Math.sqrt(Math.pow(nodes[2] - nodes[0], 2) + Math.pow(nodes[3] - nodes[1], 2));
        float AC = (float) Math.sqrt(Math.pow(nodes[4] - nodes[0], 2) + Math.pow(nodes[5] - nodes[1], 2));
        float BC = (float) Math.sqrt(Math.pow(nodes[4] - nodes[2], 2) + Math.pow(nodes[5] - nodes[3], 2));
        if (AB + AC > BC && AB + BC > AC && BC + AC > AB) {
            System.out.printf("%.3f%n", AB + AC + BC);
        } else {
            System.out.println("It's not a triangle");
        }
    }
}
