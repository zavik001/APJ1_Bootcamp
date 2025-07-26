package school21.AP1JvT02.example;

import school21.AP1JvT02.example.functional.interfaces.Check;
import school21.AP1JvT02.example.functional.interfaces.Replace;
import school21.AP1JvT02.example.functional.interfaces.Upper;

public class ProcessWithFunctionalInterface {

    Check check;
    Upper upper;
    Replace replace;

    public ProcessWithFunctionalInterface(Check check, Upper upper, Replace replace) {
        this.check = check;
        this.upper = upper;
        this.replace = replace;
    }

    public String process(String s) {
        check.check(s);
        s = upper.upper(s);
        s = replace.replace(s);
        return s;
    }
}
