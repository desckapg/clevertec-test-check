package ru.clevertec.check.exception;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CheckExceptionWriterTest {

    @Test
    void write() {
        CheckExceptionWriter writer = new CheckExceptionWriter("result_error.csv");
        assertDoesNotThrow(() -> {
            writer.write(new InternalServerError("test"));
        });
        assertTrue(Files.exists(Path.of("result_error.csv")));
    }

    @AfterAll
    void clear() throws IOException {
        if (Files.exists(Path.of("result_error.csv"))) {
            Files.delete(Path.of("result_error.csv"));
        }
    }

}
