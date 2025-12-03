package com.kay.driving_exam_app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleException(MethodArgumentNotValidException e) {
        Map<String,String> map = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach((error)->{
            map.put(error.getField(),error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleExamException(ResourceNotFoundException ex) {
        String message = ex.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(ResourceAvailableException.class)
    public ResponseEntity<String> handleResourceAvailable(ResourceAvailableException ex) {
        String message = ex.getMessage();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
    }
    @ExceptionHandler(MyUserNotValidException.class)
    public ResponseEntity<String> handleMyUserNotValidException(MyUserNotValidException ex) {
        String message = ex.getMessage();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }
}
