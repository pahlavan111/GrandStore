package org.bpf.grandstore.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.bpf.grandstore.entity.CartItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class CartDto {

    private UUID id;

    private List<CartItemDto> items = new ArrayList<>();

    private BigDecimal totalPrice = BigDecimal.ZERO;
}

