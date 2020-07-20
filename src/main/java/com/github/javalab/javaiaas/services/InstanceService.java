package com.github.javalab.javaiaas.services;

import com.github.javalab.javaiaas.models.Instance;

import java.io.IOException;
import java.util.List;

public interface InstanceService {

    Instance createCopy(Instance instance) throws IOException, InterruptedException;

    void stopInstance(Long id);

    void startInstance(Long id);

    List<Instance> getAll(String username);
}
