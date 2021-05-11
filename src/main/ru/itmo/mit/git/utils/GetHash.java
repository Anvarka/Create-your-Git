package ru.itmo.mit.git.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import static ru.itmo.mit.git.command.Status.isIndexFile;

public class GetHash {
    public static String getHash(File file) throws IOException {
        return DigestUtils.md5Hex(new FileInputStream(file));
    }

    public static String getHashDir(File file, boolean withoutIndex) throws IOException {
        Vector<FileInputStream> fileStreams = new Vector<>();
        getAllFiles(file, fileStreams, withoutIndex);
        try (SequenceInputStream sequenceInputStream = new SequenceInputStream(fileStreams.elements())) {
            return DigestUtils.md5Hex(sequenceInputStream);
        }
    }

    private static void getAllFiles(File directory, List<FileInputStream> fileInputStreams, boolean withoutIndex) throws IOException {
        File[] files = directory.listFiles();

        if (files != null) {
            Arrays.sort(files, Comparator.comparing(File::getName));
            for (File file : files) {
                if (file.isDirectory()) {
                    getAllFiles(file, fileInputStreams, withoutIndex);
                } else {
                    if (isIndexFile(file) || withoutIndex) {
                        fileInputStreams.add(new FileInputStream(file));
                    }
                }
            }
        }
    }
}
