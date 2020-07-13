package com.github.javalab.javaiaas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.github.javalab.javaiaas.dtos.UserDto;
import com.github.javalab.javaiaas.services.UsersService;

@RestController
public class SignUpController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody final UserDto dto) {
        usersService.signUp(dto);
        return ResponseEntity.ok("Successfully added user");
    }
}
