package com.ecommerce.service.product;

import com.ecommerce.domain.product.Product;
import com.ecommerce.repository.product.ProductRepository;

import java.util.List;
import java.util.Optional;

// DIP: the product service uses the ProductRepository interface,
// so you can change the repository, e.g. from memory to database.
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Product product) {
        validate(product);
        productRepository.save(product);
    }

    public void removeProduct(String id) {
        productRepository.remove(id);
    }

    public void updateProduct(Product product) {
        validate(product);

        if (!productRepository.existsById(product.getId())) {
            throw new IllegalArgumentException("Product does not exist");
        }

        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    private void validate(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
    }
}
