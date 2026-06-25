package org.bpf.grandstore.controller;

import lombok.AllArgsConstructor;
import org.bpf.grandstore.dto.ProductDto;
import org.bpf.grandstore.entity.Category;
import org.bpf.grandstore.entity.Product;
import org.bpf.grandstore.mapper.ProductMapper;
import org.bpf.grandstore.repository.CategoryRepository;
import org.bpf.grandstore.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;


    @GetMapping
    public List<ProductDto> getAllProducts(
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
    public ResponseEntity<ProductDto> getProductById(@PathVariable long id) {

        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(productMapper.toDto(product));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody ProductDto request,
            UriComponentsBuilder uriBuilder
    ) {
        Category category = categoryRepository.findById(request.getCategoryId()).orElse(null);

        if (category == null) {
            return ResponseEntity.badRequest().build();
        }

        Product product = productMapper.toEntity(request);
        product.setCategory(category);
        productRepository.save(product);
        request.setId(product.getId());

        var uri = uriBuilder.path("/products/{id}").buildAndExpand(request.getId()).toUri();

        return ResponseEntity.created(uri).body(request);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable(name = "id") Long id,
            @RequestBody ProductDto request
    ) {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        request.setId(id);

        if (!Objects.equals(product.getCategory().getId(), request.getCategoryId())) {
            Category category = categoryRepository.findById(request.getCategoryId()).orElse(null);

            if (category == null) {
                return ResponseEntity.badRequest().build();
            }

            product.setCategory(category);
        }

        productMapper.update(request, product);

        productRepository.save(product);

        return ResponseEntity.ok(productMapper.toDto(product));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable(name = "id") Long id
    ) {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
