package com.zti.workoutLogger.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidArgumentExceptions extends RuntimeException {

    public InvalidArgumentExceptions(String content) {
        super(content);
    }
}