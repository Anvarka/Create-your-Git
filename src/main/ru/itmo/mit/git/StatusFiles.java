package ru.itmo.mit.git;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class StatusFiles {
    public static Map<String, String> workingTree = new HashMap<>();
    public static Map<String, String> indexTree = new HashMap<>();
    public static Map<String, String> commitTree = new HashMap<>();

    public static void initial(File file) {
        String hash = getHash(file);
        workingTree.put(file.getName(), hash);
    }

    public static String getHash(File file) {
        String md5 = "";
        try (InputStream is = new FileInputStream(file)) {
            md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return md5;
    }

    public static boolean isUntracked(File file) {
        return workingTree.containsKey(file.getName())
                && !indexTree.containsKey(file.getName())
                && !commitTree.containsKey(file.getName());
    }

    public static boolean isNewFile(File file) {
        return workingTree.containsKey(file.getName())
                && indexTree.containsKey(file.getName())
                && !commitTree.containsKey(file.getName());
    }

    public static boolean isReadyToRemove(File file) {
        return !indexTree.containsKey(file.getName())
                && commitTree.containsKey(file.getName());
    }

    public static boolean isModified(File file) {
        if (indexTree.containsKey(file.getName())
                && workingTree.containsKey(file.getName())) {
            return !indexTree.get(file.getName()).equals(workingTree.get(file.getName()));
        }
        return false;
    }

    public static boolean isModifiedInCommit(File file) {
        if (indexTree.containsKey(file.getName())
                && commitTree.containsKey(file.getName())) {
            return !indexTree.get(file.getName()).equals(commitTree.get(file.getName()));
        }
        return false;
    }

    public static boolean isRemoved(File file) {
        return !workingTree.containsKey(file.getName())
                && commitTree.containsKey(file.getName());
    }

    public static boolean isEverythingUpdate() {
        for (Map.Entry<String,String> nameHash : workingTree.entrySet()){
            if(!nameHash.getValue().equals(indexTree.get(nameHash.getKey()))){
                return false;
            }
        }
        for (Map.Entry<String,String> nameHash : workingTree.entrySet()){
            if(!nameHash.getValue().equals(commitTree.get(nameHash.getKey()))){
                return false;
            }
        }
        return true;
    }


}
