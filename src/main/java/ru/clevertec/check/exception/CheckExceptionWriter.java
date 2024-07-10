package ru.clevertec.check.exception;

import java.io.FileWriter;
import java.io.IOException;

public class CheckExceptionWriter {

    private final String pathToFile;

    public CheckExceptionWriter(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public void write(CheckException checkException) {
        try (FileWriter writer = new FileWriter(pathToFile)) {
            writer.write("EXCEPTION\n");
            writer.write(checkException.getType() + "\n");
            writer.write(checkException.getMessage() + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
