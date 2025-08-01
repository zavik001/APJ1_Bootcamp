package org.example.splIterator;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.Deque;
import java.util.ArrayDeque;

public class TreeSpliterator implements Spliterator<TreeNodeCat> {
    TreeNodeCat root;
    private final Deque<TreeNodeCat> stack = new ArrayDeque<>();

    public TreeSpliterator(TreeNodeCat root) {
        this.root = root;
        if (root != null) {
            stack.push(root);
        }
    }

    @Override
    public boolean tryAdvance(Consumer<? super TreeNodeCat> action) {
        if (stack.isEmpty())
            return false;
        TreeNodeCat current = stack.pop();
        if (current.left != null) {
            stack.add(current.left);
        }
        if (current.right != null) {
            stack.add(current.right);
        }
        action.accept(current);
        return true;
    }

    @Override
    public Spliterator<TreeNodeCat> trySplit() {
        if (root == null || (root.left == null && root.right == null)) {
            return null;
        }
        if (root.left != null) {
            TreeNodeCat leftRoot = root.left;
            root.left = null;
            return new TreeSpliterator(leftRoot);
        }
        if (root.right != null) {
            TreeNodeCat rightRoot = root.right;
            root.right = null;
            return new TreeSpliterator(rightRoot);
        }
        return null;
    }

    @Override
    public long estimateSize() {
        return Long.MAX_VALUE;
    }

    @Override
    public int characteristics() {
        return Spliterator.SIZED | Spliterator.SUBSIZED | Spliterator.ORDERED;
    }
}
