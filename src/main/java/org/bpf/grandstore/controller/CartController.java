package org.bpf.grandstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.bpf.grandstore.dto.AddItemToCartRequest;
import org.bpf.grandstore.dto.CartDto;
import org.bpf.grandstore.dto.CartItemDto;
import org.bpf.grandstore.dto.UpdateCartRequest;
import org.bpf.grandstore.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Tag(name = "Carts")
@AllArgsConstructor
@RestController
@RequestMapping("/carts")
class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(
            UriComponentsBuilder uriBuilder
    ) {
        CartDto cartDto = cartService.createCart();
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(
            @PathVariable UUID cartId
    ) {
        CartDto cartDto = cartService.getCartDto(cartId);
        return ResponseEntity.ok(cartDto);
    }


    @PostMapping("/{cartId}/items")
    @Operation(summary = "Add a product to a cart")
    public ResponseEntity<CartItemDto> addToCart(
            @Parameter(description = "The id of the cart")
            @PathVariable UUID cartId,
            @Valid @RequestBody AddItemToCartRequest request
    ) {
        CartItemDto cartItemDto = cartService.addToCart(cartId, request.getProductId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<CartItemDto> updateItem(
            @PathVariable UUID cartId,
            @PathVariable Long productId,
            @Valid @RequestBody UpdateCartRequest request
    ) {

        var cartItemDto = cartService.updateItem(cartId, productId, request.getQuantity());
        return ResponseEntity.ok(cartItemDto);
    }


    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable UUID cartId,
            @PathVariable Long productId
    ) {
        cartService.deleteItem(cartId, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<Void> clearCart(
            @PathVariable UUID cartId
    ) {
        cartService.clearCArt(cartId);
        return ResponseEntity.noContent().build();
    }
}
