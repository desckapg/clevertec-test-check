package ru.clevertec.check.repository.sql.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.clevertec.check.paramresolver.SqlRepositoryParamResolver;
import ru.clevertec.check.repository.sql.SqlRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({
        SqlRepositoryParamResolver.class
})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SqlDaoFactoryImplTest {

    private final SqlRepository sqlRepository;

    SqlDaoFactoryImplTest(SqlRepository sqlRepository) {
        this.sqlRepository = sqlRepository;
    }

    @Test
    void checkProductDaoLoaded() {
        assertNotNull(this.sqlRepository.getDaoFactoryImpl().getProductDAO());
    }


    @Test
    void checkDiscountCardDaoLoaded() {
        assertNotNull(this.sqlRepository.getDaoFactoryImpl().getDiscountCardDAO());
    }

}
