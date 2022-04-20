package com.packet.indoor.config.handler;

import com.packet.indoor.exception.AuthenticationException;
import com.packet.indoor.exception.IllegalActionException;
import com.packet.indoor.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalActionException.class)
    public ResponseEntity<ErrorBody> illegalArgumentExceptionHandler(IllegalActionException e, HttpServletRequest request){
        ErrorBody errorBody = ErrorBody.create(request, HttpStatus.BAD_REQUEST, e);
        request.setAttribute("exception", e);
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorBody> notFoundExceptionHandler(NotFoundException e, HttpServletRequest request){
        ErrorBody errorBody = ErrorBody.create(request, HttpStatus.NOT_FOUND, e);
        request.setAttribute("exception", e);
        return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorBody> authenticationExceptionHandler(AuthenticationException e, HttpServletRequest request){
        ErrorBody errorBody = ErrorBody.create(request, HttpStatus.UNAUTHORIZED, e);
        request.setAttribute("exception", e);
        return new ResponseEntity<>(errorBody, HttpStatus.UNAUTHORIZED);
    }
}
