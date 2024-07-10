package ru.clevertec.check.repository.sql;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.clevertec.check.exception.CheckException;
import ru.clevertec.check.paramresolver.SqlRepositoryParamResolver;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({
        SqlRepositoryParamResolver.class
})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SqlRepositoryTest {

    private SqlRepository sqlRepository;

    SqlRepositoryTest(SqlRepository sqlRepository) {
        this.sqlRepository = sqlRepository;
    }

    @Test
    void connectionWithWrongParams() {
        assertThrows(CheckException.class, () -> {
           new SqlRepository("", "", "").load();
        });
    }

    @Test
    void connectionWithRightParams() {
        assertDoesNotThrow(() -> {
            sqlRepository.load();
        });
    }

    @Test
    void getDaoFactoryLoaded() {
        assertTrue(Objects.nonNull(sqlRepository.getDaoFactoryImpl()));
    }

}
