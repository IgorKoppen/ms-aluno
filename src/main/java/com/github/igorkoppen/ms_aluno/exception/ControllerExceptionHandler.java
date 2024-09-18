package com.github.igorkoppen.ms_aluno.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorDTO> resourceNotFound(ResourceNotFoundException exception, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomErrorDTO errorDTO = new CustomErrorDTO(Instant.now().toString(),status.value(),exception.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(errorDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorDTO> methodNotValid(MethodArgumentNotValidException exception, HttpServletRequest request){
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationErrorDTO validationError = new ValidationErrorDTO(Instant.now().toString(), status.value(), "Dados inválidos", request.getRequestURI());
        for(FieldError fieldError : exception.getBindingResult().getFieldErrors()){
            validationError.addError(fieldError.getField(),fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(validationError);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomErrorDTO> databaseException(DatabaseException exception, HttpServletRequest request){
        HttpStatus status = HttpStatus.CONFLICT;
        CustomErrorDTO errorDTO = new CustomErrorDTO(Instant.now().toString(),status.value(),exception.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(errorDTO);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomErrorDTO> handleDataIntegrityViolationException(DataIntegrityViolationException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        String errorMessage = "Data integrity violation";
        CustomErrorDTO errorDTO = new CustomErrorDTO(Instant.now().toString(), status.value(), errorMessage, request.getRequestURI());
        return ResponseEntity.status(status).body(errorDTO);
    }
}