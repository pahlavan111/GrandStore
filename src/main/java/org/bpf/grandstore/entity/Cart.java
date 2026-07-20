package org.bpf.grandstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "date_created", insertable = false, updatable = false)
    private LocalDate dateCreated;

    @OneToMany(mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<CartItem> cartItems = new LinkedHashSet<>();

    public BigDecimal getTotalPrice() {
        return cartItems.stream().map(
                CartItem::getTotalPrice
        ).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Optional<CartItem> getCartItemByProductId(Long ProductId) {
        return getCartItems().stream().filter(item ->
                item.getProduct().getId().equals(ProductId)
        ).findFirst();
    }

    public void removeItem(Long ProductId) {

        var item = getCartItemByProductId(ProductId);
        if (item.isPresent()) {
            cartItems.remove(item.get());
            item.get().setCart(null);
        }
    }


    public CartItem addToCart(Product product) {

        var cartItemOptional = getCartItemByProductId(product.getId());
        CartItem cartItem;

        if (cartItemOptional.isPresent()) {
            cartItem = cartItemOptional.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);

        } else {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItem.setCart(this);
            cartItems.add(cartItem);
        }
        return cartItem;
    }

    public void clear() {
        cartItems.clear();
    }

    public boolean isEmpty() {
        return cartItems.isEmpty();
    }


}