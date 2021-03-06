package com.github.javalab.javaiaas.security;

import com.github.javalab.javaiaas.security.details.UserDetailsImpl;
import com.github.javalab.javaiaas.security.filters.JwtAuthenticationFilter;
import com.github.javalab.javaiaas.security.providers.JwtAuthenticationProvider;
import com.github.javalab.javaiaas.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenUtil util;
    private final UserDetailsImpl service;
    private final JwtAuthenticationFilter filter;
    private final JwtAuthenticationProvider provider;

    @Autowired
    public SecurityConfig(JwtTokenUtil util, UserDetailsImpl service,
                          JwtAuthenticationFilter filter, JwtAuthenticationProvider provider) {
        this.util = util;
        this.service = service;
        this.filter = filter;
        this.provider = provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().disable();
        http.sessionManagement().disable();
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling().authenticationEntryPoint(util)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authenticationProvider(provider);
        http.authorizeRequests().antMatchers("/swagger-ui.html#/**").permitAll();
    }
}
