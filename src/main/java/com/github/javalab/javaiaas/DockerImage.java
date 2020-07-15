package com.github.javalab.javaiaas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

class DockerImage {
    private final ExecutorService executorService;
    private final ProcessBuilder processBuilder;
    private final List<Consumer<String>> listeners = new ArrayList<>();
    private Process process;
    private BufferedReader output;
    private PrintWriter input;

    public DockerImage(ExecutorService executorService, String containerName) {
        this.executorService = executorService;
        processBuilder = new ProcessBuilder("docker", "run", "-i", containerName);
        processBuilder.redirectErrorStream(true);
    }

    public void run() throws IOException {
        process = processBuilder.start();
        output = new BufferedReader(new InputStreamReader(process.getInputStream()));
        input = new PrintWriter(process.getOutputStream(), true);
        executorService.execute(this::readOutput);
    }

    public void send(String inputString) {
        input.println(inputString);
    }

    public void subscribe(Consumer<String> listener) {
        listeners.add(listener);
    }

    private void readOutput() {
        while (process.isAlive()) {
            String outputLine = safeReadOutput();
            for (Consumer<String> listener : listeners) {
                listener.accept(outputLine);
            }
        }
    }

    private String safeReadOutput() {
        try {
            return output.readLine();
        } catch (IOException e) {
            return "Error: cannot read process output. " + e;
        }
    }
}
