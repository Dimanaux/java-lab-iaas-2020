package com.github.javalab.javaiaas.controllers;

import com.github.javalab.javaiaas.DockerImage;
import com.github.javalab.javaiaas.DockerImagesFactory;
import com.github.javalab.javaiaas.models.Application;
import com.github.javalab.javaiaas.repositories.ApplicationRepository;
import com.github.javalab.javaiaas.services.GitCloneService;
import org.apache.tomcat.jni.Proc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;

@RestController
public class DockerController {

    @Autowired
    private DockerImagesFactory factory;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private GitCloneService gitCloneService;

    @Value("${dir.name}")
    private String dirName;

    @GetMapping("/run")
    public ResponseEntity<?> runSingleContainer(@RequestParam("app") Long appId) throws IOException {
        Application application = applicationRepository.findById(appId).get();
        DockerImage basicImage = factory.create(application);
        basicImage.run();

        gitCloneService.clone(application);

        navigateToAppDirectory(dirName);
        basicImage.runApp("3000");
        return ResponseEntity.ok().build();
    }

    private void navigateToAppDirectory(String dirName) throws IOException {
        String command = "cd " + dirName;
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();
    }
}
