package school21.AP1JvT02.example;

import school21.AP1JvT02.example.interfaces.ProcessInterface;

public class ProcessWithInterface {

    public ProcessInterface process;

    public ProcessWithInterface(ProcessInterface process) {
        this.process = process;
    }

    public String process(String s) {
        process.check(s);
        s = process.upper(s);
        s = process.replase(s);
        return s;
    }
}
