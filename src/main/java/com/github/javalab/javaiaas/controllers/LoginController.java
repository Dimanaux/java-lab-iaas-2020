package com.github.javalab.javaiaas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.github.javalab.javaiaas.dtos.TokenDto;
import com.github.javalab.javaiaas.dtos.UserDto;
import com.github.javalab.javaiaas.services.UsersService;

@RestController
public class LoginController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/login")
    private ResponseEntity<?> login(@RequestBody final UserDto userDto) {
        TokenDto token = usersService.signIn(userDto);
        if(token.getStatus().equals("INVALID")) {
            return ResponseEntity.badRequest().body("Invalid token");
        }
        return ResponseEntity.ok(token);
    }
}
