package ru.itis.java_lab.services;

import ru.itis.java_lab.dtos.TokenDto;
import ru.itis.java_lab.dtos.UserDto;

public interface UsersService {
    void signUp(UserDto dto);
    TokenDto signIn(UserDto dto);
}
