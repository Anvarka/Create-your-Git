package ru.itmo.mit.git.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static ru.itmo.mit.git.GitConstants.*;
import static ru.itmo.mit.git.GitConstants.BLOB;

public class UtilsFile {
    public static String[] getInfoFromHead() throws FileNotFoundException {
        File headFile = new File(PATH_TO_HEAD);
        Scanner readFromHEAD = new Scanner(headFile);
        return readFromHEAD.nextLine().split(" ");
    }

    public static String getCommitHashFromBranch(String branchName) throws FileNotFoundException {
        File branchFile = new File(PATH_TO_BRANCHES + branchName);
        Scanner readFromBranch = new Scanner(branchFile);
        return readFromBranch.nextLine();
    }

    public static boolean isBranch(String branchName) {
        File branchesDir = new File(PATH_TO_BRANCHES);
        for (File branchFile : Objects.requireNonNull(branchesDir.listFiles())) {
            if (branchFile.getName().equals(branchName)) {
                return true;
            }
        }
        return false;
    }

    public static String getCurrentCommitHash() throws FileNotFoundException {
        String[] branchOrCommit = getInfoFromHead();
        String commitHash;
        if (branchOrCommit[0].equals("branch")) {
            commitHash = getCommitHashFromBranch(branchOrCommit[1]);
        } else {
            commitHash = branchOrCommit[1];
        }
        return commitHash;
    }

    public static void writeToBranch(String hashCommit) throws IOException {
        String[] branchName = getInfoFromHead();
        if (branchName[0].equals("branch")) {
            FileWriter branchFile = new FileWriter(PATH_TO_BRANCHES + branchName[1]);
            branchFile.write(hashCommit);
            branchFile.close();
        } else {
            throw new IOException("HEAD is detached");
        }
    }

    public static void writeToHEAD(String str) throws IOException {
        File headFile = new File(PATH_TO_HEAD);
        FileWriter writer = new FileWriter(headFile);
        writer.write(str);
        writer.close();
    }

    public static void writeToObjects(File curDir, File f, boolean isFile) throws IOException {

        String hashFile;
        String type;
        if (isFile) {
            hashFile = GetHash.getHash(f);
            type = BLOB;
        } else {
            hashFile = GetHash.getHashDir(f, false);
            type = TREE;
        }

        FileWriter writer = new FileWriter(PATH_TO_OBJECTS
                + GetHash.getHashDir(curDir, false), true);

        writer.write(type + hashFile + " " + f.getName() + "\n");
        writer.close();
    }

}
