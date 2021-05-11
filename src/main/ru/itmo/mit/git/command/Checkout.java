package ru.itmo.mit.git.command;

import ru.itmo.mit.git.utils.UpdateFilesFromCommit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static ru.itmo.mit.git.utils.UtilsFile.*;

public class Checkout {
    public static void checkout(List<String> arguments) throws IOException {

        if (isBranch(arguments.get(0))) {
            writeToHEAD("branch " + arguments.get(0));

            String commitHash = getCommitHashFromBranch(arguments.get(0));

            UpdateFilesFromCommit updater = new UpdateFilesFromCommit(commitHash);
            updater.updateWorkingTree();

        } else if (arguments.get(0).equals("--")) {
            for (int i = 1; i < arguments.size(); i++) {
                String filePath = arguments.get(i);
                UpdateFilesFromCommit.updateOneFile(filePath);
            }
        } else {
            String commitHash = arguments.get(0);
            writeToHEAD("detached " + commitHash);

            UpdateFilesFromCommit updater = new UpdateFilesFromCommit(commitHash);
            updater.updateWorkingTree();
        }
    }
}
