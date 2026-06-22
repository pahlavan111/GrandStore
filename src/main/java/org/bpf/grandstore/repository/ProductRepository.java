package org.bpf.grandstore.repository;

import org.bpf.grandstore.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}