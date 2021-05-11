package ru.itmo.mit.git.command;

import org.apache.commons.io.FileUtils;
import ru.itmo.mit.git.utils.GetHash;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static ru.itmo.mit.git.GitConstants.*;
import static ru.itmo.mit.git.GitConstants.BLOB;
import static ru.itmo.mit.git.utils.UpdateStatus.updateStatus;
import static ru.itmo.mit.git.utils.UtilsFile.getInfoFromHead;

public class Status {

    public static Map<String, String> workingTree = new HashMap<>();
    public static Map<String, String> indexFiles = new HashMap<>();
    public static Map<String, String> commitFiles = new HashMap<>();

    public static boolean isIndexFile(File file) {
        return indexFiles.containsKey(file.getName());
    }


    public static void getStatus(PrintStream output) throws IOException {
        updateStatus();

        String[] infoFromHead = getInfoFromHead();
        if (infoFromHead[0].equals("branch")){
            output.println("Current branch is '" + infoFromHead[1] +"'");
        }
        else{
            output.println("Error while performing status: Head is detached");
            return;
        }

        if (isNotUpdate()) {
            output.println("Everything up to date");
            return;
        }
        printInOutput(output, readyFiles(),READY_TO_COMMIT);
        printInOutput(output, newFiles(),NEW_FILES);
        printInOutput(output, untrackedFiles(),UNTRACKED_FILES);
        printInOutput(output, modifiedFilesUntracked(), MODIFIED_FILES);
        printInOutput(output, removedFiles(),REMOVED_FILES);

    }

    private static void printInOutput(PrintStream output, List<String> l, String name){
        if (l.size() > 0) {
            output.println(name);
            for (String newFile : l) {
                output.println("    " + newFile);
            }
        }
    }

    private static boolean isNotUpdate() {
        return workingTree.entrySet().equals(indexFiles.entrySet())
                && workingTree.entrySet().equals(commitFiles.entrySet());
    }

    private static List<String> readyFiles() {
        Set<Map.Entry<String, String>> unionKeys = new HashSet<>(indexFiles.entrySet());
        unionKeys.removeAll(commitFiles.entrySet());
        return unionKeys.stream().map(Map.Entry::getKey).collect(Collectors.toList());
    }

    private static List<String> untrackedFiles() {
        Set<String> unionKeys = new HashSet<>(workingTree.keySet());
        return unionKeys.stream()
                .filter(it -> !indexFiles.containsKey(it))
                .filter(it -> !commitFiles.containsKey(it))
                .collect(Collectors.toList());
    }

    private static List<String> newFiles() {
        Set<String> unionKeys = new HashSet<>(workingTree.keySet());
        return unionKeys.stream()
                .filter(it -> indexFiles.containsKey(it))
                .filter(it -> !commitFiles.containsKey(it))
                .collect(Collectors.toList());
    }

    private static List<String> modifiedFilesUntracked() {
        Set<String> unionKeys = new HashSet<>(workingTree.keySet());
        return unionKeys.stream()
                .filter(it -> indexFiles.containsKey(it))
                .filter(it -> !indexFiles.get(it).equals(workingTree.get(it)))
                .collect(Collectors.toList());
    }

    private static List<String> removedFiles() {
        Set<String> unionKeys = new HashSet<>(commitFiles.keySet());
        return unionKeys.stream()
                .filter(it -> !workingTree.containsKey(it))
                .collect(Collectors.toList());
    }
}
