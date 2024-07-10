package ru.clevertec.check.paramresolver;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.clevertec.check.model.Product;

public class ProductParamResolver implements ParameterResolver {

    private static Product product;

    static {
        Product.Builder builder = new Product.Builder();
        builder.setId(1);
        builder.setDescription("test");
        builder.setPrice(1f);
        builder.setQuantityInStock(5);
        builder.setWholesale(false);
        product = builder.build();
    }


    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == Product.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return product;
    }
}
