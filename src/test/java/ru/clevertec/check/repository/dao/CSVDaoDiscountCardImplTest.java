package ru.clevertec.check.repository.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.paramresolver.CSVRepositoryParamResolver;
import ru.clevertec.check.repository.csv.CSVRepository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith({
        CSVRepositoryParamResolver.class
})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CSVDaoDiscountCardImplTest {

    private Dao<DiscountCard> discountCardDao;

    CSVDaoDiscountCardImplTest(CSVRepository csvRepository) {
        this.discountCardDao = csvRepository.getDaoFactoryImpl().getDiscountCardDAO();
    }

    @Test
    void getAll() {
        assertDoesNotThrow(this.discountCardDao::getAll);
    }

    @Test
    void getProduct() {
        assertDoesNotThrow(() -> this.discountCardDao.get(1));
    }

}
