package com.example.dockerversion.generic;

import com.example.dockerversion.error.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice
public class GenericException extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
            CourierDeleteException.class,
            CourierNotAvailableException.class,
            CourierNotFoundException.class,
            CourierReadException.class,
            CourierSavedException.class,
            CourierUpdateException.class,
            PackageDeleteException.class,
            PackageNotFoundException.class,
            PackageReadException.class,
            PackageSavedException.class,
            PackageUpdateException.class
    })
    public ResponseEntity<Object> handleCustomException(Exception e) {
        GenericExceptionResponse error = new GenericExceptionResponse(e.getMessage(), new Date());
        return new ResponseEntity<>(error, HttpStatus.OK);
    }


}
