package ru.clevertec.check;

import ru.clevertec.check.core.CheckFormer;
import ru.clevertec.check.core.writer.CSVCheckWriter;
import ru.clevertec.check.core.writer.ConsoleCheckWriter;
import ru.clevertec.check.exception.CheckException;
import ru.clevertec.check.exception.CheckExceptionWriter;
import ru.clevertec.check.model.Check;
import ru.clevertec.check.params.ParamsProcessor;
import ru.clevertec.check.repository.csv.CSVRepository;
import ru.clevertec.check.service.DiscountCardService;
import ru.clevertec.check.service.ProductService;

import java.nio.file.Paths;

public class CheckRunner {

    private static ProductService productService;
    private static DiscountCardService discountCardService;

    public static void main(String[] args) {
        try {
            ParamsProcessor paramsProcessor = new ParamsProcessor(args);
            paramsProcessor.process();
            CSVRepository csvRepository = new CSVRepository(
                    "./src/main/resources/products.csv",
                    "./src/main/resources/discountCards.csv"
            );

            csvRepository.load();

            productService = new ProductService(csvRepository.getDaoFactoryImpl().getProductDAO());
            discountCardService = new DiscountCardService(csvRepository.getDaoFactoryImpl().getDiscountCardDAO());

            productService.load();
            discountCardService.load();;

            CheckFormer checkFormer = new CheckFormer(paramsProcessor.getRequiredParams(), paramsProcessor.getOptionalParams());
            Check check = checkFormer.form();

            CSVCheckWriter csvCheckWriter = new CSVCheckWriter("result.csv");
            ConsoleCheckWriter consoleCheckWriter = new ConsoleCheckWriter();

            csvCheckWriter.write(check);
            consoleCheckWriter.write(check);
        } catch (CheckException e) {
            new CheckExceptionWriter("result.csv").write(e);
        }
    }

    public static ProductService getProductService() {
        return productService;
    }

    public static DiscountCardService getDiscountCardService() {
        return discountCardService;
    }
}
