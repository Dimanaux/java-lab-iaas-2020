package com.github.javalab.javaiaas.controllers;

import com.github.javalab.javaiaas.models.Application;
import com.github.javalab.javaiaas.models.Instance;
import com.github.javalab.javaiaas.models.User;
import com.github.javalab.javaiaas.security.details.UserDetailsImpl;
import com.github.javalab.javaiaas.services.ApplicationService;
import com.github.javalab.javaiaas.services.InstanceService;
import com.github.javalab.javaiaas.services.UsersService;
import javassist.NotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.List;

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
//        System.out.println(currentUser(authentication).getLogin());
        authorize(authentication, id);
        List<Application> application = service.findAppByUserId(id);
        return ResponseEntity.ok(application);
    }

    @PostMapping
    public ResponseEntity<?> create(Authentication authentication,
                                    @RequestBody Application application) {
        application.setUser(currentUser(authentication));
        service.addApp(application);
        return ResponseEntity.status(HttpStatus.CREATED).body(application);
    }

    @PutMapping("/{id}")
    public @ResponseBody ResponseEntity<?> run(Authentication authentication,
                                 @PathVariable Long id) {
        Application application = authorize(authentication, id);
        try {
            List<Integer> ports = service.runApp(application);
            return ResponseEntity.ok(mapOf("ports", ports));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(mapOf("error", "couldn't start your app"));
        }
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> destroy(Authentication authentication,
//                                     @PathVariable final Long id) {
//        Application application = authorize(authentication, id);
//        service.destroyImage(application);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

//    private void authorize(Authentication authentication, Long userId) {
//        if (!currentUser(authentication).getId().equals(userId)) {
//            throw new HttpErrors.Unauthorized();
//        }
//    }
private Application authorize(Authentication authentication,
                              Long applicationId) {
    try {
        Application application = service.findAppById(applicationId);
        if (!currentUser(authentication).getId()
                .equals(application.getUser().getId())) {
            throw new HttpErrors.Unauthorized();
        }
        return application;
    } catch (NotFoundException e) {
        throw new HttpErrors.NotFound();
    }
}

    private User currentUser(Authentication authentication) {
        return ((UserDetailsImpl) authentication.getPrincipal()).getUser();
    }

    private static HashMap<String, ?> mapOf(String key, Object value) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }
}
