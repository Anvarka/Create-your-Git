package ru.itmo.mit.git.command;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static ru.itmo.mit.git.GitConstants.PATH_TO_BRANCHES;
import static ru.itmo.mit.git.utils.UtilsFile.*;

public class BranchCreate {
    public static boolean branchCreate(String newBranch) throws IOException {
        File newBranchFile = new File(PATH_TO_BRANCHES + newBranch);
        boolean isSuccessful = newBranchFile.createNewFile();
        String commitHash = getCurrentCommitHash();
        FileWriter f = new FileWriter(newBranchFile);
        f.write(commitHash);
        f.close();
        return isSuccessful;
    }
}
