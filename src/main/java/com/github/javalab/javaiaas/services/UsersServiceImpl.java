package com.github.javalab.javaiaas.services;

import com.github.javalab.javaiaas.dtos.TokenDto;
import com.github.javalab.javaiaas.dtos.UserDto;
import com.github.javalab.javaiaas.models.User;
import com.github.javalab.javaiaas.repositories.UsersRepository;
import com.github.javalab.javaiaas.security.details.UserDetailsImpl;
import com.github.javalab.javaiaas.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil util;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder, JwtTokenUtil util) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.util = util;
    }

    @Override
    public boolean signUp(UserDto dto) {
        Optional<User> byLogin = usersRepository.findByLogin(dto.getLogin());
        if (byLogin.isPresent()) {
            return false;
        }
        String hashPassword = passwordEncoder.encode(dto.getPassword());
        User newUser = User.builder()
                .login(dto.getLogin())
                .password(hashPassword)
                .build();
        usersRepository.save(newUser);
        return true;
    }

    @Override
    public TokenDto signIn(UserDto dto) {
        Optional<User> userCandidate = usersRepository.findByLogin(dto.getLogin());
        TokenDto tokenDto = new TokenDto();
        if (userCandidate.isPresent()) {
            User user = userCandidate.get();
            if (BCrypt.checkpw(dto.getPassword(), user.getPassword())) {
                tokenDto.setValue(util.createToken(user));
                tokenDto.setStatus("VALID");
            } else {
                tokenDto.setValue("Incorrect password");
                tokenDto.setStatus("INVALID");
            }
        } else {
            tokenDto.setValue("Can't find user with this login");
            tokenDto.setStatus("INVALID");
        }
        return tokenDto;
    }

    @Override
    public User getCurrentUser(Authentication authentication) {
        return ((UserDetailsImpl) authentication.getPrincipal()).getUser();
    }
}
