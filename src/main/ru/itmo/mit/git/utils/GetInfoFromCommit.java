package ru.itmo.mit.git.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GetInfoFromCommit {
    public String tree;
    public String parent;
    public String author;
    public String date;
    public String message;

    public GetInfoFromCommit(String commitHash) throws FileNotFoundException {
        File file = new File(".myGit/commits/" + commitHash);
        Scanner readerFromCommit = new Scanner(file);
        while(readerFromCommit.hasNextLine()){
            String[] line = readerFromCommit.nextLine().split(" ");
            switch (line[0]) {
                case "tree":{
                    tree = line[1];
                    break;}
                case "parent":{
                    parent = line[1];
                    break;}
                case "Author:":{
                    author = line[1];
                    break;}
                case "Date:":{
                    date = line[1];
                    break;}
                case "message":{
                    for(int i=1; i < line.length; i++) {
                        message += line[i] + " ";
                    }
                    break;
                }
            }
        }
    }
}
