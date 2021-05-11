package ru.itmo.mit.git;

import org.apache.commons.cli.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ru.itmo.mit.git.GitConstants.*;

public class Console {
    public static void main(String[] args) throws IOException, ParseException, GitException {

        Options options = new Options();
        options.addOption(INIT, "init", false, "git init");
        options.addOption(ADD, "add", true, "git add <file>");
        options.addOption(RM, "rm", true, "git rm <file>");
        options.addOption(STATUS, "status", false, "git status");
        options.addOption(COMMIT, "commit", true, "git commit <file>");
        options.addOption(RESET, "reset", true, "git reset <to_revision>");
        options.addOption(LOG, "log", true, "git log [from_revision]");
        options.addOption(CHECKOUT, "checkout", true, "git checkout <revision>");

        CommandLineParser cmdLineParser = new DefaultParser();// создаем парсер
        CommandLine commandLine = cmdLineParser.parse(options, args);// парсим командную строку
        GitCli gitCli = new GitCliImpl();


        if (commandLine.hasOption(INIT)) {
            List<String> arguments = Collections.emptyList();
            gitCli.runCommand(INIT, arguments);
        } else if (commandLine.hasOption(ADD)) {
            List<String> arguments = Arrays.stream(commandLine.getOptionValues(ADD)).collect(Collectors.toList());
            gitCli.runCommand(ADD, arguments);
        } else if (commandLine.hasOption(RM)) {
            List<String> arguments = Arrays.stream(commandLine.getOptionValues(RM)).collect(Collectors.toList());
            gitCli.runCommand(RM, arguments);
        } else if (commandLine.hasOption(STATUS)) {
            List<String> arguments = Collections.emptyList();
            gitCli.runCommand(STATUS, arguments);
        } else if (commandLine.hasOption(COMMIT)) {
            List<String> arguments = Arrays.stream(commandLine.getOptionValues(COMMIT)).collect(Collectors.toList());
            gitCli.runCommand(COMMIT, arguments);
        } else if (commandLine.hasOption(RESET)) {
            List<String> arguments = Arrays.stream(commandLine.getOptionValues(RESET)).collect(Collectors.toList());
            gitCli.runCommand(RESET, arguments);
        } else if (commandLine.hasOption(LOG)) {
            List<String> arguments = Arrays.stream(commandLine.getOptionValues(LOG)).collect(Collectors.toList());
            gitCli.runCommand(LOG, arguments);
        } else if (commandLine.hasOption(CHECKOUT)) {
            List<String> arguments = Arrays.stream(commandLine.getOptionValues(CHECKOUT)).collect(Collectors.toList());
            gitCli.runCommand(CHECKOUT, arguments);
        }
    }
}
