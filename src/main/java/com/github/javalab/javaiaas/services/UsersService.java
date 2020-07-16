package com.github.javalab.javaiaas.services;

import com.github.javalab.javaiaas.dtos.TokenDto;
import com.github.javalab.javaiaas.dtos.UserDto;
import com.github.javalab.javaiaas.models.User;
import org.springframework.security.core.Authentication;

public interface UsersService {
    boolean signUp(UserDto dto);

    TokenDto signIn(UserDto dto);

    User getCurrentUser(Authentication authentication);
}
