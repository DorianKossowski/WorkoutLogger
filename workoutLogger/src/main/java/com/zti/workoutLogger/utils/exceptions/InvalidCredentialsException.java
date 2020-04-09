package com.zti.workoutLogger.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidCredentialsException extends RuntimeException {
    public static final String MESSAGE = "Invalid login or password";

    public InvalidCredentialsException() {
        super(MESSAGE);
    }
}