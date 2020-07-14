package com.github.javalab.javaiaas.controllers;


import com.github.javalab.javaiaas.models.Application;
import com.github.javalab.javaiaas.services.WorkService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MainApplicationController {

    @Autowired
    private WorkService service;

    @GetMapping("/application/id/{id}")
    public ResponseEntity<?> get(@PathVariable("id") final Long id) throws NotFoundException {
        Application application = service.findAppById(id);
        return ResponseEntity.ok(application);
    }

    @GetMapping("/application/gitUrl/{url}")
    public ResponseEntity<?> getByUrl(@PathVariable("url") final String gitUrl) {
        List<Application> applications = service.findAllAppByGitUrl(gitUrl);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/application/ownerName/{name}")
    public ResponseEntity<?> getByOwnerName(@PathVariable("name") final String ownerName) {
        List<Application> applications = service.findAllAppByOwnerName(ownerName);
        return ResponseEntity.ok(applications);
    }

    @PostMapping("/application")
    public ResponseEntity<?> create(@RequestBody final Application application){
        service.addApp(application);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/application")
    public ResponseEntity<?> update(@RequestBody final Application application) throws NotFoundException {
        service.updateApp(application);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/application/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") final Long id) {
        service.removeApp(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
