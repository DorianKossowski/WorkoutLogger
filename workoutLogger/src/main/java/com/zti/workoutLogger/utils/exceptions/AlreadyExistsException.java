package com.zti.workoutLogger.utils.exceptions;

public class AlreadyExistsException extends InvalidArgumentException {

    public AlreadyExistsException(String content) {
        super(content + " already exists");
    }
}