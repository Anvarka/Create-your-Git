package ru.itmo.mit.git.command;

import org.apache.commons.io.FileUtils;
import ru.itmo.mit.git.utils.*;
import java.io.*;
import java.nio.file.Paths;

import static ru.itmo.mit.git.GitConstants.*;

public class Init {
    public static void gitInit() throws IOException {
        FileUtils.deleteDirectory(new File(".myGit/"));

        createInitDir();

        File masterBranch = new File(PATH_TO_BRANCHES + "master");
        masterBranch.createNewFile();

        File curDir = new File(Paths.get(PATH_ROOT).toAbsolutePath().toString());
        String hashCurDir =  GetHash.getHashDir(curDir, true);
        File file = new File(PATH_TO_OBJECTS + hashCurDir);
        file.createNewFile();

        FileWriter headFile = new FileWriter(PATH_TO_HEAD);
        headFile.write("branch master");
        headFile.close();

        FileWriter indexFile = new FileWriter(PATH_INDEX);
        indexFile.write(hashCurDir);
        indexFile.close();

        addAllFiles(curDir);

        Commit.createCommit("Initial commit");
    }

    private static void createInitDir() {
        File dir = new File(".myGit/");
        dir.mkdirs();
        File dir2 = new File(PATH_TO_OBJECTS);
        dir2.mkdirs();
        File dir3 = new File(PATH_TO_COMMITS);
        dir3.mkdirs();
        File dir4 = new File(PATH_TO_BRANCHES);
        dir4.mkdirs();
        File dir5 = new File(PATH_TO_BLOBS);
        dir5.mkdirs();

    }

    private static void addAllFiles(File curDir) throws IOException {
        File[] filesList = curDir.listFiles();

        for (File f : filesList) {
            if (f.isDirectory()) {
                addAllFiles(f);
                FileWriter writer = new FileWriter(PATH_TO_OBJECTS + GetHash.getHashDir(curDir, true), true);
                BufferedWriter bufferWriter = new BufferedWriter(writer);
                bufferWriter.write(TREE +  GetHash.getHashDir(f, true) + " " + f.getName() + "\n");
                bufferWriter.close();
                writer.close();
            }
            if (f.isFile()) {
                String hashFile = GetHash.getHash(f);
                Status.workingTree.put(f.getName(), hashFile);
                Status.indexFiles.put(f.getName(), hashFile);
                FileWriter writer = new FileWriter(PATH_TO_OBJECTS + GetHash.getHashDir(curDir, true), true);
                BufferedWriter bufferWriter = new BufferedWriter(writer);
                bufferWriter.write(BLOB + hashFile + " " + f.getName() + "\n");
                bufferWriter.close();
                writer.close();

                File copiedFile = new File(PATH_TO_BLOBS + hashFile);
                FileUtils.copyFile(f, copiedFile);
            }
        }
    }
}
