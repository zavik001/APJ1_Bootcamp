package school21.AP1JvT02.exercise4;

import java.util.concurrent.TimeUnit;

public class DogEx4 extends AnimalEx4 {

    static final Double KF = 0.5;

    public DogEx4(String name, Integer age) {
        super(name, age);
    }

    @Override
    public String toString() {
        return String.format("Dog name = %s, age = %d", this.getName(), this.getAge());
    }

    @Override
    public Double goToWalk() {
        Double time = this.getAge() * KF;
        long t = Math.round(time);
        try {
            TimeUnit.SECONDS.sleep(t);
        } catch (InterruptedException e) {
        }
        return time;
    }
}
