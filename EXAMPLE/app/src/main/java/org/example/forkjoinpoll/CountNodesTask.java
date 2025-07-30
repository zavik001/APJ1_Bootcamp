package org.example.forkjoinpoll;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class CountNodesTask extends RecursiveTask<Integer> {

    private final TreeNode node;

    public CountNodesTask(TreeNode node) {
        this.node = node;
    }

    @Override
    protected Integer compute() {
        if (node.children.isEmpty()) {
            return 1;
        }

        List<CountNodesTask> subTasks = new ArrayList<>();
        for (TreeNode child : node.children) {
            CountNodesTask task = new CountNodesTask(child);
            task.fork();
            subTasks.add(task);
        }

        int count = 1;
        for (CountNodesTask task : subTasks) {
            count += task.join();
        }

        return count;
    }
}
