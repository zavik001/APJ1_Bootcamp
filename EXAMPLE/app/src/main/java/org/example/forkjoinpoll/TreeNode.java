package org.example.forkjoinpoll;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

    final List<TreeNode> children = new ArrayList<>();

    public void addChild(TreeNode child) {
        children.add(child);
    }
}
