package ru.clevertec.check.core.writer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.clevertec.check.model.Check;
import ru.clevertec.check.paramresolver.CheckParamResolver;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({
        CheckParamResolver.class
})
public class ConsoleCheckWriterTest {

    private ICheckWriter writer;

    @BeforeAll
    void loadWriter() {
        this.writer = new ConsoleCheckWriter();
    }

    @Test
    void checkWrite(Check check) {
        assertDoesNotThrow(() -> writer.write(check));
    }

}
