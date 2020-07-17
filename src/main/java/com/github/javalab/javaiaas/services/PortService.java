package com.github.javalab.javaiaas.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class PortService {

    public static HashMap<Integer, Boolean> ports = new HashMap<>();

    @Value("${port.service.host.name}")
    private String host;

    @Value("${port.service.start}")
    private int lastIndex;

    @Value("${port.service.timeout}")
    private int timeout;

    public boolean pingHost(int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeout);
            socket.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public ArrayList<String> getFreePorts(){

        ArrayList<String> freePorts = new ArrayList<>();

        for(int index = 0; index < 20; lastIndex++) {
            if (lastIndex > 65535) {
                lastIndex = 1024;
            }
            if (pingHost(lastIndex)){
                ports.put(lastIndex, false);
                freePorts.add(Integer.toString(lastIndex));
                index++;
            } else
                ports.put(lastIndex, false);
        }
        return freePorts;
    }
}
