package com.xm.task.service.file;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileSearcher {

    private static final String pattern = "_values.csv";

    public List<String> search(Path rootDir) throws IOException {
        List<String> matchesList = new ArrayList<>();
        FileVisitor<Path> matcherVisitor = new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attribs) throws IOException {
                Path name = file.getFileName();
                if (name.toString().endsWith(pattern)) {
                    matchesList.add(file.toString());
                }
                return FileVisitResult.CONTINUE;
            }
        };
        Files.walkFileTree(rootDir, matcherVisitor);
        return matchesList;
    }
}
