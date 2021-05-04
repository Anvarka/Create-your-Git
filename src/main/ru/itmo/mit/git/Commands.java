package ru.itmo.mit.git;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;

import static ru.itmo.mit.git.GitConstants.*;
import static ru.itmo.mit.git.StatusFiles.*;

public class Commands {
    private static Tree rootFile = null;
    private static Commit parentCommit = null;

    public static void createInit() {
        File curDir = new File(System.getProperty("user.dir"));
        initial(curDir);
        createNode(null, curDir);
        System.out.println("Project initialized");
    }

    private static void createNode(Tree rootF, File file) {
        if (file.isDirectory()) {
            Tree node = new Tree(file.getName());
            if (rootF != null) {
                rootF.addChild(node);
            }
            File[] childrens = file.listFiles();
            assert childrens != null;
            for (File child : childrens) {
                createNode(node, child);
            }
        } else if (file.isFile()) {
            Tree node = new Blob(file.getName());
            rootF.addChild(node);
        }
    }

    private static Tree liftTree(String path) {
        Tree curNode = rootFile;
        String[] partPath = path.split("/");
        for (String dirFile : partPath) {
            File dirOrFile = new File(dirFile);
            if (curNode.childs.containsKey(dirOrFile.getName())) {
                if (!dirOrFile.isFile()) {
                    curNode = curNode.childs.get(dirOrFile.getName());
                }
            } else {
                return null;
            }
        }
        return curNode;
    }

    public static void add(String[] filenames) {
        for (String filename : filenames) {
            if (isModified(new File(filename))) {
                Tree curNode = liftTree(filename);
                if (curNode != null) {
                    curNode.childs.put(filename, new Blob(filename));
                }
                indexTree.put(filename, StatusFiles.getHash(new File(filename)));
            } else if (isUntracked(new File(filename))){
                indexTree.put(filename, StatusFiles.getHash(new File(filename)));
            }
        }
    }

    public static void rm(String[] filenames) {
        for (String filename : filenames) {
            Tree curNode = liftTree(filename);
            if (curNode != null) {
                curNode.childs.remove(filename);
            }
            workingTree.remove(filename);
        }
    }

    public static void createCommit(String[] message) {
        String author = System.getProperty("user.name");
        Timestamp date = new Timestamp(System.currentTimeMillis());
        Commit commit = new Commit(parentCommit, rootFile, author, date, message);
        rootFile = new Tree(rootFile);
        parentCommit = commit;
    }

    public static String getStatus() {
        for(Map.Entry<String,Tree> node: rootFile.childs.entrySet()){
            if (isModified(new File(node.getKey()))){
                return MODIFIED;
            }
            if (isUntracked(new File(node.getKey()))){
                return UNTRACKED;
            }
            if (isNewFile(new File(node.getKey()))){
                return NEW;
            }
            if (isRemoved(new File(node.getKey()))){
                return REMOVED;
            }
            if (isModifiedInCommit(new File(node.getKey()))){
                return MODIFIED;
            }
            if (isReadyToRemove(new File(node.getKey()))){
                return READYTOREMOVE;
            }
            if (isEverythingUpdate()){
                return GOOD;
            }
        }
        return "";
    }
}
