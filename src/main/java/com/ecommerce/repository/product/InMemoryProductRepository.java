package com.ecommerce.repository.product;

import com.ecommerce.domain.product.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryProductRepository implements ProductRepository {

    private final Map<String, Product> storage = new HashMap<>();

    @Override
    public void save(Product product) {
        storage.put(product.getId(), product);
    }

    @Override
    public void remove(String id) {
        storage.remove(id);
    }

    @Override
    public Optional<Product> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Product> findAll() {
        return storage.values()
                .stream()
                .toList();
    }

    @Override
    public boolean existsById(String id) {
        return storage.containsKey(id);
    }
}
