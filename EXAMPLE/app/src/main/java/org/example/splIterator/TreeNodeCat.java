package org.example.splIterator;

import org.example.animals.Cat;

public class TreeNodeCat {
    Cat cat;
    public TreeNodeCat left;
    public TreeNodeCat right;

    public TreeNodeCat(Cat cat) {
        this.cat = cat;
    }

    public Cat getCat() {
        return cat;
    }
}
