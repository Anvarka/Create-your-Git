package ru.itmo.mit.git.command;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static ru.itmo.mit.git.GitConstants.PATH_TO_BRANCHES;

public class BranchRemove {
    public static boolean branchRemove(String branchForRemove, PrintStream output){
        File branchFile = new File(PATH_TO_BRANCHES + branchForRemove);
        return branchFile.delete();
    }
}
