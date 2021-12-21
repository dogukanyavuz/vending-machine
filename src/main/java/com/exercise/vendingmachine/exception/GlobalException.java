package com.exercise.vendingmachine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(exception.getMessage());
        errorObject.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleEntityNotFoundException(EntityNotFoundException exception) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.NO_CONTENT.value());
        errorObject.setMessage(exception.getMessage());
        errorObject.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.OK);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HashMap<String, String> handleNoHandlerFound(NoHandlerFoundException e, WebRequest request) {
        HashMap<String, String> response = new HashMap<>();
        response.put("status", "fail");
        response.put("message", e.getLocalizedMessage());
        return response;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        if (ex instanceof NullPointerException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
