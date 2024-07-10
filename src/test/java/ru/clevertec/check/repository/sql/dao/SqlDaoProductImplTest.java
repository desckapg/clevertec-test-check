package ru.clevertec.check.repository.sql.dao;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.clevertec.check.exception.CheckException;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.paramresolver.ProductParamResolver;
import ru.clevertec.check.paramresolver.SqlRepositoryParamResolver;
import ru.clevertec.check.repository.dao.Dao;
import ru.clevertec.check.repository.sql.SqlRepository;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({
        SqlRepositoryParamResolver.class,
        ProductParamResolver.class
})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SqlDaoProductImplTest {

    private final Dao<Product> productDao;

    SqlDaoProductImplTest(SqlRepository sqlRepository) {
        this.productDao = sqlRepository.getDaoFactoryImpl().getProductDAO();
    }

    @Test
    @Order(1)
    void add(Product product) {
        assertDoesNotThrow(() -> {
            productDao.add(product);
        });
    }


    @Test
    @Order(2)
    void get(Product product) {
        assertDoesNotThrow(() -> {
            productDao.get(product.getId());
        });
    }

    @Test
    void getAll() {
        assertDoesNotThrow(() -> {
            productDao.getAll();
        });
    }

    @Test
    void update(Product product) {
        assertDoesNotThrow(() -> {
            productDao.update(product);
        });
    }

    @Test
    void delete(Product product) {
        assertDoesNotThrow(() -> {
            productDao.delete(product.getId());
        });
    }

    @AfterAll
    void clearTable(Product product) throws CheckException {
        productDao.delete(product.getId());
    }

}
