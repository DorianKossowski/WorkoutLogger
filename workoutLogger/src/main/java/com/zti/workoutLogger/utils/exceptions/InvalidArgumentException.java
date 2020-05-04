package com.zti.workoutLogger.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidArgumentException extends RuntimeException {

    public InvalidArgumentException(String content) {
        super(content);
    }
}