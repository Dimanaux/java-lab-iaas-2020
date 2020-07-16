package com.github.javalab.javaiaas.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class HttpErrors {
    @ResponseStatus(
            value = HttpStatus.UNAUTHORIZED,
            reason = "You are not permitted to perform this action."
    )
    public static class Unauthorized extends RuntimeException {
    }

    @ResponseStatus(
            value = HttpStatus.NOT_FOUND,
            reason = "Resource not found."
    )
    public static class NotFound extends RuntimeException {
    }
}