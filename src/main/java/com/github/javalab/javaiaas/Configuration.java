package com.github.javalab.javaiaas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ComponentScan("com.github.javalab.javaiaas")
@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(8);
    }

    @Bean(value = "dockerImagesFactory", destroyMethod = "destroy")
    public DockerImagesFactory dockerImagesFactory(@Autowired ExecutorService executorService) {
        return new DockerImagesFactory(executorService);
    }
}
