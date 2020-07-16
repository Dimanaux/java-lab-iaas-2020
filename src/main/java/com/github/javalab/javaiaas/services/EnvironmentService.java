package com.github.javalab.javaiaas.services;

import com.github.javalab.javaiaas.models.Application;
import com.github.javalab.javaiaas.models.EnvironmentVariable;
import com.github.javalab.javaiaas.repositories.EnvironmentVariableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EnvironmentService {

    @Autowired
    private EnvironmentVariableRepository repository;

    public EnvironmentVariable get(String name, Application application) {
        return repository.findByName(name, application);
    }

    public void set(EnvironmentVariable environmentVariable, Application application){
        environmentVariable.setApplication(application);
        repository.save(environmentVariable);
    }

    public ArrayList<EnvironmentVariable> getAll(Application application) {
        return new ArrayList<>(repository.findAllByApplication(application));
    }
}
