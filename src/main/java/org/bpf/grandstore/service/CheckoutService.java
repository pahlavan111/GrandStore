package org.bpf.grandstore.service;


import lombok.AllArgsConstructor;
import org.bpf.grandstore.dto.CheckoutRequest;
import org.bpf.grandstore.dto.CheckoutResponse;
import org.bpf.grandstore.entity.Cart;
import org.bpf.grandstore.entity.Order;
import org.bpf.grandstore.exception.CartIsEmptyException;
import org.bpf.grandstore.exception.CartNotFoundException;
import org.bpf.grandstore.repository.CartRepository;
import org.bpf.grandstore.repository.OrderRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CheckoutService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;

    public CheckoutResponse checkout(CheckoutRequest request) {
        Cart cart = cartRepository
                .getCartWithItems(request.getCartId())
                .orElseThrow(CartNotFoundException::new);

        if (cart.isEmpty()) {
            throw new CartIsEmptyException();
        }

        Order order = Order.fromCart(cart, authService.getCurrentUser());

        orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return new CheckoutResponse(order.getId());
    }
}
