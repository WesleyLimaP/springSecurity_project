package com.gestao.projeto.master.controller.handler;
import com.gestao.projeto.master.DTO.ErrorDto;
import com.gestao.projeto.master.DTO.FildError;
import com.gestao.projeto.master.DTO.ValidationError;
import com.gestao.projeto.master.service.exceptions.RessoussesNotFoundException;
import com.gestao.projeto.master.service.exceptions.dbExceptions.ViolationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(RessoussesNotFoundException.class)
    public ResponseEntity<ErrorDto> resourceNotFound(RessoussesNotFoundException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorDto errorDto = new ErrorDto(LocalDate.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(errorDto);
    }
    @ExceptionHandler(ViolationException.class)
    public ResponseEntity<ErrorDto> violation(ViolationException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.CONFLICT;
        ErrorDto errorDto = new ErrorDto(LocalDate.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(errorDto);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError errorDto = new ValidationError(LocalDate.now(), status.value(), "erro de validação", request.getRequestURI());
        for(FieldError err : e.getFieldErrors()){
            errorDto.getFildErrors().add(new FildError(err.getField(), err.getDefaultMessage()));
        }
        return ResponseEntity.status(status).body(errorDto);
    }
}
