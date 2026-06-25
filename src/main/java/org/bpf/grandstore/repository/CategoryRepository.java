package org.bpf.grandstore.repository;

import org.bpf.grandstore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends JpaRepository<Category, Byte> {
}