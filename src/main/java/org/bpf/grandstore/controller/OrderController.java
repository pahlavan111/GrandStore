package org.bpf.grandstore.controller;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.bpf.grandstore.dto.OrderDto;
import org.bpf.grandstore.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Transactional
    public ResponseEntity<List<OrderDto>> getAllOrder() {
        var orders = orderService.getAllOrder();
        return ResponseEntity.ok(orders);
    }

}
