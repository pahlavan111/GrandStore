package org.bpf.grandstore.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.bpf.grandstore.dto.OrderDto;
import org.bpf.grandstore.entity.User;
import org.bpf.grandstore.exception.OrderNotFoundException;
import org.bpf.grandstore.mapper.OrderMapper;
import org.bpf.grandstore.repository.OrderRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class OrderService {

    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public List<OrderDto> getAllOrder() {
        User user = authService.getCurrentUser();
        var orders = orderRepository.findAllByCustomer(user);
        return orders.stream().map(orderMapper::toDto).toList();
    }

    @Transactional
    public OrderDto getOrderById(Long orderId) {
        var order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new OrderNotFoundException("order not found");
        }
        var user = authService.getCurrentUser();


        if(!order.get().isPlacedBy(user)){
            throw new AccessDeniedException("you don't have access to this order");
        }
        System.out.println("before mapper");
        return orderMapper.toDto(order.get());
    }
}
