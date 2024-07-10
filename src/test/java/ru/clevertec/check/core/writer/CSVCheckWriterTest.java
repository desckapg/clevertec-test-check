package ru.clevertec.check.core.writer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.clevertec.check.model.Check;
import ru.clevertec.check.paramresolver.CheckParamResolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({
        CheckParamResolver.class
})
public class CSVCheckWriterTest {

    private ICheckWriter writer;

    @BeforeAll
    void loadWriter() {
        this.writer = new CSVCheckWriter("test-result.csv");
    }

    @Test
    void checkWrite(Check check) {
        assertDoesNotThrow(() -> writer.write(check));
    }

    @AfterAll
    void deleteFiles() throws IOException {
        if (Files.exists(Path.of("test-result.csv"))) {
            Files.delete(Path.of("test-result.csv"));
        }
    }

}
