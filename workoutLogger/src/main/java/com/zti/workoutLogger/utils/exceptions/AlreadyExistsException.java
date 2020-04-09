package com.zti.workoutLogger.utils.exceptions;

public class AlreadyExistsException extends InvalidArgumentExceptions {

    public AlreadyExistsException(String content) {
        super(content + " already exists");
    }
}