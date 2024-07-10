package ru.clevertec.check.repository.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.clevertec.check.exception.CheckException;
import ru.clevertec.check.paramresolver.CSVRepositoryParamResolver;
import ru.clevertec.check.repository.csv.CSVRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({
        CSVRepositoryParamResolver.class
})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CSVDaoFactoryImplTest {

    private final CSVRepository repository;

    CSVDaoFactoryImplTest(CSVRepository repository) {
        this.repository = repository;
    }

    @Test
    void checkProductDaoLoaded() {
        assertNotNull(repository.getDaoFactoryImpl().getProductDAO());
    }

    @Test
    void checkCardsDaoLoaded() {
        assertNotNull(repository.getDaoFactoryImpl().getDiscountCardDAO());
    }

}
