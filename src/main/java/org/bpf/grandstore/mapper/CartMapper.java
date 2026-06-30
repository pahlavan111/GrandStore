package org.bpf.grandstore.mapper;

import org.bpf.grandstore.dto.CartDto;
import org.bpf.grandstore.dto.CartItemDto;
import org.bpf.grandstore.entity.Cart;
import org.bpf.grandstore.entity.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartDto toDto(Cart cart);

    CartItemDto toDto(CartItem cartItem);
}