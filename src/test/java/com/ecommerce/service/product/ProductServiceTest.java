package com.ecommerce.service.product;

import com.ecommerce.domain.product.Electronics;
import com.ecommerce.domain.product.Product;
import com.ecommerce.repository.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Electronics(
                "E-1",
                "Mouse",
                BigDecimal.valueOf(199),
                10
        );
    }

    @Nested
    @DisplayName("Add product")
    class AddProduct {

        @Test
        @DisplayName("should save product when valid")
        void shouldSaveProductWhenValid() {
            service.addProduct(product);

            verify(repository, times(1)).save(product);
        }

        @Test
        @DisplayName("should throw exception when product is null")
        void shouldThrowExceptionWhenProductIsNull() {
            assertThatThrownBy(() -> service.addProduct(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Product cannot be null");
        }
    }

    @Nested
    @DisplayName("Update product")
    class UpdateProduct {

        @Test
        @DisplayName("should update product when exists")
        void shouldUpdateProductWhenExists() {
            when(repository.existsById(product.getId())).thenReturn(true);

            service.updateProduct(product);

            verify(repository).save(product);
        }

        @Test
        @DisplayName("should throw when product does not exist")
        void shouldThrowExceptionWhenProductDoesNotExist() {
            when(repository.existsById(product.getId())).thenReturn(false);

            assertThatThrownBy(() -> service.updateProduct(product))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Product does not exist");

            verify(repository, never()).save(product);
        }

        @Test
        @DisplayName("should throw exception when product is null")
        void shouldThrowExceptionWhenProductIsNull() {
            assertThatThrownBy(() -> service.addProduct(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Product cannot be null");
        }
    }

    @Nested
    @DisplayName("Remove product")
    class RemoveProduct {

        @Test
        @DisplayName("should remove product when exist")
        void shouldRemoveProductWhenExists() {
            String id = product.getId();

            service.removeProduct(id);

            verify(repository).remove(id);
        }
    }

    @Nested
    @DisplayName("Get all products")
    class GetAllProducts {

        @Test
        @DisplayName("should return all products")
        void shouldReturnAllProducts() {
            when(repository.findAll()).thenReturn(List.of(product));

            List<Product> result = service.getAllProducts();

            assertThat(result)
                    .hasSize(1)
                    .contains(product);
        }
    }

    @Nested
    @DisplayName("Find by id")
    class FindById {

        @Test
        @DisplayName("should return product when exists")
        void shouldReturnProductWhenExists() {
            when(repository.findById(product.getId()))
                    .thenReturn(Optional.of(product));

            Optional<Product> result = repository.findById(product.getId());

            assertThat(result)
                    .isPresent()
                    .contains(product);
        }
    }
}