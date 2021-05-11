package ru.itmo.mit.git.utils;

import org.apache.commons.io.FileUtils;
import ru.itmo.mit.git.command.Status;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static ru.itmo.mit.git.GitConstants.*;
import static ru.itmo.mit.git.GitConstants.PATH_TO_OBJECTS;
import static ru.itmo.mit.git.command.Status.isIndexFile;
import static ru.itmo.mit.git.utils.UtilsFile.writeToObjects;

public class UpdateStatus {
    public static void updateStatus() throws IOException {
        Status.workingTree.clear();
        File rootFile = new File(PATH_ROOT);
        String hashRoot = GetHash.getHashDir(rootFile, false);
        FileWriter writer = new FileWriter(PATH_INDEX);
        writer.write(hashRoot);
        writer.close();

        updateStatusAllFiles(rootFile);
    }

    private static void updateStatusAllFiles(File curDir) throws IOException {
        File[] filesList = curDir.listFiles();
        if (filesList != null) {
            clearFile(GetHash.getHashDir(curDir, false));

            for (File f : filesList) {
                if (f.isDirectory()) {

                    updateStatusAllFiles(f);
                    writeToObjects(curDir, f, false);

                } else if (f.isFile()) {
                    Status.workingTree.put(f.getName(), GetHash.getHash(f));
                    if (isIndexFile(f)) {
                        writeToObjects(curDir, f, true);

                        String hashFile = GetHash.getHash(f);
                        File copiedFile = new File(PATH_TO_BLOBS + hashFile);
                        FileUtils.copyFile(f, copiedFile);
                    }
                }
            }
        }
    }
    private static void clearFile(String hash) throws IOException {
        FileWriter write1 = new FileWriter(PATH_TO_OBJECTS + hash);
        write1.write("");
        write1.close();
    }
}
