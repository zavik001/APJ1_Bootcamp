package org.example.executor;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;

public class SerialExecutor implements Executor {

    final Queue<Runnable> tasks = new ArrayDeque<>();
    final Executor executor;
    Runnable active;

    public SerialExecutor(Executor executor) {
        this.executor = executor;
    }

    public synchronized void execute(Runnable r) {
        tasks.add(() -> {
            try {
                r.run();
            } finally {
                scheduleNext(); // запускаем следующую
            }
        });
        if (active == null) {
            scheduleNext(); // если ничего не выполняется — запускаем
        }
    }

    protected synchronized void scheduleNext() {
        if ((active = tasks.poll()) != null) {
            executor.execute(active);
        }
    }
}
