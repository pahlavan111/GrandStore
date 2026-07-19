package org.bpf.grandstore.exception;

import org.bpf.grandstore.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDto> handleUnReadableMessage(
            HttpMessageNotReadableException exception
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("Invalid request body"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationError(
            MethodArgumentNotValidException exception
    ) {
        var errors = new HashMap<String, String>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorDto> handleCartNotFound(
            CartNotFoundException exception
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("Cart not found"));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFound(
            ProductNotFoundException exception
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("product not found"));
    }

    @ExceptionHandler(ProductNotFoundInCartException.class)
    public ResponseEntity<ErrorDto> handleProductNotFound(
            ProductNotFoundInCartException exception
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto("Product was not found in the cart"));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentialException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
