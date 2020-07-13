package com.github.javalab.javaiaas.security.details;

import com.github.javalab.javaiaas.models.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.github.javalab.javaiaas.repositories.UsersRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Data
@Component
public class UserDetailsImpl implements UserDetails, UserDetailsService {

    private User user;
    private String token;
    private UsersRepository usersRepository;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Autowired
    public UserDetailsImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String userRole = "user";
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole);
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByLogin(email);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found with login: " + email);
        }
        return new UserDetailsImpl(user.get());
    }
}
