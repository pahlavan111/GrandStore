package org.bpf.grandstore.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.bpf.grandstore.dto.CheckoutRequest;
import org.bpf.grandstore.dto.CheckoutResponse;
import org.bpf.grandstore.entity.Cart;
import org.bpf.grandstore.entity.Order;
import org.bpf.grandstore.entity.OrderItem;
import org.bpf.grandstore.entity.OrderStatus;
import org.bpf.grandstore.exception.CartNotFoundException;
import org.bpf.grandstore.repository.CartRepository;
import org.bpf.grandstore.repository.OrderRepository;
import org.bpf.grandstore.service.AuthService;
import org.bpf.grandstore.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@AllArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<?> checkout(
            @Valid @RequestBody CheckoutRequest request
    ) {
        Cart cart = cartRepository
                .getCartWithItems(request.getCartId())
                .orElseThrow(CartNotFoundException::new);

        if (cart.getCartItems().isEmpty()){
            return ResponseEntity.badRequest().body(
                    Map.of("error" , "Cart is empty")
            );
        }

        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        order.setCustomer(authService.getCurrentUser());
        order.setTotalPrice(cart.getTotalPrice());

        cart.getCartItems().forEach(
                item -> {
                    var orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setProduct(item.getProduct());
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setUniquePrice(item.getProduct().getPrice());
                    orderItem.setTotalPrice(item.getTotalPrice());
                    order.getItems().add(orderItem);
                }
        );

        orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return ResponseEntity.ok().body(new CheckoutResponse(order.getId()));
    }
}
