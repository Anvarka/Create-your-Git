package ru.itmo.mit.git;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static ru.itmo.mit.git.GitConstants.*;


public class Blob extends Tree {
    public String type = UNTRACKED;
    private String content;

    Blob(String name) {
        super(name);
        try (InputStream in = new FileInputStream(name)) {
            String content = IOUtils.toString(in, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getContent() {
        return content;
    }
}
