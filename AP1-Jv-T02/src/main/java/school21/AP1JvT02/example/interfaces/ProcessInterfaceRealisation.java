package school21.AP1JvT02.example.interfaces;

public class ProcessInterfaceRealisation implements ProcessInterface {

    @Override
    public void check(String s) {
        if (s.length() > 10) {
            System.out.println("String is too long");
        } else {
            System.out.println("String is fine");
        }
    }

    @Override
    public String upper(String s) {
        return s.toUpperCase();
    }

    @Override
    public String replase(String s) {
        return s.replace('A', 'a');
    }
}
