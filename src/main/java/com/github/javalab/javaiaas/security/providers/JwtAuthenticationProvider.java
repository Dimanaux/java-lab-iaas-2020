package com.github.javalab.javaiaas.security.providers;

import com.github.javalab.javaiaas.security.auth.JwtAuthentication;
import com.github.javalab.javaiaas.security.details.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsImpl service;

    @Autowired
    public JwtAuthenticationProvider(UserDetailsImpl service) {
        this.service = service;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthentication jwtAuthentication
                = (JwtAuthentication) authentication;
        UserDetailsImpl userDetails = (UserDetailsImpl) service.loadUserByUsername(jwtAuthentication.getLogin());
        if (userDetails != null) {
            userDetails.setToken(jwtAuthentication.getToken());
            jwtAuthentication.setUserDetails(userDetails);
            jwtAuthentication.setAuthenticated(true);
        } else {
            throw new BadCredentialsException("Incorrect Token");
        }
        return jwtAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.equals(authentication);
    }
}
