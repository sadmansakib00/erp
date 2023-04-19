package com.brainstation23.erp.exception.custom;

import com.brainstation23.erp.exception.custom.custom.AlreadyExistsException;
import com.brainstation23.erp.exception.custom.custom.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception exception) {
        return new ResponseEntity<>("There has been some error", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Object> handleAlreadyExistsException(AlreadyExistsException alreadyExistsException) {
        return new ResponseEntity<>(alreadyExistsException.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException notFoundException) {
        return new ResponseEntity<>(notFoundException.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }


}
