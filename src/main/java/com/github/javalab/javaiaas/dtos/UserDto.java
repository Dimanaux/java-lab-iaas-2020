package com.github.javalab.javaiaas.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private String login;
    private String password;
}
