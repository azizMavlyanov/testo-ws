package com.testows.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 2667544012622508453L;

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
