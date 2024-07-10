package ru.clevertec.check.repository.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.clevertec.check.exception.CheckException;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.paramresolver.CSVRepositoryParamResolver;
import ru.clevertec.check.repository.csv.CSVRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({
        CSVRepositoryParamResolver.class
})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CSVDaoProductImplTest {

    private Dao<Product> productDao;

    CSVDaoProductImplTest(CSVRepository csvRepository) {
        this.productDao = csvRepository.getDaoFactoryImpl().getProductDAO();
    }

    @Test
    void getAll() {
        assertDoesNotThrow(this.productDao::getAll);
    }

    @Test
    void getProduct() {
        assertDoesNotThrow(() -> this.productDao.get(1));
    }

}
