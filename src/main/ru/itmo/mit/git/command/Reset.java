package ru.itmo.mit.git.command;

import ru.itmo.mit.git.utils.UpdateFilesFromCommit;
import ru.itmo.mit.git.utils.GetInfoFromCommit;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static ru.itmo.mit.git.GitConstants.PATH_TO_BRANCHES;
import static ru.itmo.mit.git.GitConstants.PATH_TO_HEAD;
import static ru.itmo.mit.git.utils.UtilsFile.getInfoFromHead;

public class Reset {
    public static void reset(List<String> arguments) throws IOException {
        String commitHash = arguments.get(0);
        String[] branchOrCommit = getInfoFromHead();

        if (branchOrCommit[0].equals("branch")){
            FileWriter writerToBranch = new FileWriter(PATH_TO_BRANCHES + branchOrCommit[1]);
            writerToBranch.write(commitHash);
            writerToBranch.close();
        }
        else{
            FileWriter writerToHead = new FileWriter(PATH_TO_HEAD);
            writerToHead.write("detached " + commitHash);
            writerToHead.close();
        }
        UpdateFilesFromCommit committer = new UpdateFilesFromCommit(commitHash);
        committer.updateWorkingTree();
        Status.commitFiles = committer.getCommitFiles();
    }
}
