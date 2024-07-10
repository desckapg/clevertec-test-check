package ru.clevertec.check.repository.csv;

import ru.clevertec.check.exception.BadRequestException;
import ru.clevertec.check.exception.CheckException;
import ru.clevertec.check.repository.csv.dao.CSVDaoFactoryImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class CSVRepository {

    private final String pathToProductFile;
    private final String pathToDiscountCardFile;

    private CSVDaoFactoryImpl daoFactoryImpl;

    public CSVRepository(String pathToProductFile, String pathToDiscountCardFile) {
        this.pathToProductFile = pathToProductFile;
        this.pathToDiscountCardFile = pathToDiscountCardFile;
    }

    public void load() throws CheckException {
        if (Files.notExists(Path.of(pathToProductFile))) {
            throw new BadRequestException("WRONG products.csv PATH");
        }
        if (Files.notExists(Path.of(pathToDiscountCardFile))) {
            throw new BadRequestException("WRONG products.csv PATH");
        }

        this.daoFactoryImpl = new CSVDaoFactoryImpl(pathToProductFile, pathToDiscountCardFile);
    }

    public CSVDaoFactoryImpl getDaoFactoryImpl() {
        return daoFactoryImpl;
    }
}
