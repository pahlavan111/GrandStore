package org.bpf.grandstore.service;

import lombok.AllArgsConstructor;
import org.bpf.grandstore.dto.CartDto;
import org.bpf.grandstore.dto.CartItemDto;
import org.bpf.grandstore.entity.Cart;
import org.bpf.grandstore.entity.Product;
import org.bpf.grandstore.exception.CartNotFoundException;
import org.bpf.grandstore.exception.ProductNotFoundException;
import org.bpf.grandstore.exception.ProductNotFoundInCartException;
import org.bpf.grandstore.mapper.CartMapper;
import org.bpf.grandstore.repository.CartRepository;
import org.bpf.grandstore.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public CartDto getCart(UUID cartId) {
        var cart = getCartEntity(cartId);
        return cartMapper.toDto(cart);
    }

    @Transactional
    public CartItemDto addToCart(UUID cartId, Long productId) {
        var cart = getCartEntity(cartId);
        var product = getProduct(productId);
        var cartItem = cart.addToCart(product);

        return cartMapper.toDto(cartItem);
    }

    @Transactional
    public CartItemDto updateItem(UUID cartId, Long productId, Integer quantity) {

        var cart = getCartEntity(cartId);
        var cartItem = cart.getCartItemByProductId(productId).orElseThrow(ProductNotFoundInCartException::new);
        cartItem.changeQuantity(quantity);
        return cartMapper.toDto(cartItem);
    }

    @Transactional
    public void deleteItem(UUID cartId, Long productId) {
        var cart = getCartEntity(cartId);
        cart.removeItem(productId);
    }

    @Transactional
    public void clearCart(UUID cartId) {
        Cart cart = getCartEntity(cartId);
        cart.clear();
    }

    private Cart getCartEntity(UUID cartId) {
        return cartRepository
                .getCartWithItems(cartId)
                .orElseThrow(CartNotFoundException::new);
    }

    private Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }
}
