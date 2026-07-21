package org.bpf.grandstore.mapper;


import org.bpf.grandstore.dto.OrderDto;
import org.bpf.grandstore.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto toDto(Order order);
}
