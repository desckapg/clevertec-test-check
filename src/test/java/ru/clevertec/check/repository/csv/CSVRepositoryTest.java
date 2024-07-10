package ru.clevertec.check.repository.csv;

import org.junit.jupiter.api.Test;
import ru.clevertec.check.exception.CheckException;

import static org.junit.jupiter.api.Assertions.*;

public class CSVRepositoryTest {

    @Test
    void loadWithWrongProductPath() {
        assertThrows(CheckException.class, () -> {
           new CSVRepository(
                   "./src/main/resources/products1.csv",
                   "./src/main/resources/discountCards.csv"
           ).load();
        });
    }

    @Test
    void loadWithWrongCardPath() {
        assertThrows(CheckException.class, () -> {
            new CSVRepository(
                    "./src/main/resources/products.csv",
                    "./src/main/resources/discountCards1.csv"
            ).load();
        });
    }

    @Test
    void loadWithRightPaths() {
        assertDoesNotThrow(() -> {
            new CSVRepository(
                    "./src/main/resources/products.csv",
                    "./src/main/resources/discountCards.csv"
            ).load();
        });
    }

    @Test
    void checkDaoLoaded() {
        assertTrue(() -> {
            CSVRepository repository = new CSVRepository(
                    "./src/main/resources/products.csv",
                    "./src/main/resources/discountCards.csv"
            );
            try {
                repository.load();
            } catch (CheckException e) {
                throw new RuntimeException(e);
            }
            return repository.getDaoFactoryImpl() != null;

        });
    }

}
