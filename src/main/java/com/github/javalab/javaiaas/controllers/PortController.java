package com.github.javalab.javaiaas.controllers;


import com.github.javalab.javaiaas.services.PortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortController {

    @Autowired
    PortService portService;

    @GetMapping("/ports")
    public ResponseEntity<?> get(){
        return ResponseEntity.ok(portService.getFreePorts());
    }

    @GetMapping("/port/{number}")
    public ResponseEntity<?> check(@PathVariable("number") Long numberPort){
        return ResponseEntity.ok(!portService.pingHost(numberPort.intValue()));
    }

}
