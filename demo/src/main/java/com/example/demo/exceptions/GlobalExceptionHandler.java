package com.example.demo.exceptions;

import com.example.demo.responses.StudentRes;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.Method;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<StudentRes> handleException(Exception ex, HttpServletRequest request) {
        StudentRes res = StudentRes.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Unexpected error: " + ex.getMessage())
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StudentRes> handleValidation(MethodArgumentNotValidException ex) {
        StudentRes res = StudentRes.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage())
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StudentRes> handleResourceNotFound(ResourceNotFoundException ex) {
        StudentRes res = StudentRes.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }
}
