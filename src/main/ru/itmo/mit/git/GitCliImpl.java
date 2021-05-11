package ru.itmo.mit.git;

import org.jetbrains.annotations.NotNull;
import ru.itmo.mit.git.command.*;
import ru.itmo.mit.git.utils.GetInfoFromCommit;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import static ru.itmo.mit.git.GitConstants.*;
import static ru.itmo.mit.git.GitConstants.CHECKOUT;
import static ru.itmo.mit.git.utils.UtilsFile.getCommitHashFromBranch;
import static ru.itmo.mit.git.utils.UtilsFile.getInfoFromHead;

public class GitCliImpl implements GitCli {
    private PrintStream output;

    @Override
    public void runCommand(@NotNull String command, @NotNull List<@NotNull String> arguments) throws GitException {
        switch (command) {
            case INIT: {
                try {
                    Init.gitInit();
                    output.println("Project initialized");
                } catch (IOException e) {
                    throw new GitException(e.getCause());
                }
                break;
            }
            case ADD: {
                try {
                    Add.add(arguments);
                    output.println("Add completed successful");
                } catch (IOException e) {
                    throw new GitException(e.getCause());
                }
                break;
            }
            case RM: {
                try {
                    Remove.rm(arguments);
                    output.println("Rm completed successful");
                } catch (IOException e) {
                    throw new GitException(e.getCause());
                }
                break;
            }
            case STATUS: {
                try {
                    Status.getStatus(output);
                } catch (IOException e) {
                    throw new GitException(e.getCause());
                }
                break;
            }
            case COMMIT: {
                try {
                    Commit.createCommit(arguments.get(0));
                    output.println("Files committed");
                } catch (IOException e) {
                    throw new GitException(e.getCause());
                }
                break;
            }
            case RESET: {
                try {
                    Reset.reset(arguments);
                    output.println("Reset successful");
                } catch (IOException e) {
                    throw new GitException(e.getCause());
                }
                break;
            }
            case LOG: {
                try {
                    Log.getLog(output);
                } catch (IOException e) {
                    throw new GitException(e.getCause());
                }
                break;
            }
            case CHECKOUT: {
                try {
                    Checkout.checkout(arguments);
                    output.println("Checkout completed successful");
                } catch (IOException e) {
                    throw new GitException(e.getCause());
                }
                break;
            }
            case SHOW_BRANCHES: {
                ShowBranches.showBranches(output);
                break;
            }
            case BRANCH_REMOVE: {
                if (BranchRemove.branchRemove(arguments.get(0), output)) {
                    output.println("Branch " + arguments.get(0) + " removed successfully");
                }
                break;
            }
            case BRANCH_CREATE:{
                try {
                    if(BranchCreate.branchCreate(arguments.get(0))){
                        output.println("Branch " + arguments.get(0) + " created successfully");
                        output.println("You can checkout it with 'checkout " + arguments.get(0) + "'");
                    }                }
                catch (IOException e){
                    throw new GitException(e.getCause());
                }
                break;
            }
        }
    }

    @Override
    public void setOutputStream(@NotNull PrintStream outputStream) {
        output = outputStream;
    }

    @Override
    public @NotNull String getRelativeRevisionFromHead(int n) throws GitException {
        String commitName;
        try {
            String[] BranchOrCommitName = getInfoFromHead();
            if (BranchOrCommitName[0].equals("branch")) {
                String branchName = BranchOrCommitName[1];
                commitName = getCommitHashFromBranch(branchName);
            } else {
                commitName = BranchOrCommitName[1];
            }
            while (n != 0) {
                try {
                    GetInfoFromCommit commitInfo = new GetInfoFromCommit(commitName);
                    commitName = commitInfo.parent;
                    n--;
                } catch (FileNotFoundException e) {
                    throw new IOException(e.getCause());
                }
            }
        } catch (IOException e) {
            throw new GitException(e.getCause());
        }
        return commitName;
    }
}
