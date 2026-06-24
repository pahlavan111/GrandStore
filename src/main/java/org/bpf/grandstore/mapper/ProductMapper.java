package org.bpf.grandstore.mapper;

import org.bpf.grandstore.dto.ProduceDto;
import org.bpf.grandstore.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.id", target = "categoryId")
    ProduceDto toDto(Product product);
}
