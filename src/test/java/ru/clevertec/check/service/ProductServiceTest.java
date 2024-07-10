package ru.clevertec.check.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.clevertec.check.exception.CheckException;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.paramresolver.ProductParamResolver;
import ru.clevertec.check.paramresolver.ServiceParamResolver;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({
        ServiceParamResolver.class
})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith({
        ProductParamResolver.class
})
public class ProductServiceTest {

    private final ProductService productService;

    ProductServiceTest(ProductService productService) {
        this.productService = productService;
    }

    @BeforeAll
    void load() throws CheckException {
        this.productService.load();
    }

    @Test
    @Order(1)
    void add(Product product) {
        assertDoesNotThrow(() -> this.productService.addProduct(product));
    }

    @Test
    @Order(2)
    void get(Product product) {
        assertTrue(this.productService.isProductExist(product.getId()));
        assertTrue(this.productService.getProduct(product.getId()).isPresent());
    }

    @Test
    void update(Product product) {
        assertDoesNotThrow(() -> this.productService.updateProduct(product));
    }

    @Test
    void delete(Product product) {
        assertDoesNotThrow(() -> this.productService.deleteProduct(product.getId()));
    }

    @AfterAll
    void clear(Product product) {
        assertDoesNotThrow(() -> this.productService.deleteProduct(product.getId()));
    }

}
