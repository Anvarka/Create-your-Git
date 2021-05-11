package ru.itmo.mit.git.command;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import static ru.itmo.mit.git.GitConstants.PATH_TO_BRANCHES;
import static ru.itmo.mit.git.GitConstants.PATH_TO_COMMITS;
import static ru.itmo.mit.git.utils.UtilsFile.getInfoFromHead;

public class Log {
    public static void getLog(PrintStream output) throws IOException {
        String[] branchName = getInfoFromHead();
        String parentCommit;
        if (branchName[0].equals("branch")) {
            File branchContent = new File(PATH_TO_BRANCHES + branchName[1]);
            Scanner branchFile = new Scanner(branchContent);
            parentCommit = branchFile.nextLine();
        } else {
            parentCommit = branchName[1];
        }

        while (parentCommit != null) {
            output.println("Commit " + parentCommit);
            File commitContent1 = new File(PATH_TO_COMMITS + parentCommit);
            Scanner commitReader1 = new Scanner(commitContent1);
            parentCommit = null;

            while (commitReader1.hasNextLine()) {
                String data = commitReader1.nextLine();
                if (data.contains("parent")) {
                    String[] parentAndHash = data.split(" ");
                    if (parentAndHash.length > 1) {
                        parentCommit = parentAndHash[1];
                    } else {
                        parentCommit = null;
                    }
                } else if (data.contains("Author") || data.contains("Date")) {
                    output.println(data);
                } else if (data.contains("message")) {
                    output.println();
                    output.println(data.substring(8));
                }
            }
            output.println();
        }
    }
}
