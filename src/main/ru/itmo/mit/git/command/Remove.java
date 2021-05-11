package ru.itmo.mit.git.command;

import java.io.IOException;
import java.util.List;

import static ru.itmo.mit.git.utils.UpdateStatus.updateStatus;

public class Remove {
    public static void rm(List<String> filenames) throws IOException {
        for (String filename: filenames){
            Status.indexFiles.remove(filename);
            updateStatus();
        }
    }
}
