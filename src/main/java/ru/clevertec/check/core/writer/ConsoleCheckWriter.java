package ru.clevertec.check.core.writer;

import ru.clevertec.check.exception.CheckException;
import ru.clevertec.check.model.Check;
import ru.clevertec.check.model.CheckPosition;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ConsoleCheckWriter implements ICheckWriter {

    public void write(Check check) throws CheckException {

        System.out.println();

        System.out.printf("%-12s %-12s%n", "DATE", "TIME");
        System.out.printf("%-12s %-12s%n",
                check.getDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                check.getDateTime().format(DateTimeFormatter.ofPattern("hh:mm:ss")));

        System.out.println();

        System.out.printf("%-12s %-30s %-12s %-12s %-12s %-14s%n", "QTY", "DESCRIPTION", "PRICE", "DISCOUNT", "TOTAL", "DISCOUNT TYPE");

        for (CheckPosition checkPosition : check.getPositions()) {

            System.out.printf("%-12d %-30s %-12.2f %-12.2f %-12.2f %-14s%n",
                    checkPosition.getQuantity(),
                    checkPosition.getDescription(),
                    checkPosition.getPrice(),
                    checkPosition.getDiscount(),
                    checkPosition.getPriceWithDiscount(),
                    checkPosition.getDiscountType().name()
            );
        }

        System.out.println();

        if (Objects.nonNull(check.getDiscountCardNumber())) {
            System.out.printf("%-14s %-20s%n", "DISCOUNT CARD", "DISCOUNT PERCENTAGE");
            System.out.printf("%-14s %-20s%n",
                    check.getDiscountCardNumber(),
                    check.getCardDiscount() + "%"
            );
            System.out.println();
        }

        System.out.printf("%-14s %-20s %-25s%n", "TOTAL PRICE", "TOTAL DISCOUNT", "TOTAL WITH DISCOUNT");
        System.out.printf("%-14s %-20s %-25s%n",
                String.format("%.2f$", check.getTotalPrice()),
                String.format("%.2f$", check.getTotalDiscount()),
                String.format("%.2f$", check.getTotalPriceWithDiscount())
        );
    }

}
