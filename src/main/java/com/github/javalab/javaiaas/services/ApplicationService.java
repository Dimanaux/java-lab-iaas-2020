package com.github.javalab.javaiaas.services;


import com.github.javalab.javaiaas.models.Application;
import com.github.javalab.javaiaas.models.User;
import com.github.javalab.javaiaas.repositories.ApplicationRepository;
import javassist.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {
    private final ApplicationRepository repository;
    private final UsersService usersService;

    public ApplicationService(ApplicationRepository repository, UsersService usersService) {
        this.repository = repository;
        this.usersService = usersService;
    }

    public void addApp(Application application) {
        repository.save(application);
    }

    public List<Application> findAllByUserName(String userName, Authentication authentication) {
        User user = usersService.getCurrentUser(authentication);
        return user.getApplications();
    }

    public List<Application> findAllAppByGitUrl(String gitUrl) {
        return repository.findAllByGitUrl(gitUrl);
    }

    public Application findAppById(Long id) throws NotFoundException {
        Optional<Application> application = repository.findById(id);
        return application.orElseThrow(() ->
                new NotFoundException("Application with id " + id + " not found"));
    }

    public void updateApp(Application application) {
        repository.save(application);
    }

    public void removeApp(Long id) {
        repository.deleteById(id);
    }

    public List<Application> findAppByUserId(Long id) {
        return repository.findAllByUserId(id);
    }
}
