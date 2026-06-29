package org.bpf.grandstore.mapper;

import org.bpf.grandstore.dto.CartDto;
import org.bpf.grandstore.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDto toDto(Cart cart);
}