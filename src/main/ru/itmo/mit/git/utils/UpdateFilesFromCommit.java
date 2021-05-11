package ru.itmo.mit.git.utils;

import org.apache.commons.io.FileUtils;
import ru.itmo.mit.git.command.Status;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static ru.itmo.mit.git.GitConstants.*;
import static ru.itmo.mit.git.utils.UtilsFile.getCommitHashFromBranch;
import static ru.itmo.mit.git.utils.UtilsFile.getInfoFromHead;

public class UpdateFilesFromCommit {
    private final String commitName;
    private final Map<String, String> currentCommitFiles = new HashMap<>();

    public UpdateFilesFromCommit(String commitHash) {
        commitName = commitHash;
    }

    public Map<String, String> getCommitFiles() {
        return currentCommitFiles;
    }

    public void updateWorkingTree() throws IOException {
        GetInfoFromCommit commitInfo = new GetInfoFromCommit(commitName);
        String tree = commitInfo.tree;
        File obj = new File(PATH_TO_OBJECTS + tree);
        update(obj, PATH_ROOT);
    }

    private void update(File f, String path) throws IOException {
        Scanner t = new Scanner(f);
        while (t.hasNextLine()) {
            String[] typeHashName = t.nextLine().split(" ");
            if (typeHashName[0].equals("tree")) {
                String dirHash = typeHashName[1];
                String dirName = typeHashName[2];
                update(new File(PATH_TO_OBJECTS + dirHash), path + dirName + "/");
            } else {
                String fileName = typeHashName[2];
                File copiedFile = new File(path + fileName);
                File fileFromCommit = new File(PATH_TO_BLOBS + typeHashName[1]);

                FileUtils.copyFile(fileFromCommit, copiedFile);
                currentCommitFiles.put(fileName, typeHashName[1]);
            }
        }
    }

    public static void updateOneFile(String filePath) throws IOException {
        String[] parseFilePath = filePath.split("/");
        for (int j = 0; j < parseFilePath.length; j++) {
            String[] branchOrCommit = getInfoFromHead();
            String root;
            if (branchOrCommit[0].equals("branch")) {
                String currentCommitHash = getCommitHashFromBranch(branchOrCommit[1]);
                GetInfoFromCommit infoFormCommit = new GetInfoFromCommit(currentCommitHash);
                root = infoFormCommit.tree;
            } else {
                GetInfoFromCommit infoFormCommit = new GetInfoFromCommit(branchOrCommit[1]);
                root = infoFormCommit.tree;
            }
            liftTree(root, parseFilePath, 0);
        }
    }

    private static void liftTree(String root, String[] filePath, int index) throws IOException {
        File nodeFile;
        if (index == filePath.length - 1) {
            nodeFile = new File(PATH_TO_BLOBS + root);
            String filePathFile = String.join("/", filePath);
            File copiedFile = new File(PATH_ROOT + filePathFile);
            FileUtils.copyFile(nodeFile, copiedFile);
            Status.commitFiles.put(filePath[index], GetHash.getHash(nodeFile));
            return;
        } else {
            nodeFile = new File(PATH_TO_OBJECTS + root);
        }

        Scanner nodeReader = new Scanner(nodeFile);
        while (nodeReader.hasNextLine()) {
            String line = nodeReader.nextLine();
            if (line.contains(filePath[index])) {
                String[] typeHashName = line.split(" ");
                root = typeHashName[1];
                break;
            }
        }
        if (index < filePath.length) {
            liftTree(root, filePath, index + 1);
        }
    }
}
