
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StringFiltering {

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<String> lines = new ArrayList<String>();
        String filter = null;
        try {
            Integer t = Integer.parseInt(reader.readLine());
            for (int i = 0; i < t; i++) {
                lines.add(reader.readLine());
            }
            filter = reader.readLine();
            reader.close();
        } catch (IOException e) {
        }

        boolean isFirst = true;
        for (String line : lines) {
            if (isUnderLine(filter, line)) {
                if (isFirst) {
                    System.out.print(line);
                    isFirst = false;
                } else {
                    System.out.print(", " + line);
                }
            }
        }
    }

    public static boolean isUnderLine(String underLine, String line) {
        int i = 0;
        boolean flag = false;
        if (underLine == null || line == null) {
            return false;
        }
        if (underLine.length() > line.length()) {
            return false;
        }
        for (char c : line.toCharArray()) {
            if (c == underLine.charAt(i)) {
                flag = true;
                i++;
                if (i == underLine.length()) {
                    break;
                }
            } else {
                flag = false;
                i = 0;
            }
        }
        return flag;
    }
}
