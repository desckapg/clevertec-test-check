package ru.clevertec.check.paramresolver;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.clevertec.check.model.DiscountCard;

public class DiscountCardParamResolver implements ParameterResolver {

    private static DiscountCard discountCard;

    static {
        DiscountCard.Builder builder = new DiscountCard.Builder();
        builder.setId(1);
        builder.setDiscount(0);
        builder.setNumber(1);
        discountCard = builder.build();
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == DiscountCard.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return discountCard;
    }
}
