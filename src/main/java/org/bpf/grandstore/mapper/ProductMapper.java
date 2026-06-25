package org.bpf.grandstore.mapper;

import org.bpf.grandstore.dto.ProductDto;
import org.bpf.grandstore.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.lang.annotation.Target;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toDto(Product product);

    Product toEntity(ProductDto productDto);

    void update(ProductDto productDto, @MappingTarget Product product);
}
