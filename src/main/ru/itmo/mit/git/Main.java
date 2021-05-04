package ru.itmo.mit.git;

import org.apache.commons.cli.*;
import org.apache.commons.codec.cli.Digest;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static ru.itmo.mit.git.GitConstants.*;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {

        Options options = new Options();
        options.addOption(INIT, "git init", false, "git init");
        options.addOption(ADD, "git add", true, "git add");
        options.addOption(RM, "rm", true, "rm <file>");
        options.addOption(STATUS, "git status", false, "git status");
        options.addOption(COMMIT, "git commit", true, "git commit <file>");
        options.addOption(RESET, "git reset", true, "git reset <to_revision>");
        options.addOption(LOG, "git log", true, "git log [from_revision]");
        options.addOption(CHECKOUT, "git checkout", true, "git checkout <revision>");
        options.addOption(CHECKOUT, "checkout --", true, "git checkout -- <files>");

        CommandLineParser cmdLineParser = new DefaultParser();// создаем парсер
        CommandLine commandLine = cmdLineParser.parse(options, args);// парсим командную строку

        if (commandLine.hasOption(INIT)) {
            Commands.createInit();
        } else if (commandLine.hasOption(ADD)) {
            String[] arguments = commandLine.getOptionValues(ADD);
            Commands.add(arguments);
        } else if (commandLine.hasOption(RM)) {
            String[] arguments = commandLine.getOptionValues(RM);
            Commands.rm(arguments);
        } else if (commandLine.hasOption(STATUS)) {
            Commands.getStatus();
        } else if (commandLine.hasOption(COMMIT)) {
            String[] arguments = commandLine.getOptionValues(COMMIT);
            Commands.createCommit(arguments);
        } else if (commandLine.hasOption(RESET)) {
            String[] arguments = commandLine.getOptionValues(RESET);
            Commands.add(arguments);
        } else if (commandLine.hasOption(LOG)) {
            String[] arguments = commandLine.getOptionValues(LOG);
            Commands.add(arguments);
        } else if (commandLine.hasOption(CHECKOUT)) {
            String[] arguments = commandLine.getOptionValues(CHECKOUT);
            Commands.add(arguments);
        }
    }
}
