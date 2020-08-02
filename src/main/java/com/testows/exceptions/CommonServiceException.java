package com.testows.exceptions;

public class CommonServiceException extends RuntimeException {
    private static final long serialVersionUID = -2889658029340002011L;

    public CommonServiceException(String message) {
        super(message);
    }
}
