package io.recheck.jobsapp.bookingvet.backend.exception;

import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        return handle(ex.getBindingResult(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
                                                         HttpStatus status, WebRequest request) {
        return handle(ex.getBindingResult(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        boolean sub = false;
        if (ex.getCause() != null) {
            if (ex.getCause() instanceof JsonParseException) {
                sub = true;
                body.put("error", ex.getCause().getMessage());
            }

            if (!sub) {
                body.put("error", ex.getCause().getCause().getMessage());
            }
        }

        return new ResponseEntity<>(body, headers, status);
    }

    private ResponseEntity<Object> handle(BindingResult br, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        List<String> fieldErrors = br
                .getFieldErrors()
                .stream()
                .map(x -> x.getField() + " : " + x.getDefaultMessage())
                .collect(Collectors.toList());

        List<String> globalErrors = br
                .getGlobalErrors()
                .stream()
                .map(x -> x.getObjectName() + " : " + x.getDefaultMessage())
                .collect(Collectors.toList());

        globalErrors.addAll(fieldErrors);
        body.put("errors", globalErrors);

        return new ResponseEntity<>(body, headers, status);
    }
}
