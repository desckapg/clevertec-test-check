package ru.clevertec.check.core.writer;

import ru.clevertec.check.exception.BadRequestException;
import ru.clevertec.check.exception.CheckException;
import ru.clevertec.check.exception.InternalServerError;
import ru.clevertec.check.model.Check;
import ru.clevertec.check.model.CheckPosition;
import ru.clevertec.check.model.Product;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

public class CSVCheckWriter implements ICheckWriter {

    private final String pathToFile;

    public CSVCheckWriter(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public void write(Check check) throws CheckException {
        try (FileWriter writer = new FileWriter(pathToFile)) {

            writer.write("Date;Time\n");
            writer.write(String.format("%s;%s\n",
                    check.getDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    check.getDateTime().format(DateTimeFormatter.ofPattern("hh:mm:ss"))
            ));

            writer.write("\n");

            writer.write("QTY;DESCRIPTION;PRICE;DISCOUNT;TOTAL\n");

            for (CheckPosition checkPosition : check.getPositions()) {

                writer.write(String.format("%d;%s;%.2f;%.2f;%.2f\n",
                        checkPosition.getQuantity(),
                        checkPosition.getDescription(),
                        checkPosition.getPrice(),
                        checkPosition.getDiscount(),
                        checkPosition.getPriceWithDiscount()
                ));
            }

            writer.write("\n");

            if (Objects.nonNull(check.getDiscountCardNumber())) {
                writer.write("DISCOUNT CARD;DISCOUNT PERCENTAGE\n");
                writer.write(String.format("%s;%d%%\n",
                        check.getDiscountCardNumber(),
                        check.getCardDiscount()));
                writer.write("\n");
            }

            writer.write("TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT\n");
            writer.write(String.format("%.2f$;%.2f$;%.2f$\n",
                    check.getTotalPrice(),
                    check.getTotalDiscount(),
                    check.getTotalPriceWithDiscount()
            ));

        } catch (
                IOException ignored) {
            throw new InternalServerError("WRONG PATH TO CHECK CSV FILE");
        }
    }
}
