package com.github.javalab.javaiaas.controllers;

import com.github.javalab.javaiaas.dtos.TokenDto;
import com.github.javalab.javaiaas.dtos.UserDto;
import com.github.javalab.javaiaas.services.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final UsersService usersService;

    public LoginController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/login")
    private ResponseEntity<?> login(@RequestBody UserDto userDto) {
        TokenDto token = usersService.signIn(userDto);
        if (token.getStatus().equals("INVALID")) {
            return ResponseEntity.badRequest().body("Invalid token");
        }
        return ResponseEntity.ok(token);
    }
}
