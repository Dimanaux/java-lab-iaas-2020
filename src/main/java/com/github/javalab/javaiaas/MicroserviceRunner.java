package com.github.javalab.javaiaas;

public class MicroserviceRunner {

    private DockerImage dockerImage;

    public MicroserviceRunner(DockerImage dockerImage) {
        this.dockerImage = dockerImage;
    }

    public void startMicroservices(String dirName) {
        String script = "cd " +
                dirName +
                "find . -maxdepth 1 -mindepth 1 -type d | while read dir; do" +
                "cd $dir;" +
                "mvn spring-boot:run;" +
                "cd ..;" +
                "done";
        dockerImage.send(script);
    }
}
