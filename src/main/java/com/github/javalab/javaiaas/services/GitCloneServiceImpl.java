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

        Path path = Paths.get(dirName);
        Process p;

        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String command = "git clone " + application.getGitUrl() + " repos/";
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
