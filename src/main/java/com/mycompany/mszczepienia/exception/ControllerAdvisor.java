package com.mycompany.mszczepienia.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<String> handleAuthenticationException(TokenRefreshException e) {
        log.error("{}", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler({
            UserAlreadyExistsException.class,
            PeselAlreadyExistsException.class,
            InvalidVisitException.class,
            VaccineOutOfStockException.class,
            VisitStatusException.class
    })
    public ResponseEntity<String> handleConflict(RuntimeException e) {
        log.error("{}", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            VisitNotFoundException.class
    })
    public ResponseEntity<String> handleNotFound(RuntimeException e) {
        log.error("{}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        log.error("{}", ex.getMessage());
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toUnmodifiableMap(
                        FieldError::getField,
                        message -> Optional.ofNullable(message.getDefaultMessage()).orElse("")));
        return new ResponseEntity<>(Map.of("errors", errors), headers, status);
    }
}
