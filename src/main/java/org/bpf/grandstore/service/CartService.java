package org.bpf.grandstore.service;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.bpf.grandstore.dto.CartDto;
import org.bpf.grandstore.dto.CartItemDto;
import org.bpf.grandstore.entity.Cart;
import org.bpf.grandstore.exception.CartNotFoundException;
import org.bpf.grandstore.exception.ProductNotFoundException;
import org.bpf.grandstore.exception.ProductNotFoundInCartException;
import org.bpf.grandstore.mapper.CartMapper;
import org.bpf.grandstore.repository.CartRepository;
import org.bpf.grandstore.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;


    public CartDto createCart() {
        var cart = new Cart();
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public CartDto getCartDto(UUID cartId) {
        var cart = getCart(cartId);
        return cartMapper.toDto(cart);
    }

    private Cart getCart(UUID cartId) {
        return cartRepository
                .getCartWithItems(cartId)
                .orElseThrow(CartNotFoundException::new);
    }

    @Transactional
    public CartItemDto addToCart(UUID cartId, Long productId) {
        var cart = getCart(cartId);

        var product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

        var cartItem = cart.addToCart(product);

        cartRepository.save(cart);

        return cartMapper.toDto(cartItem);
    }

    public CartItemDto updateItem(UUID cartId, Long productId, Integer quantity) {

        var cart = getCart(cartId);
        var cartItem = cart.getCartItemByProductId(productId);

        if (cartItem == null) {
            throw new ProductNotFoundInCartException();
        }

        cartItem.setQuantity(quantity);
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }

    public void deleteItem(UUID cartId, Long productId) {
        Cart cart = getCart(cartId);
        cart.removeItem(productId);
        cartRepository.save(cart);
    }

    public void clearCArt(UUID cartId) {

        Cart cart = getCart(cartId);
        cart.clear();
        cartRepository.save(cart);
    }
}
