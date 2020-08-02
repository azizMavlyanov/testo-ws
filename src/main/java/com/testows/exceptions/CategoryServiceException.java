package com.testows.exceptions;

public class CategoryServiceException extends RuntimeException {
    private static final long serialVersionUID = -2889658029340002011L;

    public CategoryServiceException(String message) {
        super(message);
    }
}
