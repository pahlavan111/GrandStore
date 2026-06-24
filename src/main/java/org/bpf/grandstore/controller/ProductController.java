package org.bpf.grandstore.controller;

import lombok.AllArgsConstructor;
import org.bpf.grandstore.dto.ProduceDto;
import org.bpf.grandstore.entity.Product;
import org.bpf.grandstore.mapper.ProductMapper;
import org.bpf.grandstore.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    @GetMapping
    public List<ProduceDto> getAllProducts(
            @RequestParam(required = false, name = "catId") Byte catId
    ) {

        List<Product> products;

        if (catId == null) {
            products = productRepository.findAllWithCategory();
        } else {
            products = productRepository.findByCategory_Id(catId);
        }

        return products.stream()
                .map(productMapper::toDto
                ).toList();

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProduceDto> getProductById(@PathVariable long id) {

        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(productMapper.toDto(product));
        }

    }

}
