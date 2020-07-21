package com.github.javalab.javaiaas.controllers;

import com.github.javalab.javaiaas.models.Application;
import com.github.javalab.javaiaas.models.Instance;
import com.github.javalab.javaiaas.models.User;
import com.github.javalab.javaiaas.security.details.UserDetailsImpl;
import com.github.javalab.javaiaas.services.ApplicationService;
import com.github.javalab.javaiaas.services.InstanceService;
import com.github.javalab.javaiaas.services.UsersService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/applications")
public class ApplicationsController {
    private final ApplicationService service;
    @Autowired
    private InstanceService instService;

    public ApplicationsController(ApplicationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> index(Authentication authentication) {
        return ResponseEntity.ok(currentUser(authentication).getApplications());
    }


    @PostMapping("/copy")
    public ResponseEntity<?> createCopy(Authentication authentication, @RequestBody Instance instance) throws IOException, InterruptedException {
        return ResponseEntity.ok(instService.createCopy(instance));
    }

    @PutMapping("/stop/{id}")
    public ResponseEntity<?> stopInstance(Authentication authentication, @PathVariable Long id) {
        instService.stopInstance(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/start/{id}")
    public ResponseEntity<?> startInstance(Authentication authentication, @PathVariable Long id) {
        instService.startInstance(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(Authentication authentication,
                                  @PathVariable Long id)  {
        authorize(authentication, id);
        List<Application> application = service.findAppByUserId(id);
        return ResponseEntity.ok(application);
    }

    @PostMapping
    public ResponseEntity<?> create(Authentication authentication,
                                    @RequestBody Application application) {
        application.setUser(currentUser(authentication));
        service.addApp(application);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(Authentication authentication,
                                    @PathVariable Long id,
                                    @RequestBody Application application) {
        authorize(authentication, id);
        application.setId(id);
        service.updateApp(application);
        return ResponseEntity.ok(application);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") final Long id) {
        service.removeApp(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void authorize(Authentication authentication, Long userId) {
//        try {
//            Application application = service.findAppById(applicationId);
//            if (!currentUser(authentication).getId().equals(userId)) {
//                throw new HttpErrors.Unauthorized();
//            }
//        } catch (NotFoundException e) {
//            throw new HttpErrors.NotFound();
//        }
        if (!currentUser(authentication).getId().equals(userId)) {
            throw new HttpErrors.Unauthorized();
        }
    }

    private User currentUser(Authentication authentication) {
        return ((UserDetailsImpl) authentication.getPrincipal()).getUser();
    }
}