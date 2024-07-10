package ru.clevertec.check;

import ru.clevertec.check.core.CheckFormer;
import ru.clevertec.check.core.writer.CSVCheckWriter;
import ru.clevertec.check.core.writer.ConsoleCheckWriter;
import ru.clevertec.check.exception.CheckException;
import ru.clevertec.check.exception.CheckExceptionWriter;
import ru.clevertec.check.model.Check;
import ru.clevertec.check.params.ParamsProcessor;
import ru.clevertec.check.repository.sql.SqlRepository;
import ru.clevertec.check.service.DiscountCardService;
import ru.clevertec.check.service.ProductService;

import java.nio.file.Files;
import java.nio.file.Path;

public class CheckRunner {

    private static ProductService productService;
    private static DiscountCardService discountCardService;

    public static void main(String[] args) {
        ParamsProcessor paramsProcessor = new ParamsProcessor(args);
        try {
            paramsProcessor.process();
            SqlRepository repository = new SqlRepository(
                    paramsProcessor.getRequiredParams().getDatasourceUrl(),
                    paramsProcessor.getRequiredParams().getDatasourceUsername(),
                    paramsProcessor.getRequiredParams().getDatasourcePassword()
            );

            repository.load();

            productService = new ProductService(repository.getDaoFactoryImpl().getProductDAO());
            discountCardService = new DiscountCardService(repository.getDaoFactoryImpl().getDiscountCardDAO());

            productService.load();
            discountCardService.load();;

            CheckFormer checkFormer = new CheckFormer(paramsProcessor.getRequiredParams(), paramsProcessor.getOptionalParams());
            Check check = checkFormer.form();

            CSVCheckWriter csvCheckWriter = new CSVCheckWriter(paramsProcessor.getRequiredParams().getSaveToFile());
            ConsoleCheckWriter consoleCheckWriter = new ConsoleCheckWriter();

            csvCheckWriter.write(check);
            consoleCheckWriter.write(check);
        } catch (CheckException e) {
            String saveToFile = paramsProcessor.getRequiredParams().getSaveToFile();
            if (saveToFile != null && Files.exists(Path.of(saveToFile))) {
                new CheckExceptionWriter(saveToFile).write(e);
            } else {
                new CheckExceptionWriter("result.csv").write(e);
            }
        }
    }

    public static ProductService getProductService() {
        return productService;
    }

    public static DiscountCardService getDiscountCardService() {
        return discountCardService;
    }
}
