package com.github.javalab.javaiaas.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Service
public class PortService {
    private int lastCheckedPort = 1024;

    public List<Integer> allocatePorts(int count) {
        List<Integer> freePorts = new ArrayList<>(count);

        while (freePorts.size() < count) {
            if (!portTaken(lastCheckedPort)) {
                freePorts.add(lastCheckedPort);
            }
            lastCheckedPort++;
        }
        return freePorts;
    }

    private static boolean portTaken(int port) {
        try (Socket _socket = new Socket("localhost", port)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
