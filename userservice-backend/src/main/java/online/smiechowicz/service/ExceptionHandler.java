package online.smiechowicz.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handle(NotFoundException exception){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(exception.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handle(DataIntegrityViolationException exception){
        return ResponseEntity
                .status(HttpStatus.CONFLICT.value())
                .body(exception.getMessage());
    }

}
