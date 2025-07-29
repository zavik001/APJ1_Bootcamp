package org.example.thread;

public class MyThread extends Thread {

    @Override
    public void run() {
        System.out.println("Поток: " + getName() + " start");
    }
}
