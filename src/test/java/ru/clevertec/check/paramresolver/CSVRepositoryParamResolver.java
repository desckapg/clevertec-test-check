package ru.clevertec.check.paramresolver;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.clevertec.check.exception.CheckException;
import ru.clevertec.check.repository.csv.CSVRepository;
import ru.clevertec.check.repository.csv.CSVRepositoryTest;

public class CSVRepositoryParamResolver implements ParameterResolver {

    private static final String pathToProductFile = "./src/main/resources/products.csv";
    private static final String pathToCardFile = "./src/main/resources/discountCards.csv";

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == CSVRepository.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        var store = extensionContext.getStore(ExtensionContext.Namespace.create(CSVRepositoryTest.class));
        return store.getOrComputeIfAbsent(CSVRepository.class, it -> {
            CSVRepository repository = new CSVRepository(pathToProductFile, pathToCardFile);
            try {
                repository.load();
            } catch (CheckException e) {
                throw new RuntimeException(e);
            }
            return repository;
        });
    }
}
