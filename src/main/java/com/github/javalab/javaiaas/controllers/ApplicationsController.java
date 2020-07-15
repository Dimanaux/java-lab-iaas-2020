package com.github.javalab.javaiaas.controllers;


import com.github.javalab.javaiaas.models.Application;
import com.github.javalab.javaiaas.models.User;
import com.github.javalab.javaiaas.security.details.UserDetailsImpl;
import com.github.javalab.javaiaas.services.ApplicationService;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
public class ApplicationsController {
    private final ApplicationService service;

    public ApplicationsController(ApplicationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> index(Authentication authentication) {
        return ResponseEntity.ok(currentUser(authentication).getApplications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(Authentication authentication,
                                  @PathVariable Long id) throws NotFoundException {
        authorize(authentication, id);
        Application application = service.findAppById(id);
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

    private void authorize(Authentication authentication, Long applicationId) {
        try {
            Application application = service.findAppById(applicationId);
            if (!currentUser(authentication).getId().equals(application.getUser().getId())) {
                throw new HttpErrors.Unauthorized();
            }
        } catch (NotFoundException e) {
            throw new HttpErrors.NotFound();
        }
    }

    private User currentUser(Authentication authentication) {
        return ((UserDetailsImpl) authentication.getPrincipal()).getUser();
    }
}
