package org.bpf.grandstore.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private CartProductDto cartProductDto;
    private int quantity;
    private BigDecimal totalPrice;
}
