package org.darius.handler;

import org.darius.exception.InvalidObjectError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exp,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        Map<String, String> fieldsWithErrors = new HashMap<>();
        for (FieldError error : exp.getBindingResult().getFieldErrors()) {
            fieldsWithErrors.put(error.getField(), error.getDefaultMessage());
        }

        InvalidObjectError invalidObjectError = new InvalidObjectError(fieldsWithErrors);
        return new ResponseEntity<>(invalidObjectError, HttpStatus.BAD_REQUEST);
    }
}
