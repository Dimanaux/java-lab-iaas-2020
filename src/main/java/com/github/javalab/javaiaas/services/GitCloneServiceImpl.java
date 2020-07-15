package com.github.javalab.javaiaas.services;

import com.github.javalab.javaiaas.models.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class GitCloneServiceImpl implements GitCloneService {

    private final String dirName;

    @Autowired
    public GitCloneServiceImpl(@Value("${dir.name}") String dirName) {
        this.dirName = dirName;
    }

    @Override
    public void clone(Application application) {

        ProcessBuilder processBuilder = new ProcessBuilder();
        Path path = Paths.get(dirName);

        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        processBuilder.directory(new File(dirName));
        StringBuilder command = new StringBuilder();
        command.append("git clone ").append(application.getGitUrl());
        processBuilder.command("sh", "-c", command.toString());

        try {
            Process process = processBuilder.start();
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
