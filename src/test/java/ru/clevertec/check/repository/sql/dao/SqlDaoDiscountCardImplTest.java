package ru.clevertec.check.repository.sql.dao;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.clevertec.check.exception.CheckException;
import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.paramresolver.DiscountCardParamResolver;
import ru.clevertec.check.paramresolver.SqlRepositoryParamResolver;
import ru.clevertec.check.repository.dao.Dao;
import ru.clevertec.check.repository.sql.SqlRepository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@ExtendWith({
        DiscountCardParamResolver.class,
        SqlRepositoryParamResolver.class
})
public class SqlDaoDiscountCardImplTest {

    private final Dao<DiscountCard> discountCardDao;

    SqlDaoDiscountCardImplTest(SqlRepository sqlRepository) {
        this.discountCardDao = sqlRepository.getDaoFactoryImpl().getDiscountCardDAO();
    }

    @Test
    @Order(1)
    void add(DiscountCard discountCard) {
        assertDoesNotThrow(() -> {
            discountCardDao.add(discountCard);
        });
    }


    @Test
    @Order(2)
    void get(DiscountCard discountCard) {
        assertDoesNotThrow(() -> {
            discountCardDao.get(discountCard.getId());
        });
    }

    @Test
    void getAll() {
        assertDoesNotThrow(() -> {
            discountCardDao.getAll();
        });
    }

    @Test
    void update(DiscountCard discountCard) {
        assertDoesNotThrow(() -> {
            discountCardDao.update(discountCard);
        });
    }

    @Test
    void delete(DiscountCard discountCard) {
        assertDoesNotThrow(() -> {
            discountCardDao.delete(discountCard.getId());
        });
    }


    @AfterAll
    void clearTable(DiscountCard discountCard) throws CheckException {
        discountCardDao.delete(discountCard.getId());
    }

}
