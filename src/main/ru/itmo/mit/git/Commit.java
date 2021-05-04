package ru.itmo.mit.git;

import org.apache.commons.codec.cli.Digest;

import java.sql.Timestamp;
import java.util.List;

public class Commit {
    Commit parent = null;
    Tree root;
    String author;
    Timestamp date;
    String[] message;
    Commit(Commit parent1, Tree root1, String author1, Timestamp date1, String[] message1) {
        parent = parent1;
        root = root1;
        author = author1;
        date = date1;
        message = message1;
    }
}
