package ru.itis.java_lab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.java_lab.dtos.UserDto;
import ru.itis.java_lab.services.UsersService;

@RestController
public class SignUpController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody UserDto dto) {
        usersService.signUp(dto);
        return ResponseEntity.ok("Successfully added user");
    }
}
