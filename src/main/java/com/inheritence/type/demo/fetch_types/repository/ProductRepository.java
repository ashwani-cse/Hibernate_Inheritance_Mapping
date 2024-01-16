package com.inheritence.type.demo.fetch_types.repository;

import com.inheritence.type.demo.fetch_types.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
