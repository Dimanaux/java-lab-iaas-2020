package com.github.javalab.javaiaas.services;


import com.github.javalab.javaiaas.DockerImage;
import com.github.javalab.javaiaas.DockerImagesFactory;
import com.github.javalab.javaiaas.models.Application;
import com.github.javalab.javaiaas.repositories.ApplicationRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
public class ApplicationService {
    private final ApplicationRepository repository;
    private final PortService portService;
    private final DockerImagesFactory dockerImagesFactory;

    public ApplicationService(ApplicationRepository repository,
                              PortService portService,
                              DockerImagesFactory dockerImagesFactory) {
        this.repository = repository;
        this.portService = portService;
        this.dockerImagesFactory = dockerImagesFactory;
    }

    public void addApp(Application application) {
        repository.save(application);
    }

    public Application findAppById(Long id) throws NotFoundException {
        Optional<Application> application = repository.findById(id);
        return application.orElseThrow(() ->
                new NotFoundException("Application with id " + id + " not found"));
    }

    public void updateApp(Application application) {
        repository.save(application);
    }

    public List<Integer> runApp(Application application) throws IOException {
        if (application.getType().equals("docker-compose")) {
            List<Integer> ports = portService.allocatePorts(1);
            DockerImage dockerImage = dockerImagesFactory.create(application);
            dockerImage.run();
            dockerImage.send(String.format("git clone %s app", application.getGitUrl()));
            dockerImage.send(String.format("cd app && PORT=%s docker-compose up", ports.get(0)));
            return ports;
        } else {
            return emptyList();
        }
    }

    public void destroyImage(Application application) {
        dockerImagesFactory.destroyImage(application);
    }

    public List<Application> findAppByUserId(Long id) {
        return repository.findAllByUserId(id);
    }
}
