package ru.clevertec.check.params;

import ru.clevertec.check.exception.BadRequestException;
import ru.clevertec.check.exception.CheckException;

import java.util.Objects;

public class OptionalParams {

    private final String[] params;

    private Integer discountCardNumber;

    public OptionalParams(String[] params) {
        this.params = params;
        this.discountCardNumber = null;
    }

    public void process() throws CheckException {
        for (String param : params) {
            if (param.startsWith("discountCard=")) {
                try {
                    discountCardNumber = Integer.parseUnsignedInt(param.substring("discountCard=".length()));
                } catch (NumberFormatException ignored) {
                    throw new BadRequestException("WRONG DISCOUNT CARD NUMBER");
                }
            }
        }
    }

    public boolean isDiscountCardPresent() {
        return Objects.nonNull(discountCardNumber);
    }

    public Integer getDiscountCardNumber() {
        return discountCardNumber;
    }

}
