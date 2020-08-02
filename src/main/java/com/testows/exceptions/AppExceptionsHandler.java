package com.testows.exceptions;

import com.testows.models.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class AppExceptionsHandler {
    @ExceptionHandler(value = {CategoryServiceException.class})
    public ResponseEntity<Object> handleCategoryServiceException(CategoryServiceException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getLocalizedMessage());

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {ResourceAlreadyExistsException.class})
    public ResponseEntity<Object> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex,
                                                                       WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getLocalizedMessage());

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex,
                                                                       WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getLocalizedMessage());

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(value = {Exception.class})
//    public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {
//        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getLocalizedMessage());
//
//        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
