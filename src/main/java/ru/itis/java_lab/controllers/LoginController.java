package ru.itis.java_lab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.java_lab.dtos.TokenDto;
import ru.itis.java_lab.dtos.UserDto;
import ru.itis.java_lab.services.UsersService;

@RestController
public class LoginController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/login")
    private ResponseEntity<?> login(@RequestBody UserDto userDto) {
        TokenDto token = usersService.signIn(userDto);
        if(token.getStatus().equals("INVALID")) {
            return ResponseEntity.badRequest().body("Invalid token");
        }
        return ResponseEntity.ok(token);
    }
}
