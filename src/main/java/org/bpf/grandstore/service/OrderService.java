package org.bpf.grandstore.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.bpf.grandstore.dto.OrderDto;
import org.bpf.grandstore.entity.User;
import org.bpf.grandstore.mapper.OrderMapper;
import org.bpf.grandstore.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return orders.stream().map(
                orderMapper::toDto
        ).toList();
    }
}
