package com.github.javalab.javaiaas.services;

import com.github.javalab.javaiaas.dtos.TokenDto;
import com.github.javalab.javaiaas.dtos.UserDto;

public interface UsersService {
    void signUp(UserDto dto);
    TokenDto signIn(UserDto dto);
}
