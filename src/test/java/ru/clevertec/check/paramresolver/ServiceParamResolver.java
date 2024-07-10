package ru.clevertec.check.paramresolver;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.clevertec.check.exception.CheckException;
import ru.clevertec.check.repository.sql.SqlRepository;
import ru.clevertec.check.service.DiscountCardService;
import ru.clevertec.check.service.ProductService;


public class ServiceParamResolver implements ParameterResolver {

    private static final String url = "jdbc:postgresql://localhost:5432/test";
    private static final String username = "test";
    private static final String password = "test";

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == ProductService.class ||
                parameterContext.getParameter().getType() == DiscountCardService.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        var store = extensionContext.getStore(ExtensionContext.Namespace.create(SqlRepository.class));
        return store.getOrComputeIfAbsent(SqlRepository.class, it -> {
            SqlRepository repository = new SqlRepository(url, username, password);
            try {
                repository.load();
            } catch (CheckException e) {
                throw new RuntimeException(e);
            }
            if (parameterContext.getParameter().getType() == DiscountCardService.class) {
                return new DiscountCardService(repository.getDaoFactoryImpl().getDiscountCardDAO());
            } else {
                return new ProductService(repository.getDaoFactoryImpl().getProductDAO());
            }
        });
    }
}
