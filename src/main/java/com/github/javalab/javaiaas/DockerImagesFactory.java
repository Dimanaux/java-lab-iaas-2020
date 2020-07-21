package com.github.javalab.javaiaas;

import com.github.javalab.javaiaas.models.Application;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class DockerImagesFactory {
    private final ExecutorService executorService;
    private final Map<Long, DockerImage> dockerImages = new HashMap<>();

    public DockerImagesFactory(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public DockerImage create(Application application) {
        destroyImage(application);
        DockerImage dockerImage = new DockerImage(executorService, "cosmoskey/dbuilder:latest");
        dockerImages.put(application.getId(), dockerImage);
        return dockerImage;
    }

    public void destroyImage(Application application) {
        DockerImage runningImage = dockerImages.get(application.getId());
        if (runningImage != null) {
            runningImage.destroy();
        }
        dockerImages.remove(application.getId());
    }

    public void destroy() {
        dockerImages.values().forEach(DockerImage::destroy);
    }
}
