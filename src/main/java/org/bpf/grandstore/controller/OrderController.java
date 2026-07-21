package org.bpf.grandstore.controller;

import lombok.AllArgsConstructor;
import org.bpf.grandstore.dto.ErrorDto;
import org.bpf.grandstore.dto.OrderDto;
import org.bpf.grandstore.exception.OrderNotFoundException;
import org.bpf.grandstore.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<List<OrderDto>> getAllOrder() {
        var orders = orderService.getAllOrder();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(
            @PathVariable Long orderId
    ) {
        var order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Void> handleNotfound(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleAccessDenied(AccessDeniedException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto(ex.getMessage()));
    }

}
