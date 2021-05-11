package ru.itmo.mit.git.command;

import java.io.File;
import java.io.PrintStream;
import java.util.Objects;

import static ru.itmo.mit.git.GitConstants.PATH_TO_BRANCHES;

public class ShowBranches {
    public static void showBranches(PrintStream output){
        output.println("Available branches:");
        File branchDir = new File(PATH_TO_BRANCHES);
        for(File branchFile: Objects.requireNonNull(branchDir.listFiles())){
            output.println(branchFile.getName());
        }
    }
}
