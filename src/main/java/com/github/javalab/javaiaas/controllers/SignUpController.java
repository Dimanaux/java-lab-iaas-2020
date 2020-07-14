package com.github.javalab.javaiaas.controllers;

import com.github.javalab.javaiaas.dtos.UserDto;
import com.github.javalab.javaiaas.services.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {
    private final UsersService usersService;

    public SignUpController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody UserDto dto) {
        if (usersService.signUp(dto)) {
            return ResponseEntity.ok("Successfully added user");
        } else {
            return ResponseEntity.badRequest().body("Login already taken.");
        }
    }
}
