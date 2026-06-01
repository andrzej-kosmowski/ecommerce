package com.ecommerce.repository.product;

import com.ecommerce.domain.product.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    void save(Product product);
    void remove(String id);
    Optional<Product> findById(String id);
    List<Product> findAll();
    boolean existsById(String id);
}
