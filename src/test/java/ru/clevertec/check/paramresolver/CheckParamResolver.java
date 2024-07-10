package ru.clevertec.check.paramresolver;


import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.clevertec.check.model.Check;
import ru.clevertec.check.model.CheckPosition;

import java.util.List;

public class CheckParamResolver implements ParameterResolver {

    private static Check check;

    static {

        Check.Builder checkBuilder = new Check.Builder();

        CheckPosition.Builder positionBuilder = new CheckPosition.Builder();
        positionBuilder.setProductId(1);
        positionBuilder.setQuantity(1);
        positionBuilder.setDescription("test");
        positionBuilder.setPrice(1f);
        positionBuilder.setDiscount(0f);
        positionBuilder.setPriceWithDiscount(0f);

        checkBuilder.setDiscountCardNumber(1);
        checkBuilder.setPositions(List.of(positionBuilder.build()));
        checkBuilder.setTotalPrice(1f);
        checkBuilder.setTotalDiscount(0f);
        checkBuilder.setTotalPriceWithDiscount(1f);

        check = checkBuilder.build();

    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == Check.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return check;
    }
}
