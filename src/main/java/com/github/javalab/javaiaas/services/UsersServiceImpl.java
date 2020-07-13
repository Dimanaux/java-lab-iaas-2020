package com.github.javalab.javaiaas.services;

import com.github.javalab.javaiaas.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.github.javalab.javaiaas.dtos.TokenDto;
import com.github.javalab.javaiaas.dtos.UserDto;
import com.github.javalab.javaiaas.repositories.UsersRepository;

import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    public void signUp(UserDto dto) {
        String hashPassword = passwordEncoder.encode(dto.getPassword());
        User newUser = User.builder()
                .login(dto.getLogin())
                .password(hashPassword)
                .build();
        usersRepository.save(newUser);
    }

    @Override
    public TokenDto signIn(UserDto dto) {
        Optional<User> userCandidate = usersRepository.findByLogin(dto.getLogin());
        TokenDto tokenDto = new TokenDto();
        if (userCandidate.isPresent()) {
            User user = userCandidate.get();
            if (BCrypt.checkpw(dto.getPassword(), user.getPassword())) {
                tokenDto.setValue(createToken(user));
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

    private String createToken(User user) {
        return Jwts.builder()
                .claim("login", user.getLogin())
                .claim("id", user.getId())
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

}
