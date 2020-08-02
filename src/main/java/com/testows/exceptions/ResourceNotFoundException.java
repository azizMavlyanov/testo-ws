package com.testows.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    private static final long serialVersionUID = -2498729234738544482L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
