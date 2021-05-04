package ru.itmo.mit.git;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tree {
    /**
     * trees + blobs
     */
    public String dirName;
    public Map<String, Tree> childs = new HashMap<>();

    Tree(Tree otherRoot){
        childs = otherRoot.childs;
        dirName = otherRoot.dirName;
    }
    Tree(String dirName1){
        dirName = dirName1;
    }

    public void addChild(Tree file) {
        childs.put(file.dirName, file);
    }
}
