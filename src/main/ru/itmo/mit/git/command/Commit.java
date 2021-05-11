package ru.itmo.mit.git.command;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Scanner;

import static ru.itmo.mit.git.GitConstants.*;
import static ru.itmo.mit.git.command.Status.commitFiles;
import static ru.itmo.mit.git.command.Status.indexFiles;
import static ru.itmo.mit.git.utils.UtilsFile.getInfoFromHead;
import static ru.itmo.mit.git.utils.UtilsFile.writeToBranch;

public class Commit {

    public static void createCommit(String message) throws IOException {

        String res = TREE + getTree() + "\n" + "parent " + getParent() + "\n" +
                "Author: " + getAuthor() + "\n" + "Date: " + getTime() + "\n" +
                "message " + message + "\n";
        String hashCommit = DigestUtils.md5Hex(res);
        FileWriter commitFile = new FileWriter(PATH_TO_COMMITS + hashCommit);
        commitFile.write(res);
        commitFile.close();

        writeToBranch(hashCommit);
        commitFiles.putAll(indexFiles);
    }


    private static String getTree() throws IOException {
        File indexFile = new File(PATH_INDEX);
        Scanner readFromIndex = new Scanner(indexFile);
        return readFromIndex.nextLine();
    }

    private static String getParent() throws IOException {
        String[] branchOrCommitName = getInfoFromHead();
        File branchFile;
        if (branchOrCommitName[0].equals("branch")){
            branchFile = new File(PATH_TO_BRANCHES + branchOrCommitName[1]);
            Scanner readerBranch = new Scanner(branchFile);
            if (readerBranch.hasNextLine()) {
                return readerBranch.nextLine();
            } else return "";
        }
        else {
            return branchOrCommitName[1];
        }
    }

    private static String getAuthor() {
        return System.getProperty("user.name");
    }

    private static String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
