package ru.itmo.mit.git.command;

import org.jetbrains.annotations.NotNull;
import ru.itmo.mit.git.utils.GetHash;

import java.io.*;
import java.util.List;

import static ru.itmo.mit.git.GitConstants.*;
import static ru.itmo.mit.git.command.Status.*;
import static ru.itmo.mit.git.utils.UpdateStatus.updateStatus;

public class Add {
    public static void add(@NotNull List<@NotNull String> files) throws IOException {
        for (String pathToFile : files) {
            File fileForAdd = new File(PATH_ROOT + pathToFile);
            workingTree.put(fileForAdd.getName(), GetHash.getHash(fileForAdd));
            indexFiles.put(fileForAdd.getName(), GetHash.getHash(fileForAdd));
            updateStatus();
        }
    }
}
