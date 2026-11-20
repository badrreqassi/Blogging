package com.Platform.Blogging.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail notFoundException( MethodArgumentNotValidException ex ){
        ProblemDetail problemDetail = ex.getBody();
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        });
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("errors", fieldErrors);
        return problemDetail;
    }

    @ExceptionHandler(notFoundException.class)
    public ProblemDetail notFoundException( notFoundException ex){
        ProblemDetail problemDetail=  ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail ;
    }


  @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneral(Exception ex) {
      ProblemDetail problemDetail=  ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR");
      problemDetail.setProperty("timestamp", Instant.now());
      log.error("Unexpected error", ex);
      return problemDetail ;
  }
}
