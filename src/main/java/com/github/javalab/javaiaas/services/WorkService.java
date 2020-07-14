package com.github.javalab.javaiaas.services;


import com.github.javalab.javaiaas.models.Application;
import com.github.javalab.javaiaas.repositories.ApplicationRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkService {

    @Autowired
    private ApplicationRepository repository;

    public void addApp(Application application) {
        repository.save(application);
    }

    public List<Application> findAllAppByOwnerName(String ownerName) {
        return repository.findAllByOwnerName(ownerName);
    }

    public List<Application> findAllAppByGitUrl(String gitUrl) {
        return repository.findAllByGitUrl(gitUrl);
    }

    public Application findAppById(Long id) throws NotFoundException {
        Optional<Application> application = repository.findById(id);
        return application.orElseThrow(() ->
                new NotFoundException("Application with id " + id + " not found"));
    }

    public void updateApp(Application application) throws NotFoundException {
            Application app = repository.findById(application.getId()).orElseThrow(() ->
                    new NotFoundException("Application with id " + application.getId() + " not found"));
            app.setOwnerName(application.getOwnerName());
            app.setGitUrl(application.getGitUrl());
            repository.save(app);
    }

    public void removeApp(Long id) {
        repository.deleteById(id);
    }
}
