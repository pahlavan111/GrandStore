package org.bpf.grandstore.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.bpf.grandstore.dto.CheckoutRequest;
import org.bpf.grandstore.dto.ErrorDto;
import org.bpf.grandstore.exception.CartIsEmptyException;
import org.bpf.grandstore.exception.CartNotFoundException;
import org.bpf.grandstore.service.CheckoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<?> checkout(
            @Valid @RequestBody CheckoutRequest request
    ) {
        return ResponseEntity.ok(checkoutService.checkout(request));
    }


    @ExceptionHandler({CartNotFoundException.class, CartIsEmptyException.class})
    public ResponseEntity<ErrorDto> handleException(
            Exception ex
    ) {
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }
}
