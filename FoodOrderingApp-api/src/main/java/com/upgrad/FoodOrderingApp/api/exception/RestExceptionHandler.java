package com.upgrad.FoodOrderingApp.api.exception;

import com.upgrad.FoodOrderingApp.api.model.ErrorResponse;
import com.upgrad.FoodOrderingApp.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * This class contains all the Exception Handlers for all the exceptions implemented in the project.
 * This is a global code for exception handlers and all the controllers implemented in the project can use this global code.
 */
@ControllerAdvice
public class RestExceptionHandler {


    /**
     * @param exc     - ItemNotFoundException type object containing error code and error message.
     * @param request - The web request object gives access to all the request parameters.
     * @return - ResponseEntity<ErrorResponse> type object displaying error code and error message along with HttpStatus CONFLICT.
     */
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> itemNotFoundException(ItemNotFoundException exc, WebRequest request) {
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()), HttpStatus.NOT_FOUND
        );
    }

    /**
     * @param exc     - AuthorizationFailedException type object containing error code and error message.
     * @param request - The web request object gives access to all the request parameters.
     * @return - ResponseEntity<ErrorResponse> type object displaying error code and error message along with HttpStatus UNAUTHORIZED.
     */
    @ExceptionHandler(AuthorizationFailedException.class)
    public ResponseEntity<ErrorResponse> authorizationFailedException(AuthorizationFailedException exc, WebRequest request) {
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()), HttpStatus.UNAUTHORIZED
        );
    }


    /**
     * @param exc     - RestaurantNotFoundException type object containing error code and error message.
     * @param request - The web request object gives access to all the request parameters.
     * @return - ResponseEntity<ErrorResponse> type object displaying error code and error message along with HttpStatus NOT_FOUND.
     */
    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ErrorResponse> restaurantNotFoundException(RestaurantNotFoundException exc, WebRequest request) {
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()), HttpStatus.NOT_FOUND
        );
    }

    /**
     * @param exc     - CategoryNotFoundException type object containing error code and error message.
     * @param request - The web request object gives access to all the request parameters.
     * @return - ResponseEntity<ErrorResponse> type object displaying error code and error message along with HttpStatus NOT_FOUND.
     */
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> userNotFoundException(CategoryNotFoundException exc, WebRequest request) {
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()), HttpStatus.NOT_FOUND
        );
    }



}