package ru.itmo.mit.git;

import org.jetbrains.annotations.NotNull;

public final class GitConstants {
    private GitConstants() {}

    public static final @NotNull String INIT = "init";
    public static final @NotNull String COMMIT = "commit";
    public static final @NotNull String RESET = "reset";
    public static final @NotNull String LOG = "log";
    public static final @NotNull String CHECKOUT = "checkout";
    public static final @NotNull String STATUS = "status";
    public static final @NotNull String ADD = "add";
    public static final @NotNull String RM = "rm";
    public static final @NotNull String BRANCH_CREATE = "branch-create";
    public static final @NotNull String BRANCH_REMOVE = "branch-remove";
    public static final @NotNull String SHOW_BRANCHES = "show-branches";
    public static final @NotNull String MERGE = "merge";

    public static final @NotNull String MASTER = "master";

    public static final @NotNull String UNTRACKED_FILES = "Untracked files:";
    public static final @NotNull String NEW_FILES = "New files:";
    public static final @NotNull String MODIFIED_FILES = "Modified files:";
    public static final @NotNull String REMOVED_FILES = "Removed files:";
    public static final @NotNull String READY_TO_COMMIT = "Ready to commit:";

    public static final @NotNull String PATH_INDEX = ".myGit/index";
    public static final @NotNull String PATH_ROOT = "playground/";
    public static final @NotNull String PATH_TO_HEAD = ".myGit/HEAD/";
    public static final @NotNull String PATH_TO_OBJECTS = ".myGit/objects/";
    public static final @NotNull String PATH_TO_BLOBS = ".myGit/blobs/";
    public static final @NotNull String PATH_TO_BRANCHES = ".myGit/branches/";
    public static final @NotNull String PATH_TO_COMMITS = ".myGit/commits/";

    public static final @NotNull String TREE = "tree ";
    public static final @NotNull String BLOB = "blob ";
}
