package ru.kolomych.polyq.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<String> handleUserException(UserNotFoundException userNotCreatedException) {
        return new ResponseEntity<>(userNotCreatedException.getMessage(), HttpStatus.NOT_FOUND);
    }
}
